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

public class EleksParser implements Parser {

    private static final String URL = "https://careers.eleks.com/vacancies/?location=55&technology=21";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy-item");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("vacancy-item__title").first().text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.attr("href");
                vacancy = new Vacancy("Eleks", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
