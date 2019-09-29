package parser.companies;

import entity.Vacancy;
import logger.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.VacancyParserUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntelliartsParser implements Parser {
    private static final String URL = "https://intelliarts.breezy.hr/";

    @Override
    public List<Vacancy> getVacancies() {
        Document document = null;
        List<Vacancy> vacanciesList = new ArrayList<>();

        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            LogManager.getLogger(IntelliartsParser.class.getName()).error(e.getMessage());
        }

        Elements vacanciesBlocks = document.getElementsByClass("position transition");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h2").first().text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://intelliarts.breezy.hr" + vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Intelliarts", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
