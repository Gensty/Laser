package pl.gensty;

import pl.gensty.GUI.buttons.DeviceButtonCreator;
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

        createAllDeviceButtons(getAvailableDevices(), gbc, panel);
        add(panel);
    }

    private void createAllDeviceButtons(DeviceType[] devices, GridBagConstraints gbc, JPanel panel) {
        for (int i = 0; i < devices.length; i++) {
            gbc.gridy = i;
            JButton button = new DeviceButtonCreator(devices[i]).createButton(this);
            panel.add(button, gbc);
        }
    }

    private DeviceType[] getAvailableDevices() {
        return new DeviceType[]{DeviceType.REDLER, DeviceType.PODNOSNIK, DeviceType.ZASUWA, DeviceType.ROZDZIELACZ};
    }
}
