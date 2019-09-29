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

public class VeryGoodSecurity implements Parser {
    private static final String URL = "https://jobs.lever.co/vgs/?team=Engineering&location=Lviv%2C%20Ukraine";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("posting");

        Vacancy vacancy = null;
        for (Element vacancyBlock : vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h5").first().text();
            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Very Good Security", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
