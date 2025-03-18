package pl.gensty.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class Utils {
    public static String isSingleCharNumber(Integer number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return number.toString();
        }
    }

    public static boolean isRowEmpty(Row row) {
        Cell cell = row.getCell(0);
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    public static Boolean convertStringToBoolean(String value) {
        return value.equals("TAK");
    }
}
