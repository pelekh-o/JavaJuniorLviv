package parser.companies;

import entity.Vacancy;
import logger.LoggerUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterLogicParser implements Parser {
    private static final String URL = "https://www.interlogic.com.ua/ua/vacancies/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://www.interlogic.com.ua/ua/vacancies"
                        + vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("InterLogic", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
