package de.elegal;

import de.elegal.GUI.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Frame::getInstance);
    }
}