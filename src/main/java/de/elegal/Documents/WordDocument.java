package de.elegal.Documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

/**
 * A java representation of a word document
 */
public class WordDocument extends Document {
    /**
     * The opening tag
     */
    public final static String OPENING_TAG = "<";
    /**
     * The closing tag
     */
    public final static String CLOSING_TAG = ">";

    /**
     * The word document object
     */
    private XWPFDocument doc;

    /**
     * The Constuctor
     *
     * @param path Path to the word document
     * @throws InvalidFormatException When the path is malformatted
     * @throws IOException            When some IO Errors occur
     */
    public WordDocument(String path) throws InvalidFormatException, IOException {
        Objects.requireNonNull(path);
        openFile(path);
    }

    /**
     * Opens the file.
     *
     * @param path The path (can be both relative and absolute) to the document
     * @throws InvalidFormatException When the path is malformatted
     * @throws IOException            When some IO Errors occur
     */
    @Override
    protected void openFile(String path) throws IOException {
        // Important: We __don't__ want to change the actual document, so please don't use an OPCPackage, since that's
        // just a reference, hence all changes will be made on the template as well.
        FileInputStream fileStream = new FileInputStream(path);
        Objects.requireNonNull(fileStream, "Something went wrong opening" + path);

        this.doc = new XWPFDocument(fileStream);
        Objects.requireNonNull(doc, "Something went wrong parsing" + path);
    }

    /**
     * Saves and closes the file
     *
     * @param path The path (can be both relative and absolute) where the document should be saved
     * @throws IOException When some IO Errors occur
     */
    @Override
    public void saveAndCloseFile(String path) throws IOException {
        Objects.requireNonNull(path);
        // The GUI asks whether it should be replaced
        File testFile = new File(path);
        if (testFile.exists()) testFile.delete();
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        this.doc.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        this.doc.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeWithoutSaving() throws IOException {
        this.doc.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int replaceString(String string, String replace) {
        // If the tag is null, we just accept it and obviously do 0 replacements
        if (string == null) return 0;
        Objects.requireNonNull(replace);

        // See Replacer why that makes sense
        if (!getText().contains(string)) return 0;

        Replacer replacer = new Replacer(string, replace);
        return replacer.replace(this.doc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpeningTag() {
        return OPENING_TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClosingTag() {
        return CLOSING_TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getTags() {
        HashSet<String> allTags = new HashSet<>();
        Matcher m = Pattern.compile(getOpeningTag() + "(.*?)" + getClosingTag())
                .matcher(getText());
        while (m.find()) {
            String str = m.group();
            // Getting rid of the angle brackets
            allTags.add(str.substring(1, str.length() - 1));
        }
        return allTags;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return this.doc.getParagraphs()
                .stream()
                .map(XWPFParagraph::getText)
                .collect(Collectors.joining());
    }
}
