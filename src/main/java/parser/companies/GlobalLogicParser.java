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

public class GlobalLogicParser implements Parser {
    private static final String URL = "";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("career_pg_list_of_job_order");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("career_pg_job_name").first().text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = vacancyBlock.attr("href");
                vacancy = new Vacancy("GlobalLogic", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }
        return vacanciesList;
    }
}
