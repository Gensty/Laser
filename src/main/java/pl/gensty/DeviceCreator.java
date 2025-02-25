package pl.gensty;

import pl.gensty.enums.DeviceType;
import pl.gensty.enums.MaterialType;
import pl.gensty.manager.FileHandler;
import pl.gensty.manager.FolderHandler;
import pl.gensty.manager.PackageHandler;
import pl.gensty.manager.PathHandler;

import javax.swing.*;
import java.awt.*;

public class DeviceCreator extends JFrame {
    private final JTextField catalogPathField;
    private final JTextField excelPathField;
    private final JTextArea outputArea;

    public DeviceCreator(DeviceType deviceType) {

        setTitle("Kreator paczek");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        excelPathField = new JTextField();
        excelPathField.setPreferredSize(new Dimension(410, 30));
        catalogPathField = new JTextField();
        catalogPathField.setPreferredSize(new Dimension(410, 30));
        outputArea = new JTextArea(20, 60);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        DeviceType deviceTypeShort = getDeviceType(deviceType);

        createPackageTypeButton(deviceType, deviceTypeShort, MaterialType.STEEL, "LASER", 1, 3, panel, gbc);
        createPackageTypeButton(deviceType, deviceTypeShort, MaterialType.PE1000, "WODA", 2, 3, panel, gbc);
        createPackageTypeButton(deviceType, deviceTypeShort, MaterialType.PLEXI,"PLEXI", 1, 4, panel, gbc);
        createPackageTypeButton(deviceType, deviceTypeShort, MaterialType.FILC,"FILC", 2, 4, panel, gbc);

        createFieldButton("Ścieżka do konfiguratora Excel:", 0, excelPathField, panel, gbc);
        createFieldButton("Ścieżka docelowa dla paczek:", 1, catalogPathField, panel, gbc);

        createUndoButton(1, 6, 2, panel, gbc);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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

    private void createPackageTypeButton(
            DeviceType deviceType,
            DeviceType deviceTypeShort,
            MaterialType materialType,
            String packageType,
            int gridX,
            int gridY,
            JPanel panel,
            GridBagConstraints gbc
    ) {
        PathHandler pathHandler = new PathHandler(excelPathField, catalogPathField, outputArea);
        FileHandler fileHandler = new FileHandler(pathHandler, outputArea);
        FolderHandler folderHandler = new FolderHandler(pathHandler, outputArea);
        PackageHandler packageHandler = new PackageHandler(folderHandler, fileHandler, pathHandler);

        JButton button = new JButton(deviceType + " - " + packageType);
        button.setPreferredSize(new Dimension(200,30));
        button.addActionListener(e -> packageHandler.preparePackage(deviceTypeShort, materialType));

        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;

        panel.add(button, gbc);
    }

    private void createFieldButton(
            String fieldText,
            int gridY,
            JTextField textField,
            JPanel panel,
            GridBagConstraints gbc
    ) {
        gbc.gridx = 0;
        gbc.gridy = gridY;
        gbc.gridwidth = 1;
        panel.add(new JLabel(fieldText), gbc);

        gbc.gridx = 1;
        gbc.gridy = gridY;
        gbc.gridwidth = 2;
        panel.add(textField, gbc);
    }

    private void createUndoButton(int gridX, int gridY, int gridWidth, JPanel panel, GridBagConstraints gbc) {
        JButton undoButton = new JButton("Cofnij do wyboru urządzeń");
        undoButton.setPreferredSize(new Dimension(410,30));
        undoButton.addActionListener(e -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
            dispose();
        });
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;
        panel.add(undoButton, gbc);
    }
}