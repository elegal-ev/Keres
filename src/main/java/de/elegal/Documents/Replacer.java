package de.elegal.Documents;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Objects;

import static de.elegal.Utils.StringUtils.countMatches;

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


}
