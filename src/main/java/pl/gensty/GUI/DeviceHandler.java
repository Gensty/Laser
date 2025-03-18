package pl.gensty.GUI;

import pl.gensty.GUI.buttons.FieldButtonCreator;
import pl.gensty.GUI.buttons.PackageButtonCreator;
import pl.gensty.GUI.buttons.UndoButtonCreator;
import pl.gensty.enums.DeviceType;
import pl.gensty.enums.MaterialType;

import javax.swing.*;
import java.awt.*;

public class DeviceHandler extends JFrame {
    public DeviceHandler(DeviceType deviceType) {
        setTitle("Kreator paczek");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JTextField excelPathField = new JTextField();
        excelPathField.setPreferredSize(new Dimension(410, 30));

        JTextField catalogPathField = new JTextField();
        catalogPathField.setPreferredSize(new Dimension(410, 30));

        JTextArea outputArea = new JTextArea(20, 60);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        new PackageButtonCreator(deviceType, MaterialType.STEEL, "LASER", 1, 3, panel, gbc, excelPathField, catalogPathField, outputArea);
        new PackageButtonCreator(deviceType, MaterialType.PE1000, "WODA", 2, 3, panel, gbc, excelPathField, catalogPathField, outputArea);
        new PackageButtonCreator(deviceType, MaterialType.PLEXI, "PLEXI", 1, 4, panel, gbc, excelPathField, catalogPathField, outputArea);
        new PackageButtonCreator(deviceType, MaterialType.FILC, "FILC", 2, 4, panel, gbc, excelPathField, catalogPathField, outputArea);

        new FieldButtonCreator("Ścieżka do konfiguratora Excel:", 0, excelPathField, panel, gbc);
        new FieldButtonCreator("Ścieżka docelowa dla paczek:", 1, catalogPathField, panel, gbc);

        new UndoButtonCreator(1, 6, 2, panel, gbc, this);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}