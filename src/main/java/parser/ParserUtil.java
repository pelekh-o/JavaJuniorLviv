package parser;

import bot.TelegramChannelBot;
import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.companies.*;
import persistence.Factory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bot.TelegramBotUtil.getBotInstance;

public class ParserUtil implements Runnable {
    private static Logger logger = LogManager.getLogger(ParserUtil.class.getName());
    public static final ParserUtil INSTANCE = new ParserUtil();

    private ParserUtil() {
    }

    @Override
    public void run() {
        ArrayList<Vacancy> newVacancies = getNewVacancies();
        if (newVacancies.size() != 0) {
            TelegramChannelBot bot = getBotInstance();
            bot.sendVacanciesToChannel(newVacancies);
            logger.info("New vacancies found: {}.", newVacancies);
        } else
            logger.info("No new vacancies found.");
    }

    private ArrayList<Vacancy> getNewVacancies() {
        List<Vacancy> vacanciesOnSite = null;
        ArrayList<Vacancy> newVacancies = new ArrayList<>();

        Set<Parser> parserSet = getParsers();               // Get parsers for all tracked companies.
        for (Parser companyParser : parserSet) {
            vacanciesOnSite = companyParser.getVacancies(); // Parse vacancies from company site.
            if (!vacanciesOnSite.isEmpty())                 // If vacancies are found
                for (Vacancy vacancy : vacanciesOnSite)     // check if they are in the database
                    if (!isVacancyInDB(vacancy)) {          // (ie, whether they are new).
                        newVacancies.add(vacancy);
                        Factory.getInstance().getVacancyDAO().addVacancy(vacancy);
                    }
        }
        return newVacancies;
    }

    private boolean isVacancyInDB(Vacancy vacancy) {
        String companyName = vacancy.getCompanyName();
        List<Vacancy> vacanciesCompanyList = Factory.getInstance().getVacancyDAO().getVacanciesByCompany(companyName);
        return vacanciesCompanyList.stream().anyMatch(v -> v.equals(vacancy));
    }

    private Set<Parser> getParsers() {
        return Stream.of(
                new DataArtParser(),
                new EpamParser(),
                new SoftServeParser(),
                new EleksParser(),
                new CiklumParser(),

                new IntelliasParser(),
                new NiXParser(),
                new LohikaParser(),
                new AMCBridgeParser(),
                new InoxoftParser(),

                new VectorSoftwareParser(),
                new DataRobotParser(),
                new InterLogicParser(),
                new DevProParser(),
                new G5Parser(),

                new Levi9Parser(),
                new GlobalLogicParser(),
                new IntelliartsParser(),
                new CoreValueParser(),
                new VeryGoodSecurity()
        ).collect(Collectors.toSet());
    }
}
