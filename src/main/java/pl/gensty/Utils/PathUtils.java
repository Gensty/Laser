package pl.gensty.Utils;

import pl.gensty.Configuration.AbstractConfig;

import javax.swing.*;

import java.util.Optional;

import static pl.gensty.Utils.ExcelUtils.readPathFromExcel;

public class PathUtils {
    public static String getTargetPath(JTextArea outputArea, JTextField catalogPathField) {
        String targetPath = catalogPathField.getText();
        if (targetPath == null || targetPath.isEmpty() || targetPath.isBlank()) {
            outputArea.append("Musisz podać ścieżkę, w której chcesz stworzyć paczki.\n");
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return targetPath;
    }

    public static String getExcelPath(JTextArea outputArea, JTextField excelPathField) {
        String excelPath = excelPathField.getText();
        if (excelPath == null || excelPath.isEmpty() || excelPath.isBlank()) {
            outputArea.append("Musisz podać ścieżkę do konfiguratora Excel.\n");
            throw new IllegalArgumentException("Ścieżka do katalogu jest pusta.");
        }
        return excelPath;
    }

    public static String getSourcePath(String excelPath, AbstractConfig abstractConfig) {
        return readPathFromExcel(excelPath)
                .flatMap(paths -> Optional.ofNullable(paths.get(abstractConfig.getSize())))
                .orElseThrow(() -> new IllegalArgumentException("Nie udało się znaleźć ścieżki dla podanego typu i rozmiaru w pliku Excel."));
    }
}
