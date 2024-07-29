package pl.gensty;

import pl.gensty.Enums.DeviceType;
import pl.gensty.Enums.MaterialType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static pl.gensty.Utils.FileUtils.*;


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

        JButton setLaserButton = new JButton(deviceType + " - LASER");
        setLaserButton.setPreferredSize(new Dimension(200,30));

        JButton setWaterButton = new JButton(deviceType + " - WODA");
        setWaterButton.setPreferredSize(new Dimension(200,30));

        JButton setPlexiButton = new JButton(deviceType + " - PLEXI");
        setPlexiButton.setPreferredSize(new Dimension(200,30));

        JButton setFilcButton = new JButton(deviceType + " - FILC");
        setFilcButton.setPreferredSize(new Dimension(200,30));

        JButton undoButton = new JButton("Cofnij do wyboru urządzeń");
        undoButton.setPreferredSize(new Dimension(410,30));

        DeviceType deviceTypeShort = getDeviceTypeOther(deviceType);

        setLaserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareLaser(deviceTypeShort, MaterialType.SHEET, outputArea,excelPathField,catalogPathField);
            }
        });

        setWaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareLaser(deviceTypeShort, MaterialType.PE1000, outputArea,excelPathField,catalogPathField);
            }
        });

        setPlexiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareLaser(deviceTypeShort, MaterialType.PLEXI, outputArea,excelPathField,catalogPathField);
            }
        });

        setFilcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareLaser(deviceTypeShort, MaterialType.FILC, outputArea,excelPathField,catalogPathField);
            }
        });

        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindow mainWindow = new MainWindow();
                mainWindow.setVisible(true);
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Ścieżka konfiguratora Excel:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(excelPathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Ścieżka katalogu dla paczek:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(catalogPathField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(setLaserButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(setWaterButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(setPlexiButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(setFilcButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(undoButton, gbc);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private static DeviceType getDeviceTypeOther(DeviceType deviceType) {
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