package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateAccountPage extends BasePage {
    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }
    public void enterFirstName(String firstName) {
        driver.findElement(By.id("firstname")).sendKeys(firstName);
    }
    public void enterLastName(String lastName) {
        driver.findElement(By.id("lastname")).sendKeys(lastName);
    }
    public void enterEmail(String email) {
        driver.findElement(By.id("email_address")).sendKeys(email);
    }
    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }
    public void confirmPassword(String password) {
        driver.findElement(By.id("password-confirmation")).sendKeys(password);
    }
    public void clickCreateAccount() {
        driver.findElement(By.cssSelector("button[title='Create an Account']")).click();
    }
}