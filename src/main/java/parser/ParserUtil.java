package parser;

import bot.TelegramChannelBot;
import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.companies.*;
import persistence.Factory;

import java.util.*;

public class ParserUtil {
    private static Logger logger = LogManager.getLogger(ParserUtil.class.getName());

    public void checkNewVacancies(TelegramChannelBot telegramBot) {
        List<Vacancy> vacanciesOnSite = null;
        Set<Vacancy> newVacancies = new HashSet<>();

        Set<Parser> parserSet = getParsers();               // Get parsers for all tracked companies.
        for (Parser companyParser : parserSet) {
            vacanciesOnSite = companyParser.getVacancies(); // Parse vacancies from company site.

            if (!vacanciesOnSite.isEmpty()) {               // If vacancies are found
                for (Vacancy vacancy : vacanciesOnSite) {   // check if they are in the database
                    if (!isVacancyInDB(vacancy)) {          // (ie, whether they are new).
                        newVacancies.add(vacancy);
                        Factory.getInstance().getVacancyDAO().addVacancy(vacancy);
                    }
                }
            }
        }

        if (!newVacancies.isEmpty()) {                      // Send new vacancies to the channel
            telegramBot.sendVacanciesToChannel(newVacancies);
            logger.info("Vacancies passed to the bot [{}]", newVacancies);
        } else
            logger.info("there are no new vacancies.");
    }

    private boolean isVacancyInDB(Vacancy vacancy) {
        String companyName = vacancy.getCompanyName();
        List<Vacancy> vacanciesCompanyList = Factory.getInstance().getVacancyDAO().getVacanciesByCompany(companyName);
        for (Vacancy v: vacanciesCompanyList){
            if (v.equals(vacancy))
                return true;
        }
        return false;
    }

    private static Set<Parser> getParsers() {
        Set<Parser> parsers = new HashSet<>();

        parsers.add(new DataArtParser());
        parsers.add(new EpamParser());
        parsers.add(new SoftServeParser());
        parsers.add(new EleksParser());
        parsers.add(new CiklumParser());

        parsers.add(new IntelliasParser());
        parsers.add(new NiXParser());
        parsers.add(new LohikaParser());
        parsers.add(new AMCBridgeParser());
        parsers.add(new InoxoftParser());

        parsers.add(new VectorSoftwareParser());
        parsers.add(new DataRobotParser());
        parsers.add(new InterLogicParser());
        parsers.add(new DevProParser());
        parsers.add(new G5Parser());

        parsers.add(new Levi9Parser());
        parsers.add(new GlobalLogicParser());
        parsers.add(new IntelliartsParser());
        parsers.add(new CoreValueParser());
        parsers.add(new VeryGoodSecurity());

        return parsers;
    }
}
