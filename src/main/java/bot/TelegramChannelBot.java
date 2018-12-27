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
import persistence.Factory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramChannelBot extends TelegramLongPollingBot {
    private static Logger logger = LogManager.getLogger(TelegramChannelBot.class.getName());

    //TODO винести channelID, OlehPelekh у файл
    private static final String channelId = "@JavaJuniorLviv";
    private String admChatId = null;

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if(message.getFrom().getUserName() != null
                    && message.getFrom().getUserName().equals("OlehPelekh")) {
                doAdminsActions(message);
                admChatId = String.valueOf(message.getChatId());
            } else {
                doUsersAction(message.getChatId());
                logger.info("New bot user: {}", message.getFrom());
            }
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

    private void sendMsg(String chatId, String answer) {
        SendMessage sendMessage = new SendMessage()
                .disableWebPagePreview()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setParseMode("HTML")
                .setText(answer);
        try {
            execute(sendMessage);
            logger.info("Message:{}", answer);
        } catch (TelegramApiException e) {
            sendMsg(admChatId, EmojiParser.parseToUnicode(":x:") + e);
            logger.error("Error when sending a message[{}], chatId[{}]. Exception: {}", answer, chatId, e);
        }
    }

    private void doAdminsActions(Message message) {
        String command = message.getText();
        String admChatId = String.valueOf(message.getChatId());

        switch (command) {
            case "d":
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.scheduleAtFixedRate(() -> startChannelUpdates(admChatId), 0, 12, TimeUnit.HOURS);
                sendMsg(admChatId, EmojiParser.parseToUnicode(":white_check_mark: Daily updates are working now"));
                logger.info("Daily updates are working now");
                break;
            default:
                sendMsg(admChatId, EmojiParser.parseToUnicode(":no_entry: Wrong parameters"));
        }
    }

    private void doUsersAction(long chatId) {
        String message = "Нові вакансії для початківців Java у Львові <a href=\"https://t.me/JavaJuniorLviv\">@JavaJuniorLviv</a>";
        sendMsg(String.valueOf(chatId), message);
    }

    /**
     * The function periodically reads new vacancies from the database
     * and sends them to the Telegram channel
     * @param admChatId
     */
    private void startChannelUpdates(String admChatId) {
        List<Vacancy> vacancies = Factory.getInstance().getVacancyDAO().getNewVacancies();
        StringBuilder sbVacancies = new StringBuilder();

        if (!vacancies.isEmpty()) {     // if there are new vacancies
            sbVacancies.append("<b>Нові вакансії:</b>\n");
            for (Vacancy vacancy : vacancies) {
                sbVacancies.append(EmojiParser.parseToUnicode("\n:black_small_square: ") + vacancy.getVacancyTitle()
                        + " at <b>" + vacancy.getCompanyName() + "</b>\n "
                        + vacancy.getLink() + "\n");

                vacancy.setIsNew(false);
                Factory.getInstance().getVacancyDAO().updateVacancy(vacancy);
            }
            sendMsg(channelId, String.valueOf(sbVacancies));    // send vacancies to channel
        } else { // send message to bot chat with admin
            sendMsg(admChatId, EmojiParser.parseToUnicode(":thumbsdown: За останню добу нових вакансій не знайдено"));
        }
    }

}
