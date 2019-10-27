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

public class AMCBridgeParser implements Parser {
    private static final String URL = "https://amcbridge.com.ua/ua/join-us/vacancies?location%5B%5D=Lviv&category%5B%5D=java&keyword=";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy-item");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.getElementsByClass("title a-invert").first();

            String vacancyName = element.text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://amcbridge.com.ua" + element.select("a").first().attr("href");
                vacancy = new Vacancy("AMCBridge", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
