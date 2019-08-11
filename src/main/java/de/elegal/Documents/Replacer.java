package de.elegal.Documents;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Objects;

import static de.elegal.Utils.StringUtils.countMatches;

/**
 * The advanced text replacer.
 * This is needed because of docx internals. Best explained by an example:
 * For example, we have the String "<TagInNewLine>" somewhere in our document.
 * Normally, this should be within one run, because there is no reason not to.
 * At least, if you are not completely Word-Developer insane.
 * But, since "TagInNewLine" is not a real word, it's splitted in
 * <w:p w:rsidR="00240200" w:rsidRDefault="00240200">
 * <w:r>
 * <w:t>&lt;</w:t>
 * </w:r>
 * <w:proofErr w:type="spellStart"/>
 * <w:r>
 * <w:t>TagInNewLine</w:t>
 * </w:r>
 * <w:proofErr w:type="spellEnd"/>
 * <w:r>
 * <w:t>&gt;</w:t>
 * </w:r>
 * </w:p>
 * Therefore, __if__ it really exists, we have to do an expensive lookup over multiple runs.
 * Also, we theoretically could work on paragraphs only, but that would destroy any kind
 * of formatting within the paragraph.
 * Thats because "spellStart" is not the only type a run can have and setting the text for a paragraph
 * would loose all those Attributes.
 * See https://stackoverflow.com/a/25186678 for further informations.
 */
class Replacer {
    /**
     * The String to be replaced
     */
    private final String toBeReplaced;
    /**
     * The String to replace it with
     */
    private final String replacedWith;
    /**
     * The number of replacements
     */
    private int numberOfReplacementsDone = 0;

    /**
     * The constuctor
     *
     * @param toBeReplaced The string to be replaced. Can't be null
     * @param replacedWith The string to replace it with. Can't be null
     */
    Replacer(String toBeReplaced, String replacedWith) {
        Objects.requireNonNull(toBeReplaced);
        Objects.requireNonNull(replacedWith);
        this.toBeReplaced = toBeReplaced;
        this.replacedWith = replacedWith;
    }

    /**
     * The function to replace. The only public function
     *
     * @param document The document reference to do the replacements at.
     * @return The number of replacements done
     */
    int replace(XWPFDocument document) {
        Objects.requireNonNull(document);
        document.getParagraphs().forEach(this::replace);
        int res = numberOfReplacementsDone;
        numberOfReplacementsDone = 0;
        return res;
    }

    /**
     * Replacement for a single paragraph.
     *
     * @param paragraph The paragraph reference to do the replacements at.
     */
    private void replace(XWPFParagraph paragraph) {
        if (paragraph == null) return;
        String text = paragraph.getText();
        this.numberOfReplacementsDone += countMatches(text, toBeReplaced);
        text = text.replace(toBeReplaced, replacedWith);
        removeAllRuns(paragraph);
        insertFixedRuns(paragraph, text);
    }

    /**
     * A helper function to remove all runs
     *
     * @param paragraph
     */
    private void removeAllRuns(XWPFParagraph paragraph) {
        int size = paragraph.getRuns().size();
        for (int i = 0; i < size; i++) paragraph.removeRun(0);
    }

    /**
     * The function to replace the new replacements with
     *
     * @param paragraph      the paragraph to replace
     * @param replacedString the string with the new text
     */
    private void insertFixedRuns(XWPFParagraph paragraph, String replacedString) {
        String[] replacementTextSplitOnCarriageReturn = replacedString.split("\n");
        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
            String part = replacementTextSplitOnCarriageReturn[j];
            XWPFRun newRun = paragraph.insertNewRun(j);
            newRun.setText(part);
            if (j + 1 < replacementTextSplitOnCarriageReturn.length) newRun.addCarriageReturn();
        }
    }


}
