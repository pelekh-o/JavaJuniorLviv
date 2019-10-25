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

public class SigmaSoftwareParser implements Parser {
    private static final String URL = "https://career.sigma.software/what-we-offer/vacancies/?direction=java&seniority=junior%2Cinterns&location=lviv";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("accordion-item");
        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByTag("h2").first().text();
            String link = vacancyBlock.select("a").first().attr("href");
            vacancy = new Vacancy("SigmaSoftware", vacancyName, link, new Date(), true);
            vacanciesList.add(vacancy);
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
