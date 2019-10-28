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

public class CoreValueParser implements Parser {
    private static final String URL = "https://corevalue.com.ua/careers/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancies__item");

        Vacancy vacancy = null;
        for (Element vacancyBlock : vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h4").first().text();
            String location = vacancyBlock.getElementsByClass("vacancies__taxonomy").first().text();
            if (vacancyName.toLowerCase().contains("java")
                    && VacancyParserUtil.isJunior(vacancyName)
                    && location.toLowerCase().contains("lviv")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("CoreValue", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}