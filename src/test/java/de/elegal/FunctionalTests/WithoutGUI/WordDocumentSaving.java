package de.elegal.FunctionalTests.WithoutGUI;

import de.elegal.Documents.WordDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static de.elegal.WordDocumentTests.FinalPaths.*;

public class WordDocumentSaving {
    private WordDocument wordDocument;
    private String replacedWith = "Oh God oh please";

    @Before
    public void setUp() throws Exception {
        wordDocument = new WordDocument(TESTING_FILE);
        Assert.assertFalse(wordDocument.getText().contains(replacedWith));
    }

    @Test
    public void replaceAndSaveDoesNotChangeDocument() throws Exception{
        wordDocument.replaceTag("Test", replacedWith);
        wordDocument.saveAndCloseFile(TEMP_FILE);
        if (!new File(TEMP_FILE).delete()) {
            throw new IOException("Wasn't able to delete the TEMP_FILE");
        }
        WordDocument oldDocument = new WordDocument(TESTING_FILE);
        Assert.assertFalse(oldDocument.getText().contains(replacedWith));
    }
}
