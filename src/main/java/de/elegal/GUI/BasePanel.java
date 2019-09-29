package de.elegal.GUI;

import de.elegal.Documents.WordDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Optional;

public abstract class BasePanel extends JPanel {
    JButton docxButton = new JButton("Open");
    JButton applyButton = new JButton("Save");

    WordDocument template = null;

    public BasePanel() {
        docxButton.addActionListener(ignored ->
                selectFile("Word Documents", "docx")
                .ifPresent(this::updateDocx));
        applyButton.addActionListener(ignored -> {
            if (inputsValid()) {
                applyReplacements();
                saveWordDocument();
            }
        });
    }

    Optional<String> selectFile(String description, String extension) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(description, extension));
        chooser.setAcceptAllFileFilterUsed(false);
        return (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                ? Optional.of(chooser.getSelectedFile().getAbsolutePath())
                : Optional.empty();
    }

    private void updateDocx(String selectedPath) {
        loadWordDocument(selectedPath);
        updateGUIAfterLoadingDocx();
    }

    private void loadWordDocument(String selectedPath) {
        try {
            template = new WordDocument(selectedPath);
        } catch (Exception ex) {
            System.err.println("An Error occured loading " + selectedPath);
            System.err.println(ex);
        }
    }

    abstract void updateGUIAfterLoadingDocx();
    abstract boolean inputsValid();
    abstract void applyReplacements();

    void saveWordDocument() {
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
