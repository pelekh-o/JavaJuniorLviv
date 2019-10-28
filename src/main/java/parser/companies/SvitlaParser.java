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

public class SvitlaParser implements Parser{
    private static final String URL = "https://svitla.com/career?search=&category%5B%5D=java&country%5B%5D=ukraine";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByAttribute("data-key");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {

            String vacancyName = vacancyBlock.getElementsByTag("h3").text();
            String location = vacancyBlock.getElementsByClass("blog-1__text").text();
            if (VacancyParserUtil.isJunior(vacancyName) &&
                    (location.toLowerCase().contains("lviv") || location.toLowerCase().contains("any"))) {
                String link = "https://svitla.com" + vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Svitla", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
