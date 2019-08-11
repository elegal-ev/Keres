package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.elegal.WordDocumentTests.FinalPaths.*;

/**
 * Test cases for an empty document
 */
public class WordDocumentEmpty {
    /**
     * The worddocument object. Gets reloaded before every test
     */
    private WordDocument wordDocument;

    /**
     * Setting up every test by creating a new WordDocument-Object
     *
     * @throws Exception Some IO Stuff mostly
     */
    @Before
    public void setUp() throws Exception {
        wordDocument = new WordDocument(EMPTY_FILE);
    }

    /**
     * Closes the fs file lock after every test that the next setUp won't fail
     *
     * @throws Exception Some IO Stuff which will most likely never happen
     */
    @After
    public void tearDown() throws Exception {
        wordDocument.closeWithoutSaving();
    }

    /**
     * Checks whether it has no tags.
     */
    @Test
    public void noTags() {
        Assert.assertEquals(wordDocument.getTags().size(), 0);
    }

    /**
     * Checks whether it doesn't contain some random Tag
     */
    @Test
    public void doesntContainStuff() {
        Assert.assertFalse(wordDocument.getTags().contains("Stuff"));
    }

    /**
     * Checks whether it replaces nothing.
     */
    @Test
    public void doesntReplaceNothing() {
        Assert.assertEquals(wordDocument.replaceTag("Nothing", "Nothing"), 0);
    }
}
