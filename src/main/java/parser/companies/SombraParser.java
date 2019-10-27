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

public class SombraParser implements Parser {
    private static final String URL = "https://sombrainc.com/career/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Element vacanciesTab = document.getElementById("tab-city-1");
        Elements vacanciesBlocks = vacanciesTab.getElementsByClass("vacancy");

        Vacancy vacancy = null;
        for (Element vacancyBlock : vacanciesBlocks) {
            String vacancyName = vacancyBlock.text();
            if (VacancyParserUtil.isJunior(vacancyName) && vacancyName.toLowerCase().contains("java")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Sombra Inc", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
