
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreateAccountPage;
import pages.HomePage;

public class CreateAccountTest extends BaseTest {
    private CreateAccountPage createAccountPage;

    @BeforeMethod
    public void setUpTest() {
        createAccountPage = new CreateAccountPage(driver);
    }

    @Test
    public void testCreateAccount() {
        homePage.clickCreateAccount();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");

        createAccountPage.enterFirstName("Johhn");
        createAccountPage.enterLastName("Doe");
        createAccountPage.enterEmail("johndoe" + System.currentTimeMillis() + "@test.com");
        createAccountPage.enterPassword("Test@1234");
        createAccountPage.confirmPassword("Test@1234");
        createAccountPage.clickCreateAccount();

        // Verify account creation success
        Assert.assertTrue(driver.getPageSource().contains("Thank you for registering"));

        // Step 7: Click on User profile and Sign Out
        homePage.clickUserProfile();
        homePage.clickSignOut();
    }
}

