package de.elegal.Documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.*;

/**
 * The base class for all document types.
 */
public abstract class Document {

    /**
     * An abstract method for opening a File.
     *
     * @param path The path (can be both relative and absolute) to the document
     * @throws InvalidFormatException If the string is malformatted
     * @throws IOException            When some problems with the file system occur (like file locks or alike)
     */
    protected abstract void openFile(String path) throws InvalidFormatException, IOException;

    /**
     * An abstract method for closing the file.
     *
     * @param path The path (can be both relative and absolute) where the document should be saved
     * @throws IOException When some problems with the file system occur (like file locks or alike)
     */
    public abstract void saveAndCloseFile(String path) throws IOException;

    /**
     * Closing the file access without saving
     *
     * @throws IOException When some problems with the file system occur (like file locks or alike)
     */
    public abstract void closeWithoutSaving() throws IOException;

    /**
     * The replace Function for a tag.
     *
     * @param oldString the old string
     * @param newString the new string
     * @return the number of replacements
     */
    public int replaceTag(final String oldString, final String newString) {
        return replaceString(stringToTag(oldString), newString);
    }

    /**
     * Internal method for replaceTag to replace Strings
     *
     * @param oldString the old string
     * @param newString the new sting
     * @return the number of repalcements
     */
    protected abstract int replaceString(final String oldString, final String newString);

    /**
     * an internal method to replace multiple strings
     *
     * @param replacements a map of repalcements
     * @return A map of the strings and the number it was replaced
     * @throws IllegalArgumentException If any value is null when the key isnt
     */
    protected Map<String, Integer> replaceAllStrings(final Map<String, String> replacements) throws IllegalArgumentException {
        Objects.requireNonNull(replacements);
        boolean invalidReplacements = replacements.entrySet().stream().anyMatch(
                x -> (x.getKey() != null && x.getValue() == null)
        );
        if (invalidReplacements) {
            throw new IllegalArgumentException("Values can't be null if Keys are not null");
        }
        HashMap<String, Integer> res = new HashMap<>();
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            res.put(replacement.getKey(),
                    replaceString(replacement.getKey(), replacement.getValue()));
        }
        return res;
    }

    /**
     * A function to replace multiple tags
     *
     * @param replacements a map of replacements
     * @return A map of the strings and the number it was replaced
     * @throws IllegalArgumentException If any value is null when the key isnt
     */
    public Map<String, Integer> replaceAllTags(final Map<String, String> replacements) throws IllegalArgumentException {
        Objects.requireNonNull(replacements);
        Map<String, String> replacementTags = new HashMap<>();
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            replacementTags.put(stringToTag(replacement.getKey()), replacement.getValue());
        }
        return replaceAllStrings(replacementTags);
    }

    /**
     * A helper method to convert a string to a tag
     *
     * @param str The nontaggy string
     * @return The taggy string
     */
    protected String stringToTag(final String str) {
        Objects.requireNonNull(str);
        return getOpeningTag() + str + getClosingTag();
    }


    /**
     * A getter for the opening tag
     *
     * @return the opening tag
     */
    public abstract String getOpeningTag();

    /**
     * A getter for the closing tag
     *
     * @return the closing tag
     */
    public abstract String getClosingTag();

    /**
     * Extracts all Tags from the document
     *
     * @return A set of tags
     */
    public abstract Set<String> getTags();

    /**
     * Extracts the text from a document
     *
     * @return the Text
     */
    public abstract String getText();
}
