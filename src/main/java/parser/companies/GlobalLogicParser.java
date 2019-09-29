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

public class GlobalLogicParser implements Parser {
    private static final String URL = "https://www.globallogic.com/ua/careers/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("career_pg_list_of_job_order");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("career_pg_job_name").first().text();
            String location = vacancyBlock.text();
            if (VacancyParserUtil.isJunior(vacancyName) &&
                    (location.toLowerCase().contains("lviv") || location.toLowerCase().contains("multiple cities")) &&
                    vacancyName.toLowerCase().contains("java")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("GlobalLogic", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
