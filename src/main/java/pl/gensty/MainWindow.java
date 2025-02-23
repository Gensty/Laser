package pl.gensty;

import pl.gensty.enums.DeviceType;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{
    public static void main(String[] args) {
        System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.NullLogger");
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }

    public MainWindow() {
        setTitle("Wybór typu urządzenia");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        createDeviceButton(DeviceType.REDLER, 0, panel, gbc);
        createDeviceButton(DeviceType.PODNOSNIK, 1, panel, gbc);
        createDeviceButton(DeviceType.ZASUWA, 2, panel, gbc);
        createDeviceButton(DeviceType.ROZDZIELACZ, 3, panel, gbc);

        add(panel);
    }

    private void createDeviceButton(DeviceType deviceType, int gridY, JPanel panel, GridBagConstraints gbc) {
        JButton button = new JButton(deviceType.toString());
        button.setPreferredSize(new Dimension(300, 30));
        button.addActionListener(e -> openCreatorWindow(deviceType));

        gbc.gridx = 0;
        gbc.gridy = gridY;
        panel.add(button, gbc);
    }

    private void openCreatorWindow(DeviceType deviceType) {
        DeviceCreator creator = new DeviceCreator(deviceType);
        creator.setVisible(true);
        this.dispose();
    }
}
