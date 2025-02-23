package pl.gensty.manager;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.enums.Module;

import javax.swing.*;

import java.util.Map;

import static pl.gensty.utils.ExcelReader.readPaths;

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
        String targetPath = catalogPathField.getText();
        if (targetPath == null || targetPath.isEmpty() || targetPath.isBlank()) {
            outputArea.append("Musisz podać ścieżkę, w której chcesz stworzyć paczki.\n");
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return targetPath;
    }

    public String getExcelPath() {
        String excelPath = excelPathField.getText();
        if (excelPath == null || excelPath.isEmpty() || excelPath.isBlank()) {
            outputArea.append("Musisz podać ścieżkę do konfiguratora Excel.\n");
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return excelPath;
    }

    public String getSourcePath(String excelPath, AbstractConfig abstractConfig, Module module) {
        String sourcePath;
        if (abstractConfig instanceof ConfigOther) {
            sourcePath = abstractConfig.getSize();
        } else {
            sourcePath = abstractConfig.getSize() + "-" + module.toString();
        }

        Map<String, String> paths = readPaths(excelPath);
        return paths.get(sourcePath);
    }
}
