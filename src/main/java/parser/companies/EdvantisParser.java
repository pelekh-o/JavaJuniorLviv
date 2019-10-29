package parser.companies;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EdvantisParser implements Parser {
    private static final String URL = "https://www.edvantis.com/come-join/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();
        ChromeDriver driver = (ChromeDriver) Parser.driver;
        Vacancy vacancy = null;

        driver.get(URL);

        try {
            Select selectLocation = new Select(driver.findElement(By.id("tdp-0")));
            selectLocation.selectByValue("ukraine");
            Select selectJobCategory = new Select(driver.findElement(By.id("tdp-1")));
            selectJobCategory.selectByValue("software-development");

            driver.findElement(By.id("uwpqsf_id_btn")).click();

            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("article")));
            List<WebElement> vacanciesBlocks = driver.findElements(By.tagName("article"));

            for (WebElement element : vacanciesBlocks) {
                String vacancyName = element.getText().split("\n")[0];
                if (vacancyName.toLowerCase().contains("java")
                        && VacancyParserUtil.isJunior(vacancyName)) {
                    String link = element.findElement(By.tagName("a")).getAttribute("href");
                    vacancy = new Vacancy("Edvantis", vacancyName, link, new Date(), true);
                    vacanciesList.add(vacancy);
                }
            }
        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        }

        return vacanciesList;
    }
}
