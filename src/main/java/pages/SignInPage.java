package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage extends BasePage {
    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        driver.findElement(By.id("email")).sendKeys(email);
    }
    public void enterPassword(String password) {
        driver.findElement(By.id("pass")).sendKeys(password);
    }
    public void clickSignIn() {
        driver.findElement(By.id("send2")).click();
    }
}
