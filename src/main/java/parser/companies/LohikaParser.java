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

public class LohikaParser implements Parser {
    private static final String URL = "https://www.lohika.com.ua/careers#location=LV";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("row list-item");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.getElementsByClass("list-item-content-container").first();

            String vacancyName = element.getElementsByClass("list-item-title").first().text();
            String location = element.getElementsByClass("tag-item location").first().text();
            if (location.toLowerCase().contains("lviv")
                    && vacancyName.toLowerCase().contains("java")
                    && VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://www.lohika.com.ua" + element.select("a").first().attr("href");
                vacancy = new Vacancy("Lohika", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
