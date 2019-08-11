package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import de.elegal.Utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static de.elegal.WordDocumentTests.FinalPaths.*;

/**
 * All mixed tests for which is there weren't enough use cases to put in an own class.
 */
public class WordDocumentTestsSimple {
    /**
     * A simple test if an existing docx file can be opened
     *
     * @throws Exception IOException if there are problems with the fs, other exceptions when there are problems with POI
     */
    @Test
    public void canOpenDocument() throws Exception {
        new WordDocument(TESTING_FILE).closeWithoutSaving();
    }

    /**
     * A simple test if an existing but empty docx file can be opened
     *
     * @throws Exception IOException if there are problems with the fs, other exceptions when there are problems with POI
     */
    @Test
    public void openEmptyDocument() throws Exception {
        new WordDocument(EMPTY_FILE).closeWithoutSaving();
    }

    /**
     * Check whether it fails when tried to open an non existing document
     */
    @Test
    public void notExistingDocumentFails() {
        WordDocument wordDocument;
        try {
            wordDocument = new WordDocument(TEST_PATH + "NotExisting.docx");
        } catch (Exception ex) {
            return;
        }
        try {
            wordDocument.closeWithoutSaving();
        } catch (IOException ignore) {
            System.err.println("Internal Problem while trying to close a nonexisting document");
        }
        Assert.fail();
    }

    /**
     * Check whether it fails when trying to open an xlsx file instead since it's some zipped xml stuff as well
     * <p>
     * Note: It currently throws the following message to stderr
     * "Cleaning up unclosed ZipFile for archive path path/to/repo/src/test/resources/docx/ActuallyNotADocX.xlsx"
     * Sadly, for now this is unfixable. I can't close the fs access since it happens async in the contructor
     * and it is intended that the constuctor fails.
     * Also, even if I flush the stderr afterwards, I can't redirect the stderr just for that test.
     */
    @Test
    public void nonWordFails() {
        try {
            // fixme I can't get rid of
            // "Cleaning up unclosed ZipFile for archive path/to/repo/src/test/resources/docx/ActuallyNotADocX.xlsx"
            new WordDocument(XLSX_FILE);
        } catch (Exception ex) {
            System.err.flush();
            return;
        }
        Assert.fail("was able to open xlsx files");
    }

    /**
     * Checks whether a file can be saved and removed afterwards
     *
     * @throws Exception Probably some IOException if for example the file already exists or an fs file lock is on it.
     */
    @Test
    public void canSaveAndRemoveDocument() throws Exception {
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String newPath = TEST_PATH + "Lol.docx";
        wordDocument.saveAndCloseFile(newPath);
        File file = new File(newPath);
        if (!file.exists()) Assert.fail("File wasn't saved");
        if (!file.delete()) Assert.fail("File wasn't deleted");
    }

    /**
     * Checks whether it fails when there is already a file existing.
     * it is intended that it fails since it's better than just losing the old file
     *
     * @throws Exception
     */
    @Test
    public void throwsExceptionWhileSavingWhenFileExistsAlready() throws Exception {
        String tempPath = TEST_PATH + "Lol.docx";
        File file = new File(tempPath);
        if (!file.createNewFile())
            throw new IOException("Internal Test Error creating the test file.");
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        boolean ok = false;
        try {
            wordDocument.saveAndCloseFile(tempPath);
        } catch (Exception ex) {
            ok = true;
        }
        if (!file.delete())
            throw new IOException("Internal Test Error deleting the test file.");
        if (!ok) Assert.fail();
    }

    /**
     * Checking whether getOpeningTag works
     *
     * @throws Exception IOException when creating the WordDocument object
     */
    @Test
    public void getOpeningTag() throws Exception {
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertEquals("<", wordDocument.getOpeningTag());
        wordDocument.closeWithoutSaving();
    }

    /**
     * Checking whether getClosingTag works
     *
     * @throws Exception IOException when creating the WordDocument object
     */
    @Test
    public void getClosingTag() throws Exception {
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertEquals(">", wordDocument.getClosingTag());
        wordDocument.closeWithoutSaving();
    }

    /**
     * Checking whether OPENING_TAG is set correctly
     */
    @Test
    public void getOPENING_TAG() {
        Assert.assertEquals("<", WordDocument.OPENING_TAG);
    }

    /**
     * Checking whether CLOSING_TAG is set correctly
     */
    @Test
    public void getCLOSING_TAG() {
        Assert.assertEquals(">", WordDocument.CLOSING_TAG);
    }

    /**
     * Checks whether the text contains everything expected.
     * Here, we are just checking partially for single words since it's word ordering is based on the docx internal
     * xml and we can't really trust M$ to not to awkward stuff
     *
     * @throws Exception Probably some IOException stuff when working with fs
     */
    @Test
    public void getText() throws Exception {
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String text = wordDocument.getText();
        wordDocument.closeWithoutSaving();
        Assert.assertTrue(text.contains("Headline"));
        Assert.assertTrue(text.contains("Lorem ipsum"));
        Assert.assertEquals(2, StringUtils.countMatches(text, "<Tag>"));
        Assert.assertTrue(text.contains("< Tags"));
        Assert.assertTrue(text.contains("<RightBound>"));
        Assert.assertTrue(text.contains("<Endgegner>"));
    }
}
