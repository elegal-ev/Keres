package de.elegal.Documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordDocument extends Document{
    private final static String OPENING_TAG = "<";
    private final static String CLOSING_TAG = ">";

    private XWPFDocument doc;
    private HashSet<String> existingTags;

    public WordDocument(String path) throws InvalidFormatException, IOException{
        Objects.requireNonNull(path);
        openFile(path);
        this.existingTags = findInitialTags();
    }

    public WordDocument(String path, Collection<String> tags) throws InvalidFormatException, IOException{
        this(path);
        // TODO: Checking here
    }

    @Override
    protected void openFile(String path) throws InvalidFormatException, IOException {
        File file = new File(path);

        OPCPackage opcPackage = OPCPackage.open(file); // File -> Apache-Doc-Wrapper
        Objects.requireNonNull(opcPackage, "Something went wrong opening" + path);

        this.doc = new XWPFDocument(opcPackage);
        Objects.requireNonNull(doc, "Something went wrong parsing" + path);


        this.doc.close();
        opcPackage.close(); // Maybe not even needed
    }

    @Override
    public void saveAndCloseFile(String path) throws IOException {
        Objects.requireNonNull(path);
        this.doc.write(new FileOutputStream(path));
    }

    @Override
    protected int replaceString(String string, String replace) {
        // If the tag is null, we just accept it and obviously do 0 replacements
        if (string == null) return 0;
        Objects.requireNonNull(replace);
        int counter = 0;
        for (XWPFRun run : getRuns())
            if (replaceRun(run, string, replace)) counter++;
        return counter;
    }

    @Override
    public String getOpeningTag() {
        return getOpeningTag();
    }

    @Override
    public String getClosingTag() {
        return getClosingTag();
    }

    @Override
    public Set<String> getTags() {
        return existingTags;
    }

    @Override
    public String getText() {
        return getRuns().stream()
                .map(x -> x.getText(0))
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

    private List<XWPFRun> getRuns() {
        return this.doc.getParagraphs()
                .stream()
                .map(XWPFParagraph::getRuns)
                // getRuns returns null instead of empty lists
                .filter(Objects::nonNull)
                // This is just a flatmapping from List<List<XWPFRun>> to List<XWPFRun>
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    private static boolean replaceRun(XWPFRun run, final String tag, final String replace) {
        if (run == null || tag == null || replace == null) return false;
        String text = run.getText(0);
        if (text == null || !text.contains(tag)) return false;
        text = text.replace(tag, replace);
        run.setText(text, 0);
        return true;
    }
}
