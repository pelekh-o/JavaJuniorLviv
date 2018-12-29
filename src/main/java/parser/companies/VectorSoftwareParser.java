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

public class VectorSoftwareParser implements Parser {
    private static final String URL = "https://vector-software.com/careers.aspx";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements vacanciesBlocks = document.getElementsByClass("vacancy-block");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            String vacancyName = vacancyBlock.getElementsByClass("blueText").first().text();

            if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                vacancy = new Vacancy("Vector Software", vacancyName, URL, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
