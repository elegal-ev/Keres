package de.elegal.GUI;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO: Visual Feedback
// TODO: docx in file chooser
public class SinglePanel extends BasePanel {
    private JTextField inputDocx = new JTextField("Select a Word File");

    private HashMap<String, JTextField> inputs = null;

    public SinglePanel() {
        super();
        inputDocx.setEditable(false);
        add(super.docxButton);
        add(inputDocx);
    }

    @Override
    void updateGUIAfterLoadingDocx() {
        clearInputs();
        createInputs();
        add(applyButton);
    }

    @Override
    boolean inputsValid() {
        if (inputs == null) return false;
        return inputs.entrySet().stream()
                .noneMatch(
                        entry -> entry.getValue().getText().equals("Replace for " + entry.getKey())
                );
    }

    @Override
    void applyReplacements() {
        HashMap<String, String> replacements = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : inputs.entrySet()) {
            replacements.put(entry.getKey(), entry.getValue().getText());
        }
        template.replaceAllTags(replacements);
    }

    private void clearInputs() {
        if (inputs == null) return;
        inputs.keySet().forEach(tag -> remove(inputs.get(tag)));
    }

    private void createInputs() {
        Set<String> tags = template.getTags();
        if (tags.size() == 0) {
            System.out.println("No tags existing, doing nothing :/");
            return;
        }
        inputs = new HashMap<>();
        for (String tag : tags) {
            // TODO: Adding labels after proper layout
            // TODO: Make function for text to check against
            JTextField tagfield = new JTextField("Replace for " + tag);
            add(tagfield);
            System.out.println(tag + " " + tagfield);
            inputs.put(tag, tagfield);
        }
        updateUI();
    }
}
