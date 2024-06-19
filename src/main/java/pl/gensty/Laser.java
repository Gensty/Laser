package pl.gensty;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Laser extends JFrame {
    public static void main(String[] args) {
        System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.NullLogger");
        SwingUtilities.invokeLater(() -> new Laser().setVisible(true));
    }

    private final JTextField catalogPathField;
    private final JTextField excelPathField;
    private final JTextArea outputArea;

    public Laser() {
        setTitle("Laser");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1));

        catalogPathField = new JTextField(100);
        excelPathField = new JTextField(100);
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton setFilenamesButton = new JButton("Ustaw nazwy plików DWG");
        setFilenamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFilenames();
            }
        });

        panel.add(new JLabel("Podaj ścieżkę do katalogu:"));
        panel.add(catalogPathField);
        panel.add(new JLabel("Podaj ścieżkę do pliku Excel:"));
        panel.add(excelPathField);
        panel.add(setFilenamesButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void laser() {
        return;
    }

    private void setFilenames() {
        String catalogPath = catalogPathField.getText();
        String excelPath = excelPathField.getText();

        File directory = new File(catalogPath);
        if (!directory.isDirectory()) {
            outputArea.append("Podana ścieżka nie jest katalogiem.\n");
            return;
        }

        List<String> newNames = readExcelFile(excelPath);
        if (newNames == null) {
            outputArea.append("Nie udało się wczytać pliku Excel.\n");
            return;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".dwg");
            }
        });
        if (files == null) {
            outputArea.append("Katalog jest pusty lub wystąpił błąd podczas odczytu.\n");
            return;
        }

        for (int i = 0; i < files.length && i < newNames.size(); i++) {
            File file = files[i];
            if (file.isFile() && (file.getName().endsWith(".dwg") || file.getName().endsWith(".DWG"))) {
                String newFileName = newNames.get(i) + ".DWG";
                File newFile = new File(catalogPath + File.separator + newFileName);
                if (file.renameTo(newFile)) {
                    outputArea.append("Stara nazwa pliku: " + file.getName() + "\n" + "Nowa nazwa pliku: " + newFileName + "\n" + "\n");
                } else {
                    outputArea.append("Nie udało się zmienić nazwy pliku: " + file.getName() + "\n");
                }
            }
        }
    }

    private static List<String> readExcelFile(String excelFilePath) {
        List<String> newNames = new ArrayList<>();

        File excelFile = new File(excelFilePath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelFilePath);
            return null;
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Sheet sheet = workbook.getSheet("Laser - ZPR");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Cell cell = row.getCell(7);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    break;
                }

                CellValue cellValue = evaluator.evaluate(cell);

                String laserFilename = cellValue.getStringValue();
                newNames.add(laserFilename);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return newNames;
    }
}