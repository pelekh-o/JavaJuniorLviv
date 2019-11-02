package parser.companies;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.*;

public class TeamInternationalParser implements Parser {
    private static final String URL = "https://www.teaminternational.com/careers/";

    @Override
    public List<Vacancy> getVacancies() {
        Set<Vacancy> vacancySet = new HashSet<>();
        ChromeDriver driver = (ChromeDriver) Parser.driver;
        Vacancy vacancy = null;

        driver.get(URL);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("select")));

            Select selectLocation = new Select(driver.findElement(By.className("careers_tabs_filter-inline"))
                    .findElement(By.tagName("select")));
            selectLocation.selectByVisibleText("Lviv");

            Document htmlDoc = Jsoup.parse(driver.getPageSource());
            Elements vacancyBlocks = htmlDoc.getElementsByClass("slick-slide slick-cloned");

            for (Element vacancyDiv: vacancyBlocks) {
                String vacancyName = vacancyDiv.getElementsByTag("a").first().text();
                if (vacancyName.toLowerCase().contains("java")
                        && VacancyParserUtil.isJunior(vacancyName)) {
                    String link = vacancyDiv.select("a").first().attr("href");
                    vacancy = new Vacancy("TEAM International", vacancyName, link, new Date(), true);
                    vacancySet.add(vacancy);
                }
            }

        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        }

        return new ArrayList<>(vacancySet);
    }
}
