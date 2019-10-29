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

public class SemicolonParser implements Parser {
    private static final String URL = "https://semicolonlab.com/career/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy");

        Vacancy vacancy = null;
        for (Element vacancyBlock : vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h4").first().text();
            if (!vacancyName.toLowerCase().contains("javascript") && vacancyName.toLowerCase().contains("java")
                    && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Semicolon Lab", vacancyName, URL, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
