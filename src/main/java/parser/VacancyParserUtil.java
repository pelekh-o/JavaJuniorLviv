package parser;

public class VacancyParserUtil {
    public static boolean isJunior(String vacancyName) {
        if (vacancyName.toLowerCase().contains("lead") ||
                vacancyName.toLowerCase().contains("senior") ||
                vacancyName.toLowerCase().contains("middle") ||
                vacancyName.toLowerCase().contains("architect") ||
                vacancyName.toLowerCase().contains("manager"))
            return false;
        return true;
    }
}
