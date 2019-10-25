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

public class DevComParser implements Parser {
    private static final String URL = "https://devcom.com/career/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacancies = document.getElementsByClass("vacancy");

        Vacancy vacancy = null;
        for (Element vacancyBlock : vacancies) {
            String vacancyName = vacancyBlock.getElementsByClass("job-title").first().text();
            String location = vacancyBlock.getElementsByClass("location").first().text();
            if (vacancyName.toLowerCase().contains("java") &&
                    VacancyParserUtil.isJunior(vacancyName) && 
                    location.toLowerCase().contains("lviv")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("DevCom", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
