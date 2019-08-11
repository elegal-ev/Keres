package de.elegal.Utils;

public class StringUtils {
    public static int countMatches(String string, String subString) {
        int count = 0, lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = string.indexOf(subString, lastIndex);
            if (lastIndex != -1) { // TODO: Refactor
                count++;
                lastIndex += subString.length();
            }
        }
        return count;
    }
}
