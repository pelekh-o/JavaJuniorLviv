package parser;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.companies.*;
import persistence.Factory;

import java.util.*;

public class ParserUtil {
    private static Logger logger = LogManager.getLogger(ParserUtil.class.getName());

    private static Set<Parser> parsers = new HashSet<>();

    static {
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

        //parsers.add(new InfopulseParser());
        //parsers.add(new GlobalLogicParser());
    }

    public static void saveToDB() {
        int count = 0;
        List<Vacancy> vacancies = null;
        for (Parser parser: parsers) {
            count++;
            vacancies = parser.getVacancies();
            if (!vacancies.isEmpty()) {
                for (Vacancy vacancy : vacancies) {
                    if (!isVacancyInDB(vacancy, vacancy.getCompanyName())) {
                        Factory.getInstance().getVacancyDAO().addVacancy(vacancy);
                        logger.info("New vacancy[{}]", vacancy);
                    }
                }
            }
        }
        logger.info("Companies checked count: {}. Companies in list: {}", count, parsers.size());
    }

    private static boolean isVacancyInDB(Vacancy vacancy, String company) {
        List<Vacancy> vacanciesCompanyList = Factory.getInstance().getVacancyDAO().getVacanciesByCompany(company);
        for (Vacancy v: vacanciesCompanyList){
            if (v.equals(vacancy))
                return true;
        }
        return false;
    }
}
