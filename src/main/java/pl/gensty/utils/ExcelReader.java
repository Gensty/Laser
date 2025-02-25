package pl.gensty.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.gensty.configuration.AbstractConfig;
import pl.gensty.devicePart.AbstractPart;
import pl.gensty.devicePart.strategy.FactoryPart;
import pl.gensty.enums.Module;
import pl.gensty.enums.Parameter;
import pl.gensty.exceptions.ConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {
    public static String getDeviceConfig(String excelPath, String header) {
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            throw new IllegalArgumentException("Plik nie istnieje: " + excelPath);
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Sheet sheet = workbook.getSheet("Konfigurator");

            String data = null;
            String headerExcel = null;
            int i=1;
            while (!header.equals(headerExcel)) {
                headerExcel = getCellStringValue(sheet.getRow(i).getCell(1), evaluator);
                Cell cell = sheet.getRow(i).getCell(2);

                if (cell.getCellType() == CellType.BLANK) {
                    throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
                } else {
                    try {
                        data = getCellStringValue(sheet.getRow(i).getCell(2), evaluator);
                    } catch (IllegalStateException e) {
                        data = String.valueOf(getCellIntValue(sheet.getRow(i).getCell(2), evaluator));
                    }
                }

                i++;
            }

            return data;
        } catch (IOException e) {
            throw  new ConfigException(header);
        }
    }

    public static List<AbstractPart> getPartsFromConfig(String excelPath, AbstractConfig abstractConfig, Module module) {
        List<AbstractPart> parts = new ArrayList<>();
//        File excelFile = new File(excelPath);
//        if (!excelFile.exists()) {
//            throw new IllegalArgumentException("Plik nie istnieje: " + excelPath);
//        }

        if (abstractConfig == null) {
            System.out.println("Nie podano konfiguracji do odczytu części");
            return parts;
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet(module.toString());

            for (Row row : sheet) {
                if (row.getRowNum() < 10) {
                    continue;
                }

                if (isRowEmpty(row)) {
                    break;
                }

                AbstractPart part = createPart(row, evaluator);
                parts.add(part);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Plik nie istnieje: " + excelPath);
//            e.printStackTrace();
//            return parts;
        }

        return parts;
    }

    public static Integer readModuleQuantity(String excelPath, Module module) {
        Integer moduleQuantity = 1;

        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            throw new IllegalArgumentException("Plik nie istnieje: " + excelPath);
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet(module.name());

            String header = "Ilość modułów";
            String headerExcel = null;
            int i=0;
            while (!header.equals(headerExcel)) {
                headerExcel = getCellStringValue(sheet.getRow(i).getCell(0), evaluator);
                Cell cell = sheet.getRow(i).getCell(0);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
                }

                try {
                    moduleQuantity = getCellIntValue(sheet.getRow(i).getCell(1), evaluator);
                } catch (IllegalStateException e) {
                    System.out.println("Sprawdź ilość modułów w excelu. Moduł: " + module);
                }

                i++;
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Plik nie istnieje: " + excelPath);
//            e.printStackTrace();
//            return moduleQuantity;
        }

        return moduleQuantity;
    }

    public static Map<String, String> getPaths(String excelPath) {
        Map<String, String> paths = new HashMap<>();
        File excelFile = new File(excelPath);

        if (!excelFile.exists()) {
            System.out.println("Plik Excel nie istnieje: " + excelPath);
        }

        try (FileInputStream fis = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fis)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet("DataPath");

            readPaths(sheet, paths, evaluator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    private static void readPaths(Sheet sheet, Map<String, String> paths, FormulaEvaluator evaluator) {
        for (Row row : sheet) {
            if (isRowEmpty(row)) {
                if (paths.isEmpty()) {
                    continue;
                }
                break;
            }

            String size = getCellStringValue(row.getCell(0), evaluator);
            String path = getCellStringValue(row.getCell(1), evaluator);

            paths.put(size, path);
        }
    }

    private static AbstractPart createPart(Row row, FormulaEvaluator evaluator) {
        Map<Parameter, Object> params = Map.of(
                Parameter.NUMBER_EDT, getCellStringValue(row.getCell(1), evaluator),
                Parameter.MATERIAL, getCellStringValue(row.getCell(2), evaluator),
                Parameter.THICKNESS, getCellIntValue(row.getCell(3), evaluator),
                Parameter.QUANTITY, getCellIntValue(row.getCell(4), evaluator),
                Parameter.DESCRIPTION, getCellStringValue(row.getCell(5), evaluator)
        );

        return FactoryPart.createPart(params);
    }

    private static boolean isRowEmpty(Row row) {
        Cell cell = row.getCell(0);
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    private static String getCellStringValue(Cell cell, FormulaEvaluator evaluator) {
        if (cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
        }

        if (cell.getCellType() == CellType.FORMULA) {
            return evaluator.evaluate(cell).getStringValue();
        } else {
            return cell.getStringCellValue();
        }
    }

    private static Integer getCellIntValue(Cell cell, FormulaEvaluator evaluator) {
        if (cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
        }

        if (cell.getCellType() == CellType.FORMULA) {
            return (int) evaluator.evaluate(cell).getNumberValue();
        } else if (cell.getCellType() == CellType.STRING){
            return (int) evaluator.evaluate(cell).getNumberValue();
        } else {
            return (int) cell.getNumericCellValue();
        }
    }

    public static Boolean convertStringToBoolean(String value) {
        return value.equals("TAK");
    }

    public static String isSingleCharNumber(Integer number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return number.toString();
        }
    }
}
