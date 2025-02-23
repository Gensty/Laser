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
    public static String readDeviceConfig(String excelPath, String header) {
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
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

    public static List<AbstractPart> readPartsFromConfig(String excelPath, AbstractConfig abstractConfig, Module module) {
        List<AbstractPart> parts = new ArrayList<>();

        if (abstractConfig == null) {
            System.out.println("Nie podano konfiguracji do odczytu części");
            return parts;
        }

        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
            return parts;
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet(module.toString());

            for (Row row : sheet) {
                if (row.getRowNum() < 10) {
                    continue;
                }
                Cell cell = row.getCell(0);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    break;
                }

                //TODO: weryfikacja rzędów, kolumn
                String numberEDT = getCellStringValue(row.getCell(1), evaluator);
                String material = getCellStringValue(row.getCell(2), evaluator);
                Integer thickness = getCellIntValue(row.getCell(3), evaluator);
                Integer quantity = getCellIntValue(row.getCell(4), evaluator);
                String description = getCellStringValue(row.getCell(5), evaluator);

                Map<Parameter, Object> params = new HashMap<>();
                params.put(Parameter.NUMBER_EDT, numberEDT);
                params.put(Parameter.MATERIAL, material);
                params.put(Parameter.THICKNESS, thickness);
                params.put(Parameter.QUANTITY, quantity);
                params.put(Parameter.DESCRIPTION, description);

                AbstractPart abstractPart = FactoryPart.createPart(params);
                parts.add(abstractPart);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return parts;
        }

        return parts;
    }

    public static Integer readModuleQuantity(String excelPath, AbstractConfig abstractConfig, Module module) {
        Integer moduleQuantity = 1;
        if (abstractConfig == null) {
            return moduleQuantity;
        }

        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
            return moduleQuantity;
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet(module.toString());

            String header = "Ilość modułów";
            String headerExcel = null;
            int i=0;
            while (!header.equals(headerExcel)) {
                headerExcel = getCellStringValue(sheet.getRow(i).getCell(0), evaluator);
                Cell cell = sheet.getRow(i).getCell(0);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
                } else {
                    try {
                        moduleQuantity = getCellIntValue(sheet.getRow(i).getCell(1), evaluator);
                    } catch (IllegalStateException e) {
                        System.out.println("Sprawdź ilość modułów w excelu. Moduł: " + module);
                    }
                }

                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return moduleQuantity;
        }

        return moduleQuantity;
    }

    public static Map<String, String> readPaths(String excelPath) {
        HashMap<String, String> paths = new HashMap<>();
        File excelFile = new File(excelPath);

        if (!excelFile.exists()) {
            System.out.println("Plik Excel nie istnieje: " + excelPath);
        }

        try (FileInputStream fis = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fis)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet("DataPath");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                Cell cell = row.getCell(0);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    break;
                }

                String size = getCellStringValue(row.getCell(0), evaluator);
                String path = getCellStringValue(row.getCell(1), evaluator);

                paths.put(size, path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
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
