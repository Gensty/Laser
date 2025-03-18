package pl.gensty.GUI.buttons;

import pl.gensty.GUI.DeviceHandler;
import pl.gensty.enums.DeviceType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DeviceButtonCreator {
    private final DeviceType deviceType;

    public DeviceButtonCreator(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public JButton createButton(JFrame parentFrame) {
        JButton button = new JButton(deviceType.toString());
        button.setPreferredSize(new Dimension(300, 30));
        button.addActionListener(e -> {
            try {
                openCreatorWindow(deviceType);
                parentFrame.dispose();
            } catch (IOException ex) {
                throw new RuntimeException("Błąd podczas otwierania kreatora urządzeń", ex);
            }
        });
        return button;
    }

    private void openCreatorWindow(DeviceType deviceType) throws IOException {
        DeviceHandler creator = new DeviceHandler(deviceType);
        creator.setVisible(true);
    }
}
