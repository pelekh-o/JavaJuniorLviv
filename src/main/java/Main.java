import bot.TelegramBotUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.ParserUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(ParserUtil.INSTANCE, 0, 12, TimeUnit.HOURS);
    }
}
