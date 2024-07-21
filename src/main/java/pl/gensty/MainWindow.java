package pl.gensty;

import pl.gensty.Enums.DeviceType;

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

        JButton conveyorButton = new JButton(DeviceType.REDLER.toString());
        JButton elevatorButton = new JButton(DeviceType.PODNOSNIK.toString());
        JButton damperButton = new JButton(DeviceType.ZASUWA.toString());
        JButton dividerButton = new JButton(DeviceType.ROZDZIELACZ.toString());

        conveyorButton.setPreferredSize(new Dimension(300, 30));
        elevatorButton.setPreferredSize(new Dimension(300, 30));
        damperButton.setPreferredSize(new Dimension(300, 30));
        dividerButton.setPreferredSize(new Dimension(300, 30));

        conveyorButton.addActionListener(e -> openCreatorWindow(DeviceType.REDLER));
        elevatorButton.addActionListener(e -> openCreatorWindow(DeviceType.PODNOSNIK));
        damperButton.addActionListener(e -> openCreatorWindow(DeviceType.ZASUWA));
        dividerButton.addActionListener(e -> openCreatorWindow(DeviceType.ROZDZIELACZ));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(conveyorButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(elevatorButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(damperButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(dividerButton, gbc);

        add(panel);
    }

    private void openCreatorWindow(DeviceType deviceType) {
        DeviceCreator creator = new DeviceCreator(deviceType);
        creator.setVisible(true);
        this.dispose();
    }
}
