package bot;

import com.vdurmont.emoji.EmojiParser;
import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramChannelBot extends TelegramLongPollingBot {
    private static Logger logger = LogManager.getLogger(TelegramChannelBot.class.getName());
    private static final String CHANNEL_ID = "@JavaJuniorLviv";

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String answer = "Нові вакансії для початківців Java у Львові тут " +
                    "<a href=\"https://t.me/JavaJuniorLviv\">@JavaJuniorLviv</a>";
            sendMsg(String.valueOf(message.getChatId()), answer);
            logger.info("New bot user: {}\nMessage: {}", message.getFrom(), message.getText());
        }
    }

    public void sendVacanciesToChannel(ArrayList<Vacancy> vacancySet) {
        StringBuilder sb = new StringBuilder();
        for (Vacancy vacancy: vacancySet) {
            sb.append(EmojiParser.parseToUnicode("\n:white_small_square: "))
                    .append(vacancy.getVacancyTitle())
                    .append(" at <b>")
                    .append(vacancy.getCompanyName())
                    .append("</b>\n ")
                    .append(vacancy.getLink())
                    .append("\n");
        }

        sendMsg(CHANNEL_ID, sb.toString());
    }

    private void sendMsg(String chatId, String answer) {
        SendMessage sendMessage = new SendMessage()
                .disableWebPagePreview()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setParseMode("HTML")
                .setText(answer);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Failed to send message [{}], chatId[{}]. Exception: {}", answer, chatId, e);
        }
    }

    @Override
    public String getBotUsername() {
        return "JobFinderBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_TOKEN");
    }
}
