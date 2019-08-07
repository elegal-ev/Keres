package de.elegal;

import de.elegal.Utils.DocumentUtils;
import de.elegal.Utils.Tuple;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.util.*;

/**
 * The abstract document class to derive from
 *
 * @author Valerius Mattfeld, Lars Quentin
 */
public abstract class Document {
    /**
     * The Apache POI internal docx Object
     */
    private XWPFDocument doc;

    /**
     * The file path
     */
    protected String path;

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
     *
     * @param path the file path
     * @param tags all tags which should be replaced
     */
    protected Document(final String path, final Collection<String> tags)
            throws IOException, InvalidFormatException, NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(tags);
        this.allTags = new HashSet<>(tags);
        this.tagsStillExisting = new HashSet<>(tags);

        this.path = path;
        createDocument();
        validateTags();
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
     * Proper tag verification.
     * If there are any tags in the word document, which were not given by the constructor, its invalid.
     * Vice versa, if there are any tags given by the constructor which are not in the word document, we just remove them
     * from the list of possible replaces.
     *
     * @throws IllegalArgumentException if there are unsatisfied tags in the word document
     */
    private void validateTags() throws IllegalArgumentException {
        final Set<String> allDocumentTags = DocumentUtils.getAllTags(this.doc);

        // At first, we have to check whether all document tasks are given via the constructor
        if (!this.allTags.containsAll(allDocumentTags))
            throw new IllegalArgumentException("Not all Document tasks are provided by the constructor");

        // Then we filter out those who are given by the constructor but not in the document
        // The iterator is needed otherwise we could get concurrentmodificationexceptions
        // by removing on the object itself
        this.allTags.removeIf(s -> !allDocumentTags.contains(s));
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
