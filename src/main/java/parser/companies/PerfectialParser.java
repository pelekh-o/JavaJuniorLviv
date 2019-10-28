package parser.companies;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerfectialParser implements Parser {
    private static final String URL = "https://perfectial.com/careers/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        Document document = Parser.getHTMLDocument(URL);

        Element vacanciesTable = document.getElementById("careersResults");
        Elements vacancies = vacanciesTable.getElementsByTag("li");
        Vacancy vacancy = null;
        for (Element vacancyBlock: vacancies) {
            String vacancyName = vacancyBlock.getElementsByClass("pf-careers--group-name").first().text();
            String location = vacancyBlock.getElementsByClass("pf-careers-details-info").text();
            if (VacancyParserUtil.isJunior(vacancyName) &&
                    vacancyName.toLowerCase().contains("java") &&
                    location.toLowerCase().contains("lviv")) {
                String link = vacancyBlock.select("a").first().attr("href");
                vacancy = new Vacancy("Perfectial", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
