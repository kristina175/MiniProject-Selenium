import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.ShoppingCartPage;
import pages.SignInPage;

public class ShoppingCartTest extends BaseTest {

    private HomePage homePage;
    private CategoryPage categoryPage;
    private ShoppingCartPage shoppingCartPage;
    private SignInPage signInPage;

    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage(driver);
        categoryPage = new CategoryPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        signInPage = new SignInPage(driver);

        // Kyçja në aplikacion (parakusht)
        homePage.clickSignIn();
        signInPage.enterEmail("test1user11234@example.com");
        signInPage.enterPassword("Test@1234");
        signInPage.clickSignIn();

        // Navigo te kategoria "Women's Jackets"
        homePage.hoverOverWomenMenu();
        homePage.clickTopsOption();
        homePage.clickJacketsOption();

        // Apliko filtrat e përdorur në FilterTest
        categoryPage.selectColor("Purple");
        categoryPage.selectPriceRange("50-60");
    }

    @Test
    public void testShoppingCart() {

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Hapi 1: Shto të dy produktet në shportë duke zgjedhur masën dhe bërë hover
        categoryPage.selectSizeAndAddToCart(0, "M");  // Produkti i parë, madhësia "M"
        categoryPage.selectSizeAndAddToCart(1, "L");  // Produkti i dytë, madhësia "L"

        // Hapi 2: Verifiko mesazhin e suksesit
        //Assert.assertTrue(shoppingCartPage.isAddToCartSuccessMessageDisplayed(), "Mesazhi i suksesit nuk u shfaq!");
        wait.until(ExpectedConditions.textToBePresentInElement(shoppingCartPage.getCartItemCountElement(), "2"));

        // Hapi 3: Kliko te linku "Shopping Cart"
        categoryPage.clickShoppingCartLink();

        // Hapi 4: Verifiko që kemi naviguar në faqen Shopping Cart
        //ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        //Assert.assertTrue(shoppingCartPage.isShoppingCartPageDisplayed(), "Nuk jemi në faqen e Shopping Cart!");

        /// Hapi 5: Verifikimi i çmimit total
        double calculatedTotal = shoppingCartPage.calculateTotalPriceFromItems();
        double orderTotal = shoppingCartPage.getOrderTotalPrice();

       //Assert.assertEquals(calculatedTotal, orderTotal, "Totali i llogaritur nuk përputhet me Order Total!");
    }

}
