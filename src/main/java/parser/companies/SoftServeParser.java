package parser.companies;

import entity.Vacancy;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SoftServeParser implements Parser {
    private static final String URL = "https://career.softserveinc.com/en-us/vacancies?country%5B%5D=4&city%5B%5D=15&direction%5B%5D=5&technology%5B%5D=36&q=";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Element vacanciesBlock = document.getElementById("vacancy-content");
        Elements vacancyBlocks = vacanciesBlock.getElementsByClass("card-unit");

        Vacancy vacancy = null;

        for (Element element: vacancyBlocks) {
            String vacancyName = element.getElementsByClass("title").first().text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = element.select("a").first().attr("href");
                vacancy = new Vacancy("SoftServe", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
