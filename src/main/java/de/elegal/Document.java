package de.elegal;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public abstract class Document {
    /**
     * The Apache POI internal docx Object
     */
    private XWPFDocument doc;

    /**
     * The file path
     */
    protected String path;  // filepath

    /**
     * Set of all possible tags for a given document
     */
    protected HashSet<String> allTags;

    /**
     * Set of all tags which are not substituted.
     * This should be 0 when the document is finished
     */
    protected HashSet<String> tagsStillExisting;

    /**
     * The protected Contructor. Since Document is abstract it is just derivable.
     * @param path the file path
     * @param tags all tags which should be replaced
     */
    protected Document(final String path, final Collection<String> tags)
            throws IOException, InvalidFormatException, NullPointerException {
        Objects.requireNonNull(tags);
        this.allTags = new HashSet<>(tags);
        this.tagsStillExisting = new HashSet<>(tags);

        this.path = path;
        createDocument();
    }

    /**
     * creates an apache doc from a given string.
     *
     * @throws IOException            ex {@link IOException}
     * @throws InvalidFormatException ex {@link InvalidFormatException}
     */
    private void createDocument() throws IOException, InvalidFormatException {
        try {
            this.doc = DocumentUtils.openFile(path);  // creates an apache doc at the given path
        } catch (InvalidFormatException | NullPointerException ex) {
            System.err.println("The given path was invalid");
            throw ex;
        } catch (IOException ex) {
            System.err.println("A problem occured while parsing the Word-Document");
            throw ex;
        }
    }

    /**
     * saves a file at the very end on a given path. Can be called at any given time.
     *
     * @param path destination path
     */
    public void saveFile(final String path) {
        try {
            DocumentUtils.writeFile(doc, path);
        } catch (IOException ex) {
            System.err.println("An problem occured while saving the word document");
        }
    }

    /**
     * Replaces the tag with the substitution.
     *
     * @param tag    the tag
     * @param string the string
     * @return whether its actually replaced or not.
     */
    public boolean replace(String tag, String string) {
        Tuple<Integer, XWPFDocument> ret = DocumentUtils.replaceAllTags(doc, tag, string);
        return ret.getFirst() != 0;
    }

    /**
     * checks if a substitution is applied to the document
     *
     * @param string the substitution
     * @return whether its applied or not.
     */
    public boolean isReplaceable(String string) {
        // FIXME document check
        return this.tagsStillExisting.contains(string);
    }

    /**
     * All possible tags, even if they are processed
     *
     * @return possible tags
     */
    public ArrayList<String> getAllTags() {
        return new ArrayList<>(this.allTags);
    }

    /**
     * All tags that are not yet parsed.
     *
     * @return not applied tags.
     */
    public ArrayList<String> getExistingtags() {
        return new ArrayList<>(this.tagsStillExisting);
    }
}
