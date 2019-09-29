package parser.companies;

import entity.Vacancy;
import logger.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SoftServeParser implements Parser {
    private static Logger logger = LogManager.getLogger(SoftServeParser.class.getName());
    private static final String URL = "https://career.softserveinc.com/en-us/vacancies/country-4/city-15/direction-5/technology-36/position-1/";
    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Element vacanciesBlock = document.getElementById("vacancy-content");
        Elements vacancyBlocks = vacanciesBlock.getElementsByClass("card-unit");

        Vacancy vacancy = null;

        for (Element element: vacancyBlocks) {
            try {
                String vacancyName = element.getElementsByClass("title").first().text();
                String link = element.select("a").first().attr("href");
                vacancy = new Vacancy("SoftServe", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            } catch (NullPointerException e) {
                logger.error(e.toString());
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
