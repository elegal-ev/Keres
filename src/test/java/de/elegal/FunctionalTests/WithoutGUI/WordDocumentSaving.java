package de.elegal.FunctionalTests.WithoutGUI;

import de.elegal.Documents.WordDocument;
import org.junit.After;
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

    @After
    public void tearDown() throws Exception {
        File file = new File(TEMP_FILE);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) throw new IOException("File could not be deleted");
        }
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

    @Test
    public void replaceAndSaveAndLoadThenValidateIfItReallyReplaced() throws Exception {
        wordDocument.replaceTag("Test", replacedWith);
        wordDocument.saveAndCloseFile(TEMP_FILE);
        WordDocument reload = new WordDocument(TEMP_FILE);
        Assert.assertTrue(reload.getText().contains(replacedWith));
    }

    @Test
    public void noOperationDoesNothingWhenReloaded() throws Exception {
        wordDocument.saveAndCloseFile(TEMP_FILE);
        WordDocument reload = new WordDocument(TEMP_FILE);
        Assert.assertEquals(wordDocument.getText(), reload.getText());
    }
}
