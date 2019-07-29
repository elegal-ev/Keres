package de.elegal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public abstract class Document {
    private XWPFDocument doc;

    protected String path;
    protected HashSet<String> allVariables;
    protected HashSet<String> variablesStillExisting;

    protected Document(final String path, final Collection<String> variables)
            throws IOException, InvalidFormatException, NullPointerException {
        Objects.requireNonNull(variables);
        this.allVariables = new HashSet<>(variables);
        this.variablesStillExisting = new HashSet<>(variables);

        this.path = path;
        createDocument();
    }

    private void createDocument() throws IOException, InvalidFormatException {
        try {
            this.doc = DocumentUtils.openFile(path);
        } catch (InvalidFormatException | NullPointerException ex) {
            System.err.println("The given path was invalid");
            throw ex;
        } catch (IOException ex) {
            System.err.println("A problem occured while parsing the Word-Document");
            throw ex;
        }
    }

    public void saveFile(final String path) {
        try {
            DocumentUtils.writeFile(doc, path);
        } catch (IOException ex) {
            System.err.println("An problem occured while saving the word document");
        }
    }

    public void replace(String tag, String string) {
        DocumentUtils.replaceAllTags(doc, tag, string);
    }

    public boolean isReplaceable(String string) {
        return this.variablesStillExisting.contains(string);
    }

    public ArrayList<String> getAllVariables() {
        return new ArrayList<>(this.allVariables);
    }

    public ArrayList<String> getExistingVariables() {
        return new ArrayList<>(this.variablesStillExisting);
    }
}
