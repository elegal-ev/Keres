package de.elegal.Utils;

/**
 * Some string utils.
 */
public class StringUtils {
    /**
     * Counts how often a substring is in a string
     *
     * @param string    the string to count substrings in
     * @param subString the substring to be counted.
     * @return the number of occurences
     */
    public static int countMatches(String string, String subString) {
        int count = 0, lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = string.indexOf(subString, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += subString.length();
            }
        }
        return count;
    }
}
