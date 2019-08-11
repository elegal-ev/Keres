package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.Assert;
import org.junit.Test;

import static de.elegal.WordDocumentTests.FinalPaths.*;

public class WordDocumentTestsReplace {
    @Test
    public void replaceTag1() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        int numberOfReplacements = wordDocument.replaceTag("Tag1", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void replaceTagInNewLine() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        int numberOfReplacements = wordDocument.replaceTag("Newline", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
        wordDocument.closeWithoutSaving();

    }
    @Test
    public void replaceTagWithSpaces() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String replacedWith = "New Text!";
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        int numberOfReplacements = wordDocument.replaceTag(" Tags with awkward spaces", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void replaceTagNotExisting() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        String notExisting = "Asdf";
        Assert.assertFalse(wordDocument.getText().contains(notExisting));
        int numberOfReplacements = wordDocument.replaceTag(notExisting, notExisting);
        Assert.assertFalse(wordDocument.getText().contains(notExisting));
        Assert.assertEquals(0, numberOfReplacements);
    }
}
