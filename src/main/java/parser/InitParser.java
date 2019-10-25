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

import static bot.TelegramBotInstance.getBotInstance;

public class InitParser {
    private static Logger logger = LogManager.getLogger(InitParser.class.getName());

    public InitParser() {
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
        try {
            for (Parser companyParser : parserSet) {
                vacanciesOnSite = companyParser.getVacancies(); // Parse vacancies from company site.
                if (!vacanciesOnSite.isEmpty())                 // If vacancies are found
                    for (Vacancy vacancy : vacanciesOnSite)     // check if they are in the database
                        if (!isVacancyInDB(vacancy)) {          // (ie, whether they are new).
                            newVacancies.add(vacancy);
                            Factory.getInstance().getVacancyDAO().addVacancy(vacancy);
                        }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        finally {
            Parser.driver.quit();
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
                new AMCBridgeParser(),
                new BinariksParser(),
                new CiklumParser(),
                new CoreValueParser(),
                new DataArtParser(),
                new DataRobotParser(),
                new DevComParser(),
                new EleksParser(),
                new EpamParser(),
                new G5Parser(),
                new GlobalLogicParser(),
                new InoxoftParser(),
                new IntelliartsParser(),
                new IntelliasParser(),
                new InterLogicParser(),
                new Levi9Parser(),
                new LohikaParser(),
                new NiXParser(),
                new SigmaSoftwareParser(),
                new SoftServeParser(),
                new UkeessParser(),
                new VectorSoftwareParser(),
                new VeryGoodSecurity(),
                new VolpisParser()
        ).collect(Collectors.toSet());
    }
}
