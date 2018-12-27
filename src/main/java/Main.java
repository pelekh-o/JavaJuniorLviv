import bot.TelegramChannelBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.ParserUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Getting new vacancies
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> ParserUtil.saveToDB(), 0, 5, TimeUnit.HOURS);

        // Starting Telegram Bot
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new TelegramChannelBot());
            logger.info("Telegram bot has started.");
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }

    }


}
