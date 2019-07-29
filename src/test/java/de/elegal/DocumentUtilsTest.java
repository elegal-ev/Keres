package de.elegal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract Test class. Just for less code duplication in both classes.
 *
 * @author Lars Quentin, Valerius Mattfeld
 */
public abstract class DocumentUtilsTest {

    /**
     * The Path where all test files are located
     */
    protected static final String TEST_PATH = System.getProperty("user.dir") + "/src/test/resources/docx/";

    /**
     * The Test file for all expected values
     */
    protected static final String TEST_FILE = TEST_PATH + "Headline.docx";

    /**
     * A short method for OOP POI Document Generation
     *
     * @param fileName The full path to the docx
     * @return The POI Word object
     */
    protected XWPFDocument createDocument(String fileName) throws Exception {
        return DocumentUtils.openFile(fileName);
    }

    /**
     * A short method for extracting all strings from a word document
     *
     * @param fileName The full path to the docx
     * @return All Strings of the document
     */
    protected List<String> getAllStrings(String fileName) throws Exception {
        XWPFDocument doc = createDocument(fileName);
        return getAllStrings(doc);
    }

    /**
     * Extracts all strings from a given Document
     *
     * @param document The POI Word object
     * @return All Strings of the document
     */
    // Needed for reflections
    @SuppressWarnings("unchecked")
    protected List<String> getAllStrings(XWPFDocument document) {
        // We really don't want to make the method public since its not for normal usage
        // therefore we use reflections.
        List<XWPFRun> allElements;
        try {
            Method getRunsFromDocument = DocumentUtils.class
                    .getDeclaredMethod("getRunsFromDocument", XWPFDocument.class);
            getRunsFromDocument.setAccessible(true);
            allElements = (List<XWPFRun>) getRunsFromDocument.invoke(null, document);

        } catch (Exception e) {
            throw new IllegalStateException("This should never happen. The test itself failed.");
        }

        List<String> allStrings = allElements.stream().map(run -> run.getText(0)).collect(Collectors.toList());
        return allStrings;
    }

    /**
     * A Contains Method but with substrings
     *
     * @param strings   A list of strings
     * @param subString The substring to check against in every thing
     * @return The number of strings which contain the substring
     */
    protected int numberOfDeepChecks(List<String> strings, String subString) {
        int i = 0;
        for (String str : strings) {
            if (str.contains(subString)) i++;
        }
        return i;
    }

    /**
     * Saves the given document and immediately loades it again.
     *
     * @param toBeSaved The POI Word object
     * @param path      The path where it should be saved
     * @return The reloaded Document
     */
    protected XWPFDocument saveAndLoadAgain(XWPFDocument toBeSaved, String path) throws Exception {
        // If it doesn't exist, thats okay too. We just don't want to fail it because the file is already existing
        new File(path).delete();

        DocumentUtils.writeFile(toBeSaved, path);
        return createDocument(path);
    }
}
