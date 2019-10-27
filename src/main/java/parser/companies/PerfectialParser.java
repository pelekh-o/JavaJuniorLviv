package parser.companies;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.Parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerfectialParser implements Parser {
    private static final String URL = "https://perfectial.com/careers/";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();

        ChromeDriver driver = (ChromeDriver) Parser.driver;
        Vacancy vacancy = null;
        try {
            driver.get(URL);
            new WebDriverWait(driver, 15).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"careersFilters\"]/div[1]/div[1]"))
            );
            // Select location from dropdown
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[1]/div[1]"))
                    .click();
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[1]/div[2]/div[2]/div/div[4]")) // Lviv
                    .click();
            // Select position from dropdown
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[2]/div[1]"))
                    .click();
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[2]/div[2]/div[2]/div/div[13]"))    // Java
                    .click();
            // Select levels from dropdown
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[3]/div[1]"))
                    .click();
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[3]/div[2]/div[2]/div/div[2]")) // Junior
                    .click();
            driver.findElement(By.xpath("//*[@id=\"careersFilters\"]/div[3]/div[2]/div[2]/div/div[7]")) // Trainee
                    .click();
            // Click on blank area
            driver.findElement(By.xpath("//body"))
                    .click();

            List<WebElement> vacancies = driver.findElements(By.cssSelector("#careersResults > li"));
            for (WebElement element : vacancies) {
                String vacancyName = element.getText().split("\n")[0];
                String link = element.findElement(By.tagName("a")).getAttribute("href");
                vacancy = new Vacancy("Perfectial", vacancyName, link, new Date(), true);
                vacanciesList.add(vacancy);
            }
        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        } finally {
            driver.quit();
        }

        return vacanciesList;
    }
}
