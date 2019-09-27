package de.elegal.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Optional;

public class SinglePanel extends JPanel {
    private JButton docxButton = new JButton("Open");
    private JTextField inputDocx = new JTextField("Select a Word File");

    private String selectedPath = null;

    public SinglePanel() {
        inputDocx.setEditable(false);
        docxButton.addActionListener((ignored) -> selectFile().ifPresent(this::updateGUI));
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

    private void updateGUI(String selectedPath) {
        this.selectedPath = selectedPath;
        System.out.println("Update TODO lol");
    }
}
