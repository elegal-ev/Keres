package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static de.elegal.WordDocumentTests.FinalPaths.*;

public class WordDocumentTestsSimple {
    @Test
    public void canOpenDocument() throws Exception {
        new WordDocument(TESTING_FILE).closeWithoutSaving();
    }
    @Test
    public void openEmptyDocument() throws Exception{
        new WordDocument(EMPTY_FILE).closeWithoutSaving();
    }
    @Test
    public void notExistingDocumentFails(){
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
    @Test
    public void canSaveAndRemoveDocument() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String newPath = TEST_PATH + "Lol.docx";
        wordDocument.saveAndCloseFile(newPath);
        File file = new File(newPath);
        if (!file.exists()) Assert.fail("File wasn't saved");
        if (!file.delete()) Assert.fail("File wasn't deleted");
    }
    @Test
    public void throwsExceptionWhileSavingWhenFileExistsAlready() throws Exception{
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
    @Test
    public void getOpeningTag() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertEquals("<", wordDocument.getOpeningTag());
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getClosingTag() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertEquals(">", wordDocument.getClosingTag());
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getOPENING_TAG(){
        Assert.assertEquals("<", WordDocument.OPENING_TAG);
    }
    @Test
    public void getCLOSING_TAG(){
        Assert.assertEquals(">", WordDocument.CLOSING_TAG);
    }
    @Test
    public void getText() throws Exception{ // TODO: Comment on why just partial tests
        // TODO: Further examples now with the new one
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String text = wordDocument.getText();
        wordDocument.closeWithoutSaving();
        Assert.assertTrue(text.contains("Lorem ipsum"));
        Assert.assertTrue(text.contains("Headline"));
        Assert.assertTrue(text.contains("<Tag1>"));
    }
}
