package de.elegal;

import de.elegal.Utils.DocumentUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * The Testing class for loading and saving files
 *
 * @author Lars Quentin, Valerius Mattfeld
 */
public class DocumentUtilsIOTest extends DocumentUtilsTest {

    /**
     * Checks whether the loaded file is null
     */
    @Test
    public void isNotNull() {
        XWPFDocument test;
        try {
            test = DocumentUtils.openFile(TEST_FILE);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
            return;
        }
        Assert.assertNotEquals(test, null);
    }

    /**
     * checks whether the headline is found
     */
    @Test
    public void hasHeadline() throws Exception {
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(1, numberOfDeepChecks(allStrings, "Headline"));
    }

    /**
     * checks whether the tag is found
     */
    @Test
    public void hasTag() throws Exception {
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(1, numberOfDeepChecks(allStrings,
                DocumentUtils.OPENING_TAG + "Tag" + DocumentUtils.CLOSING_TAG));
    }

    /**
     * checks if a nonexisting word is not found
     */
    @Test
    public void hasNotRubbish() throws Exception {
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(0, numberOfDeepChecks(allStrings, "Rubbish"));
    }

    /**
     * checks whether writing works by reloading it after and comparing all words
     */
    @Test
    public void writeFile() throws Exception {
        XWPFDocument oldTest = createDocument(TEST_FILE);

        String outFilePath = TEST_PATH + "HeadlineTest.docx";
        XWPFDocument newTest = saveAndLoadAgain(oldTest, outFilePath);
        Assert.assertEquals(getAllStrings(oldTest), getAllStrings(newTest));
    }
}