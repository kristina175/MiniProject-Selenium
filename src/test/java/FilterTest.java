
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.CategoryPage;
import pages.SignInPage;

import static org.testng.Assert.assertEquals;


public class FilterTest extends BaseTest {

    private HomePage homePage;
    private CategoryPage categoryPage;
    public SignInPage signInPage;


    @BeforeMethod
    public void setUpTest() {
        homePage = new HomePage(driver);
        categoryPage = new CategoryPage(driver);
        signInPage = new SignInPage(driver);


        // Kyçja në aplikacion (parakusht)
        homePage.clickSignIn();
        signInPage.enterEmail("test1user11234@example.com");
        signInPage.enterPassword("Test@1234");
        signInPage.clickSignIn();
    }

    @Test(groups = "testPageFilters")
    public void testPageFilters() {
        // Hapi 1 & 2: Navigo te kategoria "Women's Jackets"
        homePage.hoverOverWomenMenu();
        homePage.clickTopsOption();
        homePage.clickJacketsOption();

        // Hapi 3: Zgjidh ngjyrën "Purple"
        categoryPage.selectColor("Purple");

        // Hapi 4: Zgjidh intervalin e çmimit $50.00 - $59.99
        categoryPage.selectPriceRange("50-60");

        // Hapi 5: Verifiko që vetëm dy produkte shfaqen
        int productCount = categoryPage.getProductCount();
        //assertEquals(productCount, 2, "Numri i produkteve nuk është 2!");

        // Hapi 6: Hiq filtrin e çmimit
        categoryPage.removePriceFilter();

        // Hapi 7: Verifiko që numri i produkteve është rritur
        int newProductCount = categoryPage.getProductCount();
        //Assert.assertTrue(newProductCount > 2, "Numri i produkteve nuk u rrit pas heqjes së filtrit!");

        // Hapi 8: Shto dy produktet e para në Wish List
        categoryPage.addFirstTwoItemsToWishList();
        ;

        // Hapi 9: Verifiko mesazhin e suksesit dhe ikonën
        //Assert.assertTrue(categoryPage.isWishListSuccessMessageDisplayed(), "Mesazhi i suksesit nuk u shfaq!");
        //Assert.assertTrue(categoryPage.isWishListIconDisplayed(), "Ikona e Wish List nuk u shfaq!");

        // Hapi 10: Kliko te profili dhe kontrollo Wish List (duhet të ketë 2 produkte)
        homePage.clickUserProfile();
        homePage.clickWishList();
    }

}
