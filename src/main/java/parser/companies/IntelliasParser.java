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

public class IntelliasParser implements Parser {
    private static final String URL = "https://www.intellias.ua/vacancies-java-junior-lviv";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("i col-sm-12 col-md-6");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("h3").first().text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://www.intellias.ua" + vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Intellias", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
