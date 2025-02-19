package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static java.time.Duration.*;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void waitForElementToBeVisible(org.openqa.selenium.By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(org.openqa.selenium.By locator) {
        waitForElementToBeVisible(locator);
        driver.findElement(locator).click();
    }

}
