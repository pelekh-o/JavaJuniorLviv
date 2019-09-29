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

public class CiklumParser implements Parser {
    private static final String URL = "https://jobs.ciklum.com/jobs/?key=java&offices=9";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("card__item--jobs");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.getElementsByClass("job-title").first();

            String vacancyName = element.text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = element.select("a").first().attr("href");
                vacancy = new Vacancy("Ciklum", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
