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

public class BinariksParser implements Parser {
    private static final String URL = "https://binariks.com/career/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Element vacanciesTable = document.getElementById("menu-career");
        Elements vacancies = vacanciesTable.getElementsByTag("li");
        Vacancy vacancy = null;
        for (Element vacancyBlock: vacancies) {
            String vacancyName = vacancyBlock.text();
            if (VacancyParserUtil.isJunior(vacancyName) &&
                vacancyName.toLowerCase().contains("java")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Binariks", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
