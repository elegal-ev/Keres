package de.elegal.GUI;

import de.elegal.Documents.WordDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// TODO: Visual Feedback
// TODO: docx in file chooser
public class SinglePanel extends JPanel {
    private JButton docxButton = new JButton("Open");
    private JTextField inputDocx = new JTextField("Select a Word File");

    private String selectedPath = null;
    private WordDocument template = null;

    private HashMap<String, JTextField> inputs = null;
    private JButton applyButton = null;

    public SinglePanel() {
        inputDocx.setEditable(false);
        docxButton.addActionListener((ignored) -> selectFile().ifPresent(this::updatePanel));
        add(docxButton);
        add(inputDocx);
    }

    private Optional<String> selectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Word Documents", "docx"));
        chooser.setAcceptAllFileFilterUsed(false);
        return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                ? Optional.of(chooser.getSelectedFile().getAbsolutePath())
                : Optional.empty();
    }

    private void updatePanel(String selectedPath) {
        loadWordDocument(selectedPath);
        clearInputs();
        createInputs();
        addApplyButton();
    }

    private void loadWordDocument(String selectedPath) {
        this.selectedPath = selectedPath;
        try {
            template = new WordDocument(selectedPath);
        } catch (Exception ex) {
            System.err.println("An Error occured loading:\n" + selectedPath);
            System.err.println(ex);
        }
    }

    private void clearInputs() {
        if (inputs == null) return;
        for (String tag : inputs.keySet()) {
            remove(inputs.get(tag));
        }
    }

    private void createInputs() {
        // Deleting the oldinput fields via garbage collector
        // TODO: Test whether that works how I'm expecting
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

    private void addApplyButton() {
        if (applyButton != null) return;
        applyButton = new JButton("Save");
        applyButton.addActionListener((ignored) -> {
            if (allTagsFilled()) {
                applyTags();
                saveWordDocument();
            }
        });
        add(applyButton);
    }

    private boolean allTagsFilled() {
        if (inputs == null) return false;
        return inputs.entrySet().stream()
                .noneMatch(
                        entry -> entry.getValue().getText().equals("Replace for " + entry.getKey())
                );
    }

    private void applyTags() {
        HashMap<String, String> replacements = new HashMap<>();
        for (Map.Entry<String, JTextField> entry : inputs.entrySet()) {
            replacements.put(entry.getKey(), entry.getValue().getText());
        }
        template.replaceAllTags(replacements);
    }

    private void saveWordDocument() {
        JFileChooser chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                if (f.exists() && getDialogType() == JFileChooser.SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "The file exists, overwrite?",
                            "Existing file",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                        case JOptionPane.CLOSED_OPTION:
                        case JOptionPane.NO_OPTION:
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;
        try {
            template.saveAndCloseFile(chooser.getSelectedFile().getAbsolutePath());
        } catch (Exception ex) {
            System.err.println("File could not be saved");
            System.err.println(ex);
        }
    }
}
