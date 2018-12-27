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

public class NiXParser implements Parser {
    private static final String URL = "https://www.n-ix.com/jobs/?keys=java&the_city=Lviv&hot=&latest=&183134=399863&183050=684377";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy");
;
        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.getElementsByClass("title").first();
            String vacancyName = element.text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://www.n-ix.com" + element.select("a").first().attr("href");
                vacancy = new Vacancy("N-iX", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
