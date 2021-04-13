import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class student_309551023 {
    public static WebDriver driver = new ChromeDriver();

    public static void main(String[] args) {
        // set web-driver path
        System.setProperty("webdriver.chrome.driver", ".");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        /*
            Task a:
                launch browser and navigate to NYCU home page(https://www.nycu.edu.tw/)
                → maximize the window
                → click NEWS
                → click first news
                → print the title and content
        */
        // navigate to NYCU main page
        driver.get("https://www.nycu.edu.tw/");

        // maximize the window
        driver.manage().window().maximize();

        // do click action
        driver.findElement(By.linkText("消息")).click();
        System.out.println("***** Start of Task 1 *****");

        // find latest news list
        List<WebElement> news = findNews();

        // navigate to first new
        news.get(0).findElement(By.tagName("a")).click(); // click on <a> element instead of <li>

        // fetch title
        System.out.printf( "Title: %s \n", fetchTitleFromNews() );

        // fetch content
        System.out.printf ( "Content:\n%s", fetchContentFromNews());

        System.out.println("***** End of Task 1 *****\n");

        /*
            Task b:
                open a new tab and switch to it
                → navigate to google(https://www.google.com)
                → input your student number and submit
                → print the title of second result
                → close the browser
        */
        // open a new tab and switch to it
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.google.com");

        System.out.println("***** Start of Task 2 *****");

        // type key word
        WebElement inputBox = driver.findElement(By.xpath("//input"));
        inputBox.sendKeys("309551023");
        inputBox.sendKeys(Keys.RETURN);

        // find search result
        List<WebElement> searchResults = findSearchResults();
        // fetch the second one
        System.out.printf ( "Title: %s\n", fetchTitleFromSearchResult( searchResults.get(1) ));

        System.out.println("***** End of Task 2 *****");

        // close the window
        driver.quit();
    }

    public static List<WebElement> findNews(){
        WebElement latestNews_section = driver.findElement(By.xpath("//ul[@class='su-posts su-posts-list-loop']"));
        return latestNews_section.findElements(By.xpath("./child::*"));
    }

    public static String fetchTitleFromNews(){
        WebElement title = driver.findElement(By.xpath("//h1[@class='single-post-title entry-title']"));
        return title.getText();
    }

    public static String fetchContentFromNews(){
        WebElement content_section = driver.findElement(By.xpath("//div[@class='entry-content clr']"));

        // parse only <p></p> section. Not included <figcaption></figcaption>
        List<WebElement> content_sectionElements = content_section.findElements(By.xpath("./child::p"));

        // concat paragraph
        StringBuilder contents = new StringBuilder();
        for (int i=0; i<content_sectionElements.size(); i++) {
            contents.append( String.format("=== Paragraph %d ===\n", i+1) );
            contents.append( content_sectionElements.get(i).getText() );
            contents.append("\n");
        }
        contents.append("=== End of Paragraph ===\n");
        return contents.toString();
    }

    public static List<WebElement> findSearchResults(){
        WebElement searchResult_section = driver.findElement(By.xpath("//div[@class='hlcw0c']"));
        return searchResult_section.findElements(By.xpath("./child::div"));
    }

    public static String fetchTitleFromSearchResult(WebElement searchResult){
        WebElement title = searchResult.findElement(By.tagName("h3"));
        return title.getText();
    }
}
