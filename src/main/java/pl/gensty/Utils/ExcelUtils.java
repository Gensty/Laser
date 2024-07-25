package pl.gensty.Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.gensty.Configuration.AbstractConfig;
import pl.gensty.Configuration.ConfigNPK;
import pl.gensty.Configuration.ConfigSPR;
import pl.gensty.DevicePart.AbstractPart;
import pl.gensty.Enums.DeviceType;
import pl.gensty.Enums.ModuleNPK;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtils {
    public static Optional<AbstractConfig> readLaserConfigFromExcel(String excelPath, DeviceType deviceType) {
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
            return Optional.empty();
        }

        try {
            FileInputStream fis = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fis);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet("Konfigurator");

            String type = getCellStringValue(sheet.getRow(1).getCell(2), evaluator);
            String order = getCellStringValue(sheet.getRow(2).getCell(2), evaluator);
            String size = getCellStringValue(sheet.getRow(3).getCell(2), evaluator);
            String material = getCellStringValue(sheet.getRow(4).getCell(2), evaluator);
            Integer deviceQuantity = getCellIntValue(sheet.getRow(5).getCell(2), evaluator);

            Map<String, Object> params = new HashMap<>();
            params.put("order", order);
            params.put("type", type);
            params.put("size", size);
            params.put("material", material);
            params.put("deviceQuantity", deviceQuantity);

            if (deviceType == DeviceType.NPK) {
                String feetType = getCellStringValue(sheet.getRow(7).getCell(2), evaluator);
                String filling = getCellStringValue(sheet.getRow(8).getCell(2), evaluator);
                Boolean isVentingSegment = getCellBooleanValue(sheet.getRow(9).getCell(2), evaluator);
                Boolean isMaintenancePlatform = getCellBooleanValue(sheet.getRow(10).getCell(2), evaluator);

                params.put("feetType", feetType);
                params.put("filling", filling);
                params.put("isVentingSegment", isVentingSegment);
                params.put("isMaintenancePlatform", isMaintenancePlatform);
            } else if (deviceType == DeviceType.SPR) {
                String chainSupport = getCellStringValue(sheet.getRow(7).getCell(2), evaluator);

                params.put("chainSupport", chainSupport);
            } else if (deviceType == DeviceType.OTHER) {
                String driveType = getCellStringValue(sheet.getRow(6).getCell(2), evaluator).toUpperCase();
                params.put("driveType", driveType);
            }

            AbstractConfig abstractConfig = FactoryConfig.createConfig(deviceType, params);

            workbook.close();
            fis.close();
            return Optional.of(abstractConfig);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<List<AbstractPart>> readLaserFilesFromExcel(String excelPath, DeviceType deviceType,
                                                                       AbstractConfig abstractConfig, String module) {
        List<AbstractPart> parts = new ArrayList<>();

        if (abstractConfig == null) {
            return Optional.of(parts);
        }

        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
            return Optional.of(parts);
        }

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelPath))) {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet(module);

            for (Row row : sheet) {
                if (row.getRowNum() < 9) {
                    continue;
                }
                Cell cell = row.getCell(0);

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    break;
                }

                String numberEDT = getCellStringValue(row.getCell(1), evaluator);
                String material = getCellStringValue(row.getCell(2), evaluator);
                Integer thickness = getCellIntValue(row.getCell(3), evaluator);
                Integer quantity = getCellIntValue(row.getCell(4), evaluator);
                String description = getCellStringValue(row.getCell(5), evaluator);

                Map<String, Object> params = new HashMap<>();
                params.put("numberEDT", numberEDT);
                params.put("material", material);
                params.put("thickness", thickness);
                params.put("quantity", quantity);
                params.put("description", description);

                if (abstractConfig instanceof ConfigSPR) {
                    Boolean rolls = getCellBooleanValue(row.getCell(6), evaluator);
                    Boolean upperDeck = getCellBooleanValue(row.getCell(7), evaluator);
                    Boolean transportingUpperDeck = getCellBooleanValue(row.getCell(8), evaluator);
                    Boolean guideBar = getCellBooleanValue(row.getCell(9), evaluator);

                    params.put("rolls", rolls);
                    params.put("upperDeck", upperDeck);
                    params.put("transportingUpperDeck", transportingUpperDeck);
                    params.put("guideBar", guideBar);
                } else if (abstractConfig instanceof ConfigNPK) {
                    if (ModuleNPK.STS.toString().equals(module) || ModuleNPK.STZ.toString().equals(module)) {
                        Boolean filling1Way = getCellBooleanValue(row.getCell(6), evaluator);
                        Boolean filling2Way = getCellBooleanValue(row.getCell(7), evaluator);
                        Boolean fillingNoWay = getCellBooleanValue(row.getCell(8), evaluator);

                        params.put("filling1Way", filling1Way);
                        params.put("filling2Way", filling2Way);
                        params.put("fillingNoWay", fillingNoWay);
                    }
                } else  {
                    Boolean isElectric = getCellBooleanValue(row.getCell(6), evaluator);
                    Boolean isPneumatic = getCellBooleanValue(row.getCell(7), evaluator);
                    Boolean isManual = getCellBooleanValue(row.getCell(8), evaluator);

                    params.put("isElectric", isElectric);
                    params.put("isPneumatic", isPneumatic);
                    params.put("isManual", isManual);
                }

                AbstractPart abstractPart = FactoryPart.createInstance(deviceType, module, params);

                parts.add(abstractPart);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(parts);
    }

    public static Optional<Map<String, String>> readPathFromExcel(String excelPath) {
        HashMap<String, String> paths = new HashMap<>();
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            System.out.println("Plik nie istnieje: " + excelPath);
            return Optional.empty();
        }

        try {
            FileInputStream fis = new FileInputStream(excelPath);
            Workbook workbook = new XSSFWorkbook(fis);
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            Sheet sheet = workbook.getSheet("Path");

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

            workbook.close();
            fis.close();
            return Optional.of(paths);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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
        } else {
            return (int) cell.getNumericCellValue();
        }
    }

    private static Boolean getCellBooleanValue(Cell cell, FormulaEvaluator evaluator) {
        String value;
        boolean includeFile;

        if (cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException("Sprawdź czy któraś z komórek w konfiguratorze Excel nie jest pusta.");
        }

        if (cell.getCellType() == CellType.FORMULA) {
            value = evaluator.evaluate(cell).getStringValue();
        } else {
            value = cell.getStringCellValue();
        }

        includeFile = value.equals("X");
        return includeFile;
    }
}
