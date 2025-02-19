package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ShoppingCartPage extends BasePage {
    private By itemPriceLocator = By.cssSelector(".cart-price .price");
    private By orderTotalLocator = By.cssSelector(".price-wrapper .price");
    private By cartItemsLocator = By.cssSelector(".cart.item");
    private By deleteItemButtonLocator = By.cssSelector(".action.action-delete");
    private By cartItemCountLocator = By.cssSelector(".action.showcart .counter-number");
    private By emptyCartMessageLocator = By.cssSelector(".cart-empty p");
    private By cartIcon = By.cssSelector("a.action.showcart");
    private By addToCartSuccessMessage = By.cssSelector(".message-success.success.message");
    private By shoppingCartTitle = By.cssSelector(".page-title span");


    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }
    // Metoda për të marrë shumën e çmimeve të produkteve në shportë
    public double calculateTotalPriceFromItems() {
        List<WebElement> prices = driver.findElements(itemPriceLocator);
        double total = 0.0;
        for (WebElement priceElement : prices) {
            String priceText = priceElement.getText().replace("$", "").trim(); // Heq simbolin "$"
            total += Double.parseDouble(priceText);
        }
        return total;
    }

    // Metoda për të marrë Total Order Price nga Summary
    public double getOrderTotalPrice() {
        WebElement totalElement = driver.findElement(orderTotalLocator);
        String totalText = totalElement.getText().replace("$", "").trim(); // Heq simbolin "$"
        return Double.parseDouble(totalText);
    }
    // Kthen numrin e artikujve në shportë
    public int getNumberOfItemsInCart() {
        return driver.findElements(cartItemsLocator).size();
    }

    // Fshin artikullin e parë nga shporta
    public void deleteFirstItemFromCart() {
        List<WebElement> deleteButtons = driver.findElements(deleteItemButtonLocator);
        if (!deleteButtons.isEmpty()) {
            deleteButtons.get(0).click();
            new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.stalenessOf(deleteButtons.get(0)));
        }
    }

    public void selectSizeAndAddToCart(int productIndex, String size) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Gjej listën e produkteve të filtruar
        List<WebElement> products = driver.findElements(By.cssSelector(".product-item"));

        if (products.size() > productIndex) {
            WebElement product = products.get(productIndex);

            // Zgjidh masën për produktin
            WebElement sizeOption = product.findElement(By.xpath(".//div[contains(@class,'swatch-attribute-options')]//div[@option-label='" + size + "']"));
            wait.until(ExpectedConditions.elementToBeClickable(sizeOption)).click();

            // Hover mbi produktin
            Actions actions = new Actions(driver);
            actions.moveToElement(product).perform();

            // Prit derisa butoni Add to Cart të jetë aktiv
            WebElement addToCartButton = product.findElement(By.cssSelector("button.action.tocart"));
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            // Verifiko që butoni nuk është disabled
            if (!addToCartButton.getAttribute("class").contains("disabled")) {
                addToCartButton.click();
            } else {
                throw new ElementClickInterceptedException("Butoni Add to Cart është i çaktivizuar!");
            }
        } else {
            throw new NoSuchElementException("Nuk u gjetën produkte për t'u shtuar në shportë.");
        }
    }
    public WebElement getCartItemCountElement() {
        return driver.findElement(cartItemCountLocator);
    }
    // Kontrollon nëse mesazhi i suksesit për shtimin në shportë është i dukshëm
    public boolean isAddToCartSuccessMessageDisplayed() {
        WebElement successMessage = driver.findElement(addToCartSuccessMessage);
        return successMessage.isDisplayed();
    }

    // Kontrollon nëse faqja e shportës është e dukshme
    public boolean isShoppingCartPageDisplayed() {
        WebElement cartTitle = driver.findElement(shoppingCartTitle);
        return cartTitle.isDisplayed() && cartTitle.getText().contains("Shopping Cart");
    }

    // Kthen lokatorin e artikujve në shportë
    public By getCartItemsLocator() {
        return cartItemsLocator;
    }
    public By getAccountIcon() {
        return cartIcon;
    }

    // Verifikon nëse mesazhi i shportës bosh shfaqet
    public boolean isCartEmptyMessageDisplayed() {
        return !driver.findElements(emptyCartMessageLocator).isEmpty();
    }

}

