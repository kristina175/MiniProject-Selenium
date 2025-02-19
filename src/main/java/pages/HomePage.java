package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    private WebDriver driver;
    private By womenMenu = By.xpath("//span[text()='Women']");
    private By topsOption = By.xpath("//a[text()='Tops']");
    private By jacketsOption = By.xpath("//a[text()='Jackets']");
    private By createAccountLink = By.linkText("Create an Account");
    private By signInLink = By.linkText("Sign In");
    private By userProfile = By.cssSelector(".customer-name");
    private By signOutLink = By.linkText("Sign Out");
    private By wishListLink = By.cssSelector("a[href*='wishlist']");
    private final By wishListItemCount = By.cssSelector(".counter-number");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void hoverOverWomenMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement womenElement = wait.until(ExpectedConditions.elementToBeClickable(womenMenu));
        womenElement.click();
    }

    public void clickTopsOption() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement topsElement = wait.until(ExpectedConditions.elementToBeClickable(topsOption));

    }

    public void clickJacketsOption() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Actions actions = new Actions(driver);

        // Hover mbi "Women"
        WebElement womenElement = wait.until(ExpectedConditions.visibilityOfElementLocated(womenMenu));
        actions.moveToElement(womenElement).perform();

        // Hover mbi "Tops"
        WebElement topsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(topsOption));
        actions.moveToElement(topsElement).perform();

        // Kliko "Jackets" pasi të jetë bërë i dukshëm
        WebElement jacketsElement = wait.until(ExpectedConditions.elementToBeClickable(jacketsOption));
        jacketsElement.click();
    }

    public void clickCreateAccount() {
        driver.findElement(createAccountLink).click();
    }

    public void clickSignIn() {
        driver.findElement(signInLink).click();
    }

    public void clickUserProfile() {
        driver.findElement(userProfile).click();
    }

    public void clickSignOut() {
        driver.findElement(signOutLink).click();
    }

    public void clickWishList() {
        driver.findElement(wishListLink).click();
    }

}
