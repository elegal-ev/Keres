package de.elegal;

import de.elegal.Documents.WordDocument;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class WordDocumentTests {
    private static final String TEST_PATH = System.getProperty("user.dir") + "/src/test/resources/docx/";
    private static final String EMPTY_FILE = TEST_PATH + "Empty.docx";
    private static final String HEADLINE_FILE = TEST_PATH + "Headline.docx";
    private static final String REGEX_FILE = TEST_PATH + "RegEx.docx";
    private static final String XLSX_FILE = TEST_PATH + "ActuallyNotADocX.xlsx";

    @Test
    public void canOpenDocument() throws Exception {
        new WordDocument(HEADLINE_FILE).closeWithoutSaving();
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
            // Cleaning up unclosed ZipFile for archive path/to/repo/src/test/resources/docx/ActuallyNotADocX.xlsx
            new WordDocument(XLSX_FILE);
        } catch (Exception ex) {
            System.err.flush();
            return;
        }
        Assert.fail("was able to open xlsx files");
    }
    @Test
    public void canSaveAndRemoveDocument() throws Exception{
        WordDocument wordDocument = new WordDocument(HEADLINE_FILE);
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
        WordDocument wordDocument = new WordDocument(HEADLINE_FILE);
        try {
            wordDocument.saveAndCloseFile(tempPath);
        } catch (Exception ex) {
            if (!file.delete())
                throw new IOException("Internal Test Error deleting the test file.");
            return;
        }
        if (!file.delete())
            throw new IOException("Internal Test Error deleting the test file.");
        Assert.fail();
    }
    @Test
    public void getOpeningTag() throws Exception{
        WordDocument wordDocument = new WordDocument(HEADLINE_FILE);
        Assert.assertEquals("<", wordDocument.getOpeningTag());
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getClosingTag() throws Exception{
        WordDocument wordDocument = new WordDocument(HEADLINE_FILE);
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
    public void containsTag1() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains("Tag1"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void containsTagInNewLine() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains("TagInNewLine"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void containsTagWithSpaces() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains("Tag with spaces"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getTagsOnEmptyTags() throws Exception{
        WordDocument wordDocument = new WordDocument(EMPTY_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertEquals(0, allTags.size());
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getText() throws Exception{ // TODO: Comment on why just partial tests
        WordDocument wordDocument = new WordDocument(HEADLINE_FILE);
        String text = wordDocument.getText();
        wordDocument.closeWithoutSaving();
        Assert.assertTrue(text.contains("Lorem ipsum"));
        Assert.assertTrue(text.contains("Headline"));
        Assert.assertTrue(text.contains("<Tag>"));
    }
    @Test
    public void replaceTag1() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        wordDocument.replaceTag("Tag1", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void replaceTagInNewLine() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        wordDocument.replaceTag("TagInNewLine", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        wordDocument.closeWithoutSaving();

    }
    @Test
    public void replaceTagWithSpaces() throws Exception{
        WordDocument wordDocument = new WordDocument(REGEX_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        wordDocument.replaceTag("Tag with spaces", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void replaceTagNotExisting() throws Exception{}
    // TODO: Some null check tests
    // TODO: Write a lot of test cases whether the run splitting is fucked up
    // TODO: Check for wrong written stuff
    // TODO: Also with a lot of extra formatting stuff
}
