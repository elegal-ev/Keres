package de.elegal.Documents;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Objects;

// TODO: Recode
class Replacer {
    private String toBeReplaced;
    private String replacedWith;
    private int numberOfReplacementsDone = 0;

    Replacer(String toBeReplaced, String replacedWith) {
        Objects.requireNonNull(toBeReplaced);
        Objects.requireNonNull(replacedWith);
        this.toBeReplaced = toBeReplaced;
        this.replacedWith = replacedWith;
    }

    int replace(XWPFDocument document) {
        Objects.requireNonNull(document);
        document.getParagraphs().forEach(this::replace);
        return numberOfReplacementsDone; // TODO: Reset
    }

    private void replace(XWPFParagraph paragraph) {
        if (paragraph == null) return;
        String text = paragraph.getText();
        this.numberOfReplacementsDone += countMatches(text, toBeReplaced);
        text = text.replace(toBeReplaced, replacedWith);
        removeAllRuns(paragraph);
        insertFixedRuns(paragraph, text);
    }

    private void removeAllRuns(XWPFParagraph paragraph) {
        int size = paragraph.getRuns().size();
        for (int i=0; i<size; i++) paragraph.removeRun(0);
    }

    private void insertFixedRuns(XWPFParagraph paragraph, String replacedString) {
        // TODO
        String[] replacementTextSplitOnCarriageReturn = replacedString.split("\n");

        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
            String part = replacementTextSplitOnCarriageReturn[j];

            XWPFRun newRun = paragraph.insertNewRun(j);
            newRun.setText(part);

            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                newRun.addCarriageReturn();
            }
        }
    }

    private int countMatches(String string, String subString) {
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
