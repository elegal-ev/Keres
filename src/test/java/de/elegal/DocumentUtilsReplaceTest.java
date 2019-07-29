package de.elegal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class DocumentUtilsReplaceTest extends DocumentUtilsTest {

    @Test
    public void newHeadLine() throws Exception {
        XWPFDocument doc = createDocument(TEST_FILE);
        DocumentUtils.replaceAllStrings(doc, "Headline", "Hehedline");
        doc = saveAndLoadAgain(doc, TEST_PATH + "HeadLineTest.docx");
        List<String> allStrings = getAllStrings(doc);
        Assert.assertEquals(1, numberOfDeepChecks(allStrings, "Hehedline"));
    }

    @Test
    public void oldHeadLineMissing() throws Exception {
        XWPFDocument doc = createDocument(TEST_FILE);
        DocumentUtils.replaceAllStrings(doc, "Headline", "Hehedline");
        doc = saveAndLoadAgain(doc, TEST_PATH + "HeadLineTest.docx");
        List<String> allStrings = getAllStrings(doc);
        Assert.assertEquals(0, numberOfDeepChecks(allStrings, "Headline"));
    }

    @Test
    public void newTag() throws Exception {
        XWPFDocument doc = createDocument(TEST_FILE);
        DocumentUtils.replaceAllTags(doc, "Tag", "Yeah");
        doc = saveAndLoadAgain(doc, TEST_PATH + "HeadLineTest.docx");
        List<String> allStrings = getAllStrings(doc);
        for (String x : allStrings) {
            System.out.println(x);
        }
        Assert.assertEquals(1, numberOfDeepChecks(allStrings, "Yeah"));
    }

    @Test
    public void oldTagMissing() throws Exception {
        XWPFDocument doc = createDocument(TEST_FILE);
        DocumentUtils.replaceAllTags(doc, "Tag", "Yeah");
        doc = saveAndLoadAgain(doc, TEST_PATH + "HeadLineTest.docx");
        List<String> allStrings = getAllStrings(doc);
        for (String x : allStrings) {
            System.out.println(x);
        }
        Assert.assertEquals(0, numberOfDeepChecks(allStrings,
                DocumentUtils.OPENING_TAG + "Tag" + DocumentUtils.CLOSING_TAG));
    }
}
