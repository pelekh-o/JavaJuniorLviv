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

public class DataArtParser implements Parser {

    private static final String URL = "https://dataart.ua/career/?tags=234,12";

    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Elements content = document.getElementsByClass("job-card__container");

        Vacancy vacancy = null;
        for (Element card : content) {
            Element element = card.getElementsByClass("job-card__title").first();
            String vacancyName = element.text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = "https://dataart.ua" + element.attr("href");
                vacancy = new Vacancy("DataArt", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
