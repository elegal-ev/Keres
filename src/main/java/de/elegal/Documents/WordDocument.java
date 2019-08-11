package de.elegal.Documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class WordDocument extends Document{
    public final static String OPENING_TAG = "<";
    public final static String CLOSING_TAG = ">";

    private XWPFDocument doc;
    private HashSet<String> existingTags;

    public WordDocument(String path) throws InvalidFormatException, IOException{
        Objects.requireNonNull(path);
        openFile(path);
        this.existingTags = findInitialTags();
    }

    // TODO: Validation

    @Override
    protected void openFile(String path) throws InvalidFormatException, IOException {
        File file = new File(path);

        OPCPackage opcPackage = OPCPackage.open(file); // File -> Apache-Doc-Wrapper
        Objects.requireNonNull(opcPackage, "Something went wrong opening" + path);

        this.doc = new XWPFDocument(opcPackage);
        Objects.requireNonNull(doc, "Something went wrong parsing" + path);
    }

    @Override
    public void saveAndCloseFile(String path) throws IOException {
        Objects.requireNonNull(path);
        if (new File(path).exists()) {
            throw new IllegalArgumentException("File already existing.");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        this.doc.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        this.doc.close();
    }

    @Override
    public void closeWithoutSaving() throws IOException{
        this.doc.close();
    }

    @Override
    protected int replaceString(String string, String replace) {
        // If the tag is null, we just accept it and obviously do 0 replacements
        if (string == null) return 0;
        Objects.requireNonNull(replace);

        /*
         * The following Line is needed because of docx internals. Best explained by an example:
         * For example, we have the String "<TagInNewLine>" somewhere in our document.
         * Normally, this should be within one run, because there is no reason not to.
         * At least, if you are not completely Word-Developer insane.
         * But, since "TagInNewLine" is not a real word, it's splitted in
         * <w:p w:rsidR="00240200" w:rsidRDefault="00240200">
         *   <w:r>
         *     <w:t>&lt;</w:t>
         *   </w:r>
         *   <w:proofErr w:type="spellStart"/>
         *   <w:r>
         *       <w:t>TagInNewLine</w:t>
         *   </w:r>
         *   <w:proofErr w:type="spellEnd"/>
         *   <w:r>
         *     <w:t>&gt;</w:t>
         *   </w:r>
         * </w:p>
         * Therefore, __if__ it really exists, we have to do an expensive lookup over multiple runs.
         * Also, we theoretically could work on paragraphs only, but that would destroy any kind
         * of formatting within the paragraph.
         * Thats because "spellStart" is not the only type a run can have and setting the text for a paragraph
         * would loose all those Attributes.
         * See https://stackoverflow.com/a/25186678 for further informations.
         */
        if (!getText().contains(string)) return 0;

        Replacer replacer = new Replacer(string, replace);
        return replacer.replace(this.doc);
    }

    @Override
    public String getOpeningTag() {
        return OPENING_TAG;
    }

    @Override
    public String getClosingTag() {
        return CLOSING_TAG;
    }

    @Override
    public Set<String> getTags() {
        return existingTags;
    }

    @Override
    public String getText() {
        return this.doc.getParagraphs()
                .stream()
                .map(XWPFParagraph::getText)
                .collect(Collectors.joining());
    }

    private HashSet<String> findInitialTags() {
        HashSet<String> allTags = new HashSet<>();
        Matcher m = Pattern.compile(getOpeningTag() + "(.*?)" + getClosingTag())
                .matcher(getText());
        while (m.find()) {
            String str = m.group();
            // Getting rid of the angle brackets
            allTags.add(str.substring(1,str.length()-1));
        }
        return allTags;
    }
}
