package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CategoryPage extends BasePage {
    private By colorFilter = By.xpath("//div[contains(@class, 'filter-options') and .//div[contains(text(), 'Color')]]");
    private By priceFilter = By.xpath("//div[contains(@class, 'filter-options-title') and text()='Price']");
    private By selectedProducts = By.cssSelector(".product-item");
    private By wishListItems = By.cssSelector(".product-item"); // Produktet në Wish List
    private By sizeDropdown = By.xpath("//div[@class='swatch-option text' and @option-label='XS' and @role='option']"); // Dropdown për madhësi
    private By addToCartButton = By.cssSelector("button[title='Add to Cart']"); // Butoni "Add to Cart"
    private By productPrice = By.cssSelector(".price"); // Çmimi i produktit
    private By cartTotal = By.cssSelector(".cart-total .price"); // Totali në shportë
    private By shoppingCartLink = By.cssSelector("a.action.showcart");
    private By wishListSuccessMessage = By.cssSelector(".message-success.success.message");
    private By wishListIcon = By.cssSelector(".action.towishlist");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    // **1. Zgjedhja e një ngjyre specifike në filter**
    public void selectColor(String color) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Hap dropdown-in e filtrit të ngjyrave nëse nuk është hapur
        WebElement colorFilterElement = wait.until(ExpectedConditions.elementToBeClickable(colorFilter));
        if (!colorFilterElement.getAttribute("class").contains("active")) {
            colorFilterElement.click();
        }

        // Gjej opsionin e ngjyrës dinamike sipas inputit
        By colorOption = By.xpath(String.format("//a[@aria-label='%s']/div[@option-label='%s']", color, color));
        WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(colorOption));
        colorElement.click();
    }
    public boolean verifySelectedColor(String selectedColor) {
        List<WebElement> productColorElements = driver.findElements(By.cssSelector(".product-item .color-box")); // Modifiko selektorin sipas HTML të faqes
        for (WebElement colorElement : productColorElements) {
            String colorAttribute = colorElement.getAttribute("title"); // Mund të jetë "aria-label" ose "title" sipas HTML
            if (!colorAttribute.equalsIgnoreCase(selectedColor)) {
                return false; // Nëse gjejmë një produkt që nuk përputhet, kthejmë false
            }
        }
        return true; // Nëse të gjitha produktet përputhen, kthejmë true
    }

    public void selectPriceRange(String priceRange) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // ✅ Sigurohemi që filtri i çmimit është i hapur përpara se të kërkojmë opsionet
        WebElement priceFilterElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-role='title' and contains(text(), 'Price')]")));
        if (!priceFilterElement.getAttribute("class").contains("active")) {
            priceFilterElement.click();
        }

        // Gjej opsionin e intervalit të çmimit sipas inputit
        By priceOption = By.xpath(String.format("//ol[@class='items']/li/a[contains(@href, 'price=%s')]", priceRange));
        WebElement priceElement = wait.until(ExpectedConditions.elementToBeClickable(priceOption));
        priceElement.click();
    }

    public boolean verifySelectedPriceRange(double minPrice, double maxPrice) {
        List<WebElement> products = driver.findElements(By.cssSelector(".product-item")); // Gjej të gjitha produktet

        for (WebElement product : products) {
            WebElement priceElement = product.findElement(By.cssSelector(".price")); // Gjej çmimin e produktit

            String priceText = priceElement.getText().replace("$", "").trim(); // Hiq simbolin "$"
            double price = Double.parseDouble(priceText); // Konverto në double

            // Nëse një produkt është jashtë intervalit, testi dështon
            if (price < minPrice || price > maxPrice) {
                System.out.println("Produkt me çmim jashtë intervalit: $" + price);
                return false;
            }
        }
        return true;
    }

    public void addFirstTwoItemsToWishList() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);

        // Gjej të gjithë butonat e Wish List për produktet
        List<WebElement> wishListButtons = driver.findElements(By.cssSelector("button[title='Add to Wish List']"));

// Sigurohu që ka të paktën 2 produkte në listë
        if (wishListButtons.size() >= 2) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Kliko produktin e parë
            js.executeScript("arguments[0].click();", wishListButtons.get(0));

            // Kliko produktin e dytë pa ndërruar faqen
            js.executeScript("arguments[0].click();", wishListButtons.get(1));

            System.out.println("Të dy produktet u shtuan në Wish List pa ndryshuar faqja.");
        } else {
            System.out.println("Nuk u gjetën mjaftueshëm produkte për të shtuar në Wish List.");
        }
    }

    public void removePriceFilter() {
        WebDriverWait wait = new WebDriverWait(driver, 10); // Prit deri në 10 sekonda
        WebElement removePriceButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(@class, 'item') and .//span[@class='filter-label' and text()='Price']]//a[contains(@class, 'action remove')]")
        ));
        removePriceButton.click();
    }

    public int getProductCount() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Prit derisa të shfaqen produktet në Wish List
        wait.until(ExpectedConditions.visibilityOfElementLocated(wishListItems));

        // Merr të gjithë elementet e produkteve dhe kthen numrin e tyre
        List<WebElement> products = driver.findElements(wishListItems);
        return products.size();
    }
    public boolean isWishListSuccessMessageDisplayed() {
        WebElement successMessage = driver.findElement(wishListSuccessMessage);
        return successMessage.isDisplayed();
    }

    // Kontrollon nëse ikona e Wish List është e dukshme në produkt
    public boolean isWishListIconDisplayed() {
        WebElement icon = driver.findElement(wishListIcon);
        return icon.isDisplayed();
    }

    // Klikon linkun për të hapur shportën
    public void clickShoppingCartLink() {
        driver.findElement(shoppingCartLink).click();
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

}

