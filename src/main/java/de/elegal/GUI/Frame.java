package de.elegal.GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private static Frame instance = null;

    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);
    private JPanel singlePanel = new SinglePanel(), csvPanel = new JPanel();

    private Frame() {
        super("Keres");

        initTabbedPane();

        setFrameOptions();
    }

    private void initTabbedPane() {
        tabbedPane.add("Single", singlePanel);
        tabbedPane.add("CSV", csvPanel);
        this.add(tabbedPane);
    }

    private void setFrameOptions() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500,300));
        pack();
        setVisible(true);
    }

    public static Frame getInstance() {
        if (instance == null) instance = new Frame();
        return instance;
    }
}
