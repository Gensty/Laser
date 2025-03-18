package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.Module;
import pl.gensty.utils.ExcelReader;

import javax.swing.*;

import java.util.Map;

public class PathHandler {
    private final ExcelReader excelReader;
    private final JTextField excelPathField;
    private final JTextField catalogPathField;
    private final JTextArea outputArea;

    public PathHandler(ExcelReader excelReader, JTextField excelPathField, JTextField catalogPathField, JTextArea outputArea) {
        this.excelReader = excelReader;
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

    public String getSourcePath(AbstractConfig abstractConfig, Module module) {
        Map<String, String> paths = excelReader.readPaths();

        String config = (abstractConfig instanceof ConfigOther)
        ? abstractConfig.getSize()
        : abstractConfig.getSize() + "_" + module.name();

        String sourcePath = paths.get(config);

        if (sourcePath == null || sourcePath.isBlank()) {
            throw new IllegalArgumentException("Nie znaleziono ścieżki dla: " + config);
        }

        return sourcePath;
    }

    private String validatePath(String path, String outputMessage) {
        if (path == null || path.isBlank()) {
            outputArea.append(outputMessage);
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return path;
    }
}
