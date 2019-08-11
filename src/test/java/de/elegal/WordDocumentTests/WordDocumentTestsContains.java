package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static de.elegal.WordDocumentTests.FinalPaths.*;

// TODO
public class WordDocumentTestsContains {
    @Before
    public void setUp(){

    }
    @Test
    public void containsTag1() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains("Tag1"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void containsTagInNewLine() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains("Newline"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void containsTagWithSpaces() throws Exception{
        WordDocument wordDocument = new WordDocument(TESTING_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertTrue(allTags.contains(" Tags with awkward spaces"));
        wordDocument.closeWithoutSaving();
    }
    @Test
    public void getTagsOnEmptyTags() throws Exception{
        WordDocument wordDocument = new WordDocument(EMPTY_FILE);
        Set<String> allTags = wordDocument.getTags();
        Assert.assertEquals(0, allTags.size());
        wordDocument.closeWithoutSaving();
    }
}
