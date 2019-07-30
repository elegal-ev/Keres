package de.elegal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Test Class for validating the tags in the Document constructor via regex
 *
 * @author Lars Quentin, Valerius Mattfeld
 */
public class DocumentUtilsTagsTest extends DocumentUtilsTest {
    /**
     * The Apache POI internal document
     */
    private XWPFDocument doc;

    /**
     * Before method. Sets up a document every time we start a test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        doc = DocumentUtils.openFile(TEST_PATH + "RegEx.docx");
    }

    /**
     * Checks whether it counts as extected.
     * Here its important that we expect a normal Set, not a MultiSet
     * Therefore multiple entries get counted a single time.
     */
    @Test
    public void numberOfTags() {
        int length = DocumentUtils.getAllTags(doc).size();
        Assert.assertEquals(5, length);
    }

    /**
     * Simple Test whether a Tag exists
     */
    @Test
    public void testTagExists() {
        Set<String> allTags = DocumentUtils.getAllTags(doc);
        Assert.assertTrue(allTags.contains("TestTag"));
    }

    /**
     * Test whether a Tag, which exists more than 1 time, exist
     */
    @Test
    public void adressTagExists() {
        Set<String> allTags = DocumentUtils.getAllTags(doc);
        Assert.assertTrue(allTags.contains("Adress"));
    }

    /**
     * Tests whether not existing tags are not within our list
     */
    @Test
    public void blaTagNotExist() {
        Set<String> allTags = DocumentUtils.getAllTags(doc);
        Assert.assertFalse(allTags.contains("bla"));
    }
}
