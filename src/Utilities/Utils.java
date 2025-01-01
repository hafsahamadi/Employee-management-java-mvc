package Utilities;

public class Utils {
    public static double parseDouble(String s, double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    public static double parseDouble(String s) {
        return parseDouble(s, 0);
    }
    public static boolean isDouble(String salaire) {
        try {
            Double.parseDouble(salaire);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}