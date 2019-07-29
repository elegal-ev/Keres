package de.elegal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DocumentUtilsTest {
    protected static final String TEST_PATH = System.getProperty("user.dir")+ "/src/test/resources/docx/";
    protected static final String TEST_FILE = TEST_PATH + "Headline.docx";

    protected XWPFDocument createDocument(String fileName) throws Exception{
        return DocumentUtils.openFile(fileName);
    }

    protected List<String> getAllStrings(String fileName) throws Exception {
        XWPFDocument doc = createDocument(fileName);
        return getAllStrings(doc);
    }

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

    protected int numberOfDeepChecks(List<String> strings, String subString) {
        int i = 0;
        for (String str : strings) {
            if (str.contains(subString)) i++;
        }
        return i;
    }

    protected XWPFDocument saveAndLoadAgain(XWPFDocument toBeSaved, String path) throws Exception{
        // If it doesn't exist, thats okay too. We just don't want to fail it because the file is already existing
        new File(path).delete();

        DocumentUtils.writeFile(toBeSaved, path);
        return createDocument(path);
    }
}
