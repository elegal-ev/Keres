package de.elegal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentUtils {
    public final static String OPENING_TAG = "<";
    public final static String CLOSING_TAG = ">";

    public static XWPFDocument openFile(final String str) throws NullPointerException, InvalidFormatException, IOException {
        Objects.requireNonNull(str);
        File file = new File(str);
        OPCPackage opcPackage = OPCPackage.open(file);
        Objects.requireNonNull(opcPackage, "Something went wrong opening " + str);
        return new XWPFDocument(opcPackage);
    }

    public static void writeFile(final XWPFDocument doc, final String str) throws NullPointerException, IOException {
        Objects.requireNonNull(doc);
        Objects.requireNonNull(str);
        doc.write(new FileOutputStream(str));
    }

    public static Tuple<Integer, XWPFDocument> replaceAllTags(XWPFDocument doc, final String tag, final String replace) {
        String newTag = OPENING_TAG + tag + CLOSING_TAG;
        return replaceAllStrings(doc, newTag, replace);
    }

    // Not working with tables
    // If needed, should look sth like that https://stackoverflow.com/a/22269035
    // Also, it allows to replace multiple occurrences
    public static Tuple<Integer, XWPFDocument> replaceAllStrings(XWPFDocument doc, final String tag, final String replace) {
        if (tag == null) return new Tuple<>(0, doc); // No replaces
        Objects.requireNonNull(doc);
        Objects.requireNonNull(replace);
        int counter = 0;
        List<XWPFRun> runs = doc.getParagraphs()
                .stream()
                .map(XWPFParagraph::getRuns)
                .filter(Objects::nonNull)
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        for (XWPFRun run : runs) {
            if (replaceRun(run, tag, replace)) counter++;
        }
        return new Tuple<>(counter, doc);
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
