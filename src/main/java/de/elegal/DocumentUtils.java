package de.elegal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;


/**
 * A static helper class to not scare the noobies.
 * Basically just a wrapper around Apache POI for easier .docx editing.
 *
 * @author Lars Quentin, Valerius Mattfeld
 */
public class DocumentUtils {
    /**
     * The Symbols which start an Tag.
     */
    public final static String OPENING_TAG = "<";

    /**
     * The Symbols which end an Tag.
     */
    public final static String CLOSING_TAG = ">";

    /**
     * A private constuctor since we don't want an instance of an static class.
     */
    private DocumentUtils() {
    }

    /**
     * Static Method to open a File. Both relative and absolute Paths are allowed.
     * Java looks from the current working directory, not the .class location.
     *
     * @param str The Path to the docx
     * @return A Apache POI Representation of the Word Document
     * @throws NullPointerException   If either the given path is null or Apache POI returned null while opening and parsing the file. For further information see getMessage().
     * @throws InvalidFormatException If the Path is malformatted
     * @throws IOException            If IO problems occur while parsing the File
     */
    public static XWPFDocument openFile(final String str) throws NullPointerException, InvalidFormatException, IOException {
        Objects.requireNonNull(str);
        File file = new File(str);
        OPCPackage opcPackage = OPCPackage.open(file);
        Objects.requireNonNull(opcPackage, "Something went wrong opening " + str);
        XWPFDocument document = new XWPFDocument(opcPackage);
        Objects.requireNonNull(document, "Something went wrong parsing " + str);
        return document;
    }

    /**
     * Static Method to write a file. Overwrites by default.
     *
     * @param doc The POI Word Object
     * @param str The Path to save it as
     * @throws NullPointerException If any of the arguments are null
     * @throws IOException          If IO problems occur while saving the File
     */
    public static void writeFile(final XWPFDocument doc, final String str) throws NullPointerException, IOException {
        Objects.requireNonNull(doc);
        Objects.requireNonNull(str);
        doc.write(new FileOutputStream(str));
    }

    /**
     * A wrapper to {@link #replaceAllStrings} but with tags around the string.
     *
     * @param doc     The POI Word object
     * @param tag     The String to look for within Tags
     * @param replace The String to replace it with
     * @return A Tuple of the Number of Replacements as well as the new Document, since the Object itself has changed,
     * not the saved document. See {@link #writeFile} for that.
     */
    public static Tuple<Integer, XWPFDocument> replaceAllTags(XWPFDocument doc, final String tag, final String replace) {
        String newTag = OPENING_TAG + tag + CLOSING_TAG;
        return replaceAllStrings(doc, newTag, replace);
    }

    // Not working with tables
    // If needed, should look sth like that https://stackoverflow.com/a/22269035

    /**
     * A Function to replace all occurrences of the wanted String, even if it isn't within a tag.
     * Therefore, if that's needed, use {@link #replaceAllTags} instead.
     * Does not work on Tables.
     *
     * @param doc     The POI Word object
     * @param tag     The String to look for within Tags
     * @param replace The String to replace it with
     * @return A Tuple of the Number of Replacements as well as the new Document, since the Object itself has changed,
     * not the saved document. See {@link #writeFile} for that.
     */
    public static Tuple<Integer, XWPFDocument> replaceAllStrings(XWPFDocument doc, final String tag, final String replace) {
        // If the tag is null, we just accept it and obviously do 0 replacements
        if (tag == null) return new Tuple<>(0, doc);
        Objects.requireNonNull(doc);
        Objects.requireNonNull(replace);
        int counter = 0;

        for (XWPFRun run : getRunsFromDocument(doc)) {
            if (replaceRun(run, tag, replace)) counter++;
        }
        return new Tuple<>(counter, doc);
    }

    /**
     * Flattens the complicated XWPFDocumentStructure to extract its XWPFRuns. Does return references on the same
     * objects instead of doing a deep copy.
     *
     * @param doc the apache doc parsed template file
     * @return List of all possible strings from the document
     */
    private static List<XWPFRun> getRunsFromDocument(final XWPFDocument doc) {
        return doc.getParagraphs()
                .stream()
                .map(XWPFParagraph::getRuns)
                // getRuns returns null instead of empty lists
                .filter(Objects::nonNull)
                // This is just a flatmapping from List<List<XWPFRun>> to List<XWPFRun>
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }

    /**
     * The Function which actually processes all Tags by just searching for the given term and if existing replaces it.
     *
     * @param run     The current run to potentially replace in
     * @param tag     The String to look for within Tags
     * @param replace The String to replace it with
     * @return Whether it has replaced anything. The actual value gets changed by reference.
     */
    private static boolean replaceRun(XWPFRun run, final String tag, final String replace) {
        if (run == null || tag == null || replace == null) return false;
        String text = run.getText(0);
        if (text == null || !text.contains(tag)) return false;
        text = text.replace(tag, replace);
        run.setText(text, 0);
        return true;
    }
}
