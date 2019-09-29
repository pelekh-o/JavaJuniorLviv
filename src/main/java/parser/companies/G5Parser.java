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

public class G5Parser implements Parser {
    private static final String URL = "https://jobs.g5e.com/ukraine/programming";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("cat_programming");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("position_name").first().text();
            String location = vacancyBlock.getElementsByClass("geotag").text();
            if (vacancyName.toLowerCase().contains("java")
                    && VacancyParserUtil.isJunior(vacancyName)
                    && location.toLowerCase().contains("lvov")) {
                String link = "https://jobs.g5e.com" + vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("G5", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
