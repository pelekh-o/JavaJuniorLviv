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

public class VolpisParser implements Parser {
    private static final String URL = "http://volpis.com/career";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("position ng-star-inserted");
        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h3").first().text();
            if (VacancyParserUtil.isJunior(vacancyName) &&
                    vacancyName.toLowerCase().contains("java")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Volpis", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
