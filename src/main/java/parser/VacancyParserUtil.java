package parser;

public class VacancyParserUtil {
    public static boolean isJunior(String vacancyName) {
        return (vacancyName.toLowerCase().contains("trainee") ||
                vacancyName.toLowerCase().contains("intern") ||
                vacancyName.toLowerCase().contains("junior") ||
                vacancyName.toLowerCase().contains("entry"));
    }
}
