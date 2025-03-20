package pl.gensty.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.gensty.enums.Module;
import pl.gensty.enums.Parameter;
import pl.gensty.exceptions.ConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static pl.gensty.utils.Utils.*;

public class ExcelReader {
    private final String excelPath;
    private Workbook workbook;
    private FormulaEvaluator evaluator;

    public ExcelReader(String excelPath) throws IOException {
        this.excelPath = excelPath;
        loadWorkbook();
    }

    private void loadWorkbook() throws IOException {
        this.workbook = new XSSFWorkbook(new FileInputStream(excelPath));
        this.evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    }

    public void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    public String getDeviceConfig(String header) {
        Sheet sheet = workbook.getSheet("Konfigurator");

        for (Row row : sheet) {
            if (header.equals(getCellStringValue(row.getCell(1) ))) {
                Cell cell = row.getCell(2);

                try {
                    return getCellStringValue(cell);
                } catch (IllegalStateException e) {
                    return String.valueOf(getCellIntValue(cell));
                }
            }
        }
        throw new ConfigException(header);
    }

    public Map<Parameter, Object> getPartParams(Row row) {
        return Map.of(
                Parameter.NUMBER_EDT, getCellStringValue(row.getCell(1)),
                Parameter.MATERIAL, getCellStringValue(row.getCell(2)),
                Parameter.THICKNESS, getCellIntValue(row.getCell(3)),
                Parameter.QUANTITY, getCellIntValue(row.getCell(4)),
                Parameter.DESCRIPTION, getCellStringValue(row.getCell(5))
        );
    }

    public int readModuleQuantity(Module module) {
        Sheet sheet = workbook.getSheet(module.name());
        for (Row row : sheet) {
            if ("Ilość modułów".equals(getCellStringValue(row.getCell(0)))) {
                return getCellIntValue(row.getCell(1));
            }
        }
        return 1;
    }

    public Map<String, String> readPaths() {
        Map<String, String> paths = new HashMap<>();
        Sheet sheet = workbook.getSheet("DataPath");

        for (Row row : sheet) {
            if (isRowEmpty(row)) break;
            paths.put(getCellStringValue(row.getCell(0)), getCellStringValue(row.getCell(1)));
        }
        return paths;
    }

    public String getCellStringValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return "";
        if (cell.getCellType() == CellType.FORMULA) return evaluator.evaluate(cell).getStringValue();
        return cell.getStringCellValue();
    }

    public int getCellIntValue(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return 0;
        if (cell.getCellType() == CellType.FORMULA) return (int) evaluator.evaluate(cell).getNumberValue();
        return (int) cell.getNumericCellValue();
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
