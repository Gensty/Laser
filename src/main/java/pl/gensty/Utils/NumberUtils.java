package pl.gensty.Utils;

public class NumberUtils {
    public static String isSingleCharNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return "" + number;
        }
    }
}
