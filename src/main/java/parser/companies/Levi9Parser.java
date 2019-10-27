package parser.companies;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Levi9Parser implements Parser {
    private static final String URL = "https://ukraine.levi9.jobs/open-positions/?location_id=32&role_id=20";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();

        ChromeDriver driver = new ChromeDriver();
        Vacancy vacancy = null;
        try {
            driver.get(URL);
            Select city = new Select(driver.findElement(By.className("filter--country")));
            city.selectByVisibleText("Ukraine / Lviv");

            driver.manage().window().fullscreen();
            driver.findElement(By.className("filter--search")).sendKeys("java");
            Thread.sleep(3000);

            WebElement jobTable = driver.findElementByClassName("listing__content");
            List<WebElement> vacancies = jobTable.findElements(By.className("listing__item"));

            for (WebElement element: vacancies) {
                String vacancyName = element.getText().split("\n")[0];
                if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                    String link = element.getAttribute("href");
                    vacancy = new Vacancy("Levi9", vacancyName, link, new Date(), true);
                    vacanciesList.add(vacancy);
                }
            }
        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        }
        finally {
            driver.quit();
        }

        return vacanciesList;
    }
}
