package parser.companies;

import entity.Vacancy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfopulseParser implements Parser {
    private static final String URL = "https://www.infopulse.com/vacancies/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        //Document document = Parser.getHTMLDocument(URL);
        Document document = null;
        WebDriver ghostDriver = new PhantomJSDriver();
        try {
            ghostDriver.get(URL);

            Select dropdown = new Select(ghostDriver.findElement(By.id("departments_filter_select")));
            dropdown.selectByValue("942");
            //ghostDriver.findElement(By.xpath("")).sendKeys("948");
            document = Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }

        Elements vacanciesBlocks = document.getElementsByClass("vacancy-item__link clearfix");

        Vacancy vacancy = null;
        for (Element vacancyBlock: vacanciesBlocks) {
            Element element = vacancyBlock.select("a").first();
            String vacancyName = element.text();
            if (VacancyParserUtil.isJunior(vacancyName)) {
                String link = element.select("a").first().attr("href");
                vacancy = new Vacancy("Infopulse", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        }

        return vacanciesList;
    }
}
