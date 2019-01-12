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
        // Starting Telegram Bot
        ApiContextInitializer.init();
        TelegramChannelBot telegramBot = new TelegramChannelBot();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(telegramBot);
            logger.info("Telegram bot has started.");
        } catch (TelegramApiException e) {
            logger.error("failed to start Telegram bot. {}", e.getMessage());
        }

        // Getting new vacancies
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() ->
                new ParserUtil().checkNewVacancies(telegramBot), 0, 6, TimeUnit.HOURS);

    }

}
