package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    public static void logVacanciesMessage(Class clazz, int count) {
        Logger logger = LogManager.getLogger(clazz);
        logger.info(count + " vacancies found in " + clazz.getName());
    }
}
