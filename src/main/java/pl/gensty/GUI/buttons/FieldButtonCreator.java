package pl.gensty.GUI.buttons;

import javax.swing.*;
import java.awt.*;

public class FieldButtonCreator {
    public FieldButtonCreator(String fieldText, int gridY, JTextField textField, JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        panel.add(new JLabel(fieldText), gbc);

        gbc.gridx = 1;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        panel.add(textField, gbc);
    }
}
