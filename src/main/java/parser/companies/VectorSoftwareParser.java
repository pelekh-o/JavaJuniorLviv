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

public class VectorSoftwareParser implements Parser {
    private static final String URL = "https://vector-software.com/careers";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancies-wrapper");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("vacancies-title-wrapper").first().text();

            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Vector Software", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
