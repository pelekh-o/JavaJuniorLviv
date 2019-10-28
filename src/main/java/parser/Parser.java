package parser;

import entity.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public interface Parser {
    List<Vacancy> getVacancies();
    WebDriver driver = new ChromeDriver();

    static Document getHTMLDocument(String URL) {
        Document document = null;
        try {
            driver.get(URL);
            document = Jsoup.parse(driver.getPageSource());
        } catch (Exception e) {
            LogManager.getLogger(Parser.class.getName()).error(e.toString());
        }
        return document;
    }
}
