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

public class InoxoftParser implements Parser {
    private static final String URL = "https://inoxoft.com/career/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("career__list-item");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Inoxoft", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
