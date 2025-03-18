package pl.gensty.GUI.buttons;

import pl.gensty.MainWindow;

import javax.swing.*;
import java.awt.*;

public class UndoButtonCreator {
    public UndoButtonCreator(int gridX, int gridY, int gridWidth, JPanel panel, GridBagConstraints gbc, JFrame frame) {
        JButton undoButton = new JButton("Cofnij do wyboru urządzeń");
        undoButton.setPreferredSize(new Dimension(410, 30));
        undoButton.addActionListener(e -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
            frame.dispose();
        });

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;
        panel.add(undoButton, gbc);
    }
}