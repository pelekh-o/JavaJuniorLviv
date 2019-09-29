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

public class EpamParser implements Parser {
    private static final String URL = "https://www.epam.com/careers/job-listings?query=java&country=Ukraine&city=Lviv";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("search-result__item-info");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.getElementsByClass("search-result__item-name").first();
            String vacancyName = element.text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://www.epam.com" + element.attr("href");
                vacancy = new Vacancy("Epam", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
