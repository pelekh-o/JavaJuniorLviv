package parser.companies;

import entity.Vacancy;
import logger.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import parser.Parser;
import parser.VacancyParserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataRobotParser implements Parser {
    private static final String URL = "https://www.datarobot.com/careers/?department=3543&locations=lviv-ukraine";

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vacanciesList = new ArrayList<>();

        ChromeDriver ghostDriver = new ChromeDriver();
        Vacancy vacancy = null;
        try {
            ghostDriver.get(URL);
            WebElement jobTable = ghostDriver.findElementByClassName("job-list");
            List<WebElement> vacancies = jobTable.findElements(By.className("item"));
            for (WebElement element: vacancies) {
                String vacancyName = element.getText().split("\n")[0];
                if (vacancyName.toLowerCase().contains("java") && VacancyParserUtil.isJunior(vacancyName)) {
                    String link = element.findElement(By.tagName("a")).getAttribute("href");
                    vacancy = new Vacancy("DataRobot", vacancyName, link, new Date(), true);
                    vacanciesList.add(vacancy);
                }
            }
        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        }
        finally {
            ghostDriver.quit();
        }

        LoggerUtil.logVacanciesMessage(this.getClass(), vacanciesList.size());
        return vacanciesList;
    }
}
