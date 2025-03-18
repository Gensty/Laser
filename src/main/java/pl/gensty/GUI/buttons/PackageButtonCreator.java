package pl.gensty.GUI.buttons;

import pl.gensty.enums.DeviceType;
import pl.gensty.enums.MaterialType;
import pl.gensty.manager.FileHandler;
import pl.gensty.manager.FolderHandler;
import pl.gensty.manager.PackageHandler;
import pl.gensty.manager.PathHandler;
import pl.gensty.utils.ExcelReader;

import javax.swing.*;
import java.awt.*;

public class PackageButtonCreator {
    public PackageButtonCreator(
            DeviceType deviceType,
            MaterialType materialType,
            String packageType,
            int gridX,
            int gridY,
            JPanel panel,
            GridBagConstraints gbc,
            JTextField excelPathField,
            JTextField catalogPathField,
            JTextArea outputArea
    ) {
        JButton button = new JButton(deviceType + " - " + packageType);
        button.setPreferredSize(new Dimension(200, 30));

        DeviceType deviceTypeShort = getDeviceType(deviceType);

        button.addActionListener(e -> {
            try {
                ExcelReader excelReader = new ExcelReader(excelPathField.getText());
                PathHandler pathHandler = new PathHandler(excelReader, excelPathField, catalogPathField, outputArea);
                FileHandler fileHandler = new FileHandler(excelReader, pathHandler, outputArea);
                FolderHandler folderHandler = new FolderHandler(pathHandler, outputArea);
                PackageHandler packageHandler = new PackageHandler(folderHandler, fileHandler, pathHandler);

                packageHandler.createPackage(deviceTypeShort, materialType);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Błąd: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        panel.add(button, gbc);
    }

    private static DeviceType getDeviceType(DeviceType deviceType) {
        switch (deviceType) {
            case REDLER -> {
                return DeviceType.SPR;
            }
            case PODNOSNIK -> {
                return DeviceType.NPK;
            }
            default -> {
                return DeviceType.OTHER;
            }
        }
    }
}
