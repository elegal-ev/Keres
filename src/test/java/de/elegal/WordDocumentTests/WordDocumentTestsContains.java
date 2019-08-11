package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static de.elegal.WordDocumentTests.FinalPaths.*;

/**
 * Some Test cases whether the tags work in some edge cases
 */
public class WordDocumentTestsContains {
    /**
     * The worddocument object. Gets reloaded before every test
     */
    private WordDocument wordDocument;
    /**
     * All Tags.
     */
    private Set<String> allTags;

    /**
     * Setting up every test by creating a new WordDocument-Object and relaods all tags
     *
     * @throws Exception Some IO Stuff mostly
     */
    @Before
    public void setUp() throws Exception {
        wordDocument = new WordDocument(TESTING_FILE);
        allTags = wordDocument.getTags();
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
     * Checks whether it contains a simple Tag
     */
    @Test
    public void containsTag() {
        Assert.assertTrue(allTags.contains("Test"));
    }

    /**
     * Checks whether it contains a Tag which exists on multiple occurences
     */
    @Test
    public void containsMultipleTags() {
        Assert.assertTrue(allTags.contains("Tag"));
    }

    /**
     * Checks whether it contains a Tag with spaces
     */
    @Test
    public void containsAwkwardSpaces() {
        Assert.assertTrue(allTags.contains(" Tags with awkward spaces"));
    }

    /**
     * Checks whether it contains a Tag within a Paragraph
     */
    @Test
    public void containsInline() {
        Assert.assertTrue(allTags.contains("cool"));
    }

    /**
     * Checks whether it contains a right bound tag
     */
    @Test
    public void containsRightBound() {
        Assert.assertTrue(allTags.contains("RightBound"));
    }

    /**
     * Checks whether it contains a multiple formatted tag
     */
    @Test
    public void containsEndgegner() {
        Assert.assertTrue(allTags.contains("Endgegner"));
    }

    /**
     * Checks whether it doesn't contain a non existing tag
     */
    @Test
    public void containsNonExisting() {
        Assert.assertFalse(allTags.contains("hahasdjklasjdklasjkld"));
    }
}
