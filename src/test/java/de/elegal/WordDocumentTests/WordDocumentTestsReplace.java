package de.elegal.WordDocumentTests;

import de.elegal.Documents.WordDocument;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.elegal.WordDocumentTests.FinalPaths.*;

/**
 * Some Test cases whether text replacement on some edge cases still works.
 */
public class WordDocumentTestsReplace {
    /**
     * The worddocument object. Gets reloaded before every test
     */
    private WordDocument wordDocument;
    /**
     * The Replacement text.
     */
    private final String replacedWith = "New Text!";

    /**
     * Setting up every test by creating a new WordDocument-Object
     *
     * @throws Exception Some IO Stuff mostly
     */
    @Before
    public void setUp() throws Exception {
        wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
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
     * The simplest replacement possible
     */
    @Test
    public void replaceTag() {
        int numberOfReplacements = wordDocument.replaceTag("Test", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
    }

    /**
     * Checks whether multiple replacements work
     */
    @Test
    public void replaceMultipleTimes() {
        int numberOfReplacements = wordDocument.replaceTag("Tag", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(2, numberOfReplacements);
    }

    /**
     * Checks whether replacement with some spaces within the tag works
     */
    @Test
    public void replaceAwkwardSpaces() {
        int numberOfReplacements = wordDocument.replaceTag(" Tags with awkward spaces",
                replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
    }

    /**
     * Checks whether a tag within a paragraph can be replaced
     */
    @Test
    public void replaceInline() {
        int numberOfReplacements = wordDocument.replaceTag("cool", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
    }

    /**
     * Checks whether right bound tags can be replaced
     */
    @Test
    public void replaceRightBound() {
        int numberOfReplacements = wordDocument.replaceTag("RightBound", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
    }

    /**
     * The Endgegner. A lot of different formatting within the string, this especially is
     * straining on the replacement through multiple runs
     */
    @Test
    public void replaceEndgegner() {
        int numberOfReplacements = wordDocument.replaceTag("Endgegner", replacedWith);
        Assert.assertTrue(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(1, numberOfReplacements);
    }

    /**
     * Checks whether a non existing tag is not replaced
     */
    @Test
    public void replaceNonExisting() {
        int numberOfReplacements = wordDocument.replaceTag("hahahdalsdjaksldjklsa", replacedWith);
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
        Assert.assertEquals(0, numberOfReplacements);
    }
}
