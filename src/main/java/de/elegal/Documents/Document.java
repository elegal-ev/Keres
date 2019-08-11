package de.elegal.Documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.*;

public abstract class Document {

    // TODO: Can we close the Apache stuff right afterwards?
    // TODO: Write Rules for how it should be specified
    protected abstract void openFile(String path) throws InvalidFormatException, IOException;
    public abstract void saveAndCloseFile(String path) throws IOException;
    public abstract void closeWithoutSaving() throws IOException;

    public int replaceTag(final String oldString, final String newString) {
        return replaceString(stringToTag(oldString), newString);
    }

    protected abstract int replaceString(final String oldString, final String newString);

    protected Map<String, Integer> replaceAllStrings(final Map<String, String> replacements) throws IllegalArgumentException{
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
    public Map<String, Integer> replaceAllTags(final Map<String, String> replacements) throws IllegalArgumentException {
        Objects.requireNonNull(replacements);
        Map<String, String> replacementTags = new HashMap<>();
        for (Map.Entry<String, String> replacement : replacements.entrySet()) {
            replacementTags.put(stringToTag(replacement.getKey()), replacement.getValue());
        }
        return replaceAllStrings(replacementTags);
    }

    protected String stringToTag(final String str) {
        Objects.requireNonNull(str);
        return getOpeningTag() + str + getClosingTag();
    }


    public abstract String getOpeningTag();
    public abstract String getClosingTag();

    public abstract Set<String> getTags();
    public abstract String getText();
}
