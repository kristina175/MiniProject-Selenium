import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ShoppingCartPage;
import pages.SignInPage;

public class EmptyShoppingCartTest extends BaseTest {

    private HomePage homePage;
    private ShoppingCartPage shoppingCartPage;
    private SignInPage signInPage;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        signInPage = new SignInPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, 15); // Pritje më e gjatë

        // Kyçja në aplikacion (parakusht)
        homePage.clickSignIn();
        signInPage.enterEmail("test1user11234@example.com");
        signInPage.enterPassword("Test@1234");
        signInPage.clickSignIn();

        // **Pritje derisa të shfaqet emri i përdoruesit në Home Page**
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logged-in")));

        // **Vetëm pasi të shfaqet emri i përdoruesit, klikojmë ikonën e shportës**
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".action.showcart"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.action.viewcart"))).click();
    }

    @Test
    public void testEmptyShoppingCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Merr numrin fillestar të produkteve në shportë
        int initialItemCount = shoppingCartPage.getNumberOfItemsInCart();

        // Përsërit fshirjen deri sa shporta të jetë bosh
        while (shoppingCartPage.getNumberOfItemsInCart() > 0) {
            // Prit derisa butoni i fshirjes të jetë i klikueshëm
            shoppingCartPage.deleteFirstItemFromCart();

            // Prit derisa numri i produkteve në shportë të zvogëlohet
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                    shoppingCartPage.getCartItemsLocator(), initialItemCount
            ));
            initialItemCount--;
        }

        // Verifiko që shporta është bosh
        Assert.assertTrue(shoppingCartPage.isCartEmptyMessageDisplayed(),
                "Mesazhi 'You have no items in your shopping cart.' nuk u shfaq!");
    }
}
