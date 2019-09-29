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

public class DevProParser implements Parser {
    private static final String URL = "https://careers.dev-pro.net/en/#open-positions";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("positions-list-item");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("positions-list-item-title").first().text();
            if (vacancyName.toLowerCase().contains("java")
                    && vacancyName.toLowerCase().contains("lviv")
                    && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Dev-Pro", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
