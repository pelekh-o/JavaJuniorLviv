import parser.InitParser;
import parser.Parser;

public class Main {
    public static void main(String[] args) {
        new InitParser();
        Parser.driver.quit();
        System.exit(0);
    }
}
