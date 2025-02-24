package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.Module;

import javax.swing.*;

import java.util.Map;

public class PathHandler {
    private final JTextField excelPathField;
    private final JTextField catalogPathField;
    private final JTextArea outputArea;

    public PathHandler(JTextField excelPathField, JTextField catalogPathField, JTextArea outputArea) {
        this.excelPathField = excelPathField;
        this.catalogPathField = catalogPathField;
        this.outputArea = outputArea;
    }

    public String getTargetPath() {
        return validatePath(catalogPathField.getText(), "Podaj ściężkę docelową dla paczek.\n");
    }

    public String getExcelPath() {
        return validatePath(excelPathField.getText(), "Podaj ściężkę do konfiguratora Excel.\n");
    }

    public String getSourcePath(AbstractConfig abstractConfig, Module module, Map<String, String> paths) {
        String sourcePath = (abstractConfig instanceof ConfigOther)
        ? abstractConfig.getSize()
        : abstractConfig.getSize() + "_" + module.name();

        String path = paths.get(sourcePath);

        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Nie znaleziono ścieżki dla: " + sourcePath);
        }

        return path;
    }

    private String validatePath(String path, String outputMessage) {
        if (path == null || path.isBlank()) {
            outputArea.append(outputMessage);
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return path;
    }
}
