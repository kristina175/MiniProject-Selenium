import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignInPage;
import pages.CategoryPage;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected SignInPage signInPage;
    protected CategoryPage categoryPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://magento.softwaretestingboard.com/");

        // Inicializimi i faqeve
        homePage = new HomePage(driver);
        signInPage = new SignInPage(driver);
        categoryPage = new CategoryPage(driver);

    }


//    @AfterSuite
//    public void tearDown() {
//        // Mbyll driver-in vetëm pasi të mbarojë e gjithë suita e testeve
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
