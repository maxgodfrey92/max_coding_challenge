package ui.pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Some easier to use wrappers around the standard Selenium WebDriverWait functions
    public WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, 20);
    }

    public WebElement waitForElementVisible(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(this.driver, timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementVisibleInParent(WebElement parentElement, By locator) {
        return waitForElementVisibleInParent(parentElement, locator, 20);
    }

    public WebElement waitForElementVisibleInParent(WebElement parentElement, By locator, int timeout) {
        WebElement element = null;
        for (int i = 0; i <= timeout; i ++) {
            Assert.assertTrue("Waited for " + timeout + "seconds for element with locator '" + locator + "'", i != timeout);
            try {
                element = parentElement.findElement(locator);
                break;
            } catch (NoSuchElementException | ElementNotVisibleException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return element;
    }

    // Scrolls the given element into view, so the top of the element aligns with the top of the page
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
