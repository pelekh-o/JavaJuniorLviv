package bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotUtil {
    private static Logger logger = LogManager.getLogger(TelegramBotUtil.class.getName());

    private static TelegramChannelBot botInstance;

    public static synchronized TelegramChannelBot getBotInstance() {
        if (botInstance == null) {
            botInstance = new TelegramChannelBot();
            // Starting Telegram Bot
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            try {
                botsApi.registerBot(botInstance);
                logger.info("Telegram bot has started.");
            } catch (TelegramApiException e) {
                logger.error("failed to start Telegram bot. {}", e.toString());
            }
        }
        return botInstance;
    }

    private TelegramBotUtil() {
    }
}
