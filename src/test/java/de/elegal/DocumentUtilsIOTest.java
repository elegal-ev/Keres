package de.elegal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DocumentUtilsIOTest extends DocumentUtilsTest{
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

    @Test
    public void hasHeadline() throws Exception{
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(1, numberOfDeepChecks(allStrings, "Headline"));
    }

    @Test
    public void hasTag() throws Exception{
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(1, numberOfDeepChecks(allStrings,
                DocumentUtils.OPENING_TAG + "Tag" + DocumentUtils.CLOSING_TAG));
    }

    @Test
    public void hasNotRubbish() throws Exception{
        List<String> allStrings = getAllStrings(TEST_FILE);
        Assert.assertEquals(0, numberOfDeepChecks(allStrings, "Rubbish"));
    }

    @Test
    public void writeFile() throws Exception{
        XWPFDocument oldTest = createDocument(TEST_FILE);

        String outFilePath = TEST_PATH + "HeadlineTest.docx";
        XWPFDocument newTest = saveAndLoadAgain(oldTest, outFilePath);
        Assert.assertEquals(getAllStrings(oldTest), getAllStrings(newTest));
    }
}