import parser.InitParser;
import parser.Parser;

public class Main {
    public static void main(String[] args) {
        new InitParser();
        System.exit(0);
        Parser.driver.quit();
    }
}
