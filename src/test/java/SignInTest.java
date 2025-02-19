
import pages.HomePage;
import pages.SignInPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SignInTest extends BaseTest {
    private SignInPage signInPage;

    @BeforeMethod
    public void setUpTest() {
        signInPage = new SignInPage(driver);
    }

    @Test
    public void testSignIn() {
        homePage.clickSignIn();
        signInPage.enterEmail("test1user11234@example.com");
        signInPage.enterPassword("Test@1234");
        signInPage.clickSignIn();

        // Verify successful login
        //Assert.assertTrue(homePage.isUserLoggedIn("Test1 User1"));

        // Step 5: Click on User profile and Sign Out
        homePage.clickUserProfile();
        homePage.clickSignOut();
    }
}
