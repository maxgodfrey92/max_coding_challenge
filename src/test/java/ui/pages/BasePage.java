package ui.pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base class for further Page Object classes
 * Contains helper functions for interacting with elements on page
 * Also contains the driver variable passed in via constructor
 */
public class BasePage {

    WebDriver driver;

    /**
     * Constructor to allow passing of driver into Page Objects
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Some easier to use wrappers around the standard Selenium WebDriverWait functions
     * Uses default timeout of 20 seconds if one not specified
     */
    public WebElement waitForElementVisible(By locator) {
        return waitForElementVisible(locator, 20);
    }

    public WebElement waitForElementVisible(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(this.driver, timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for Element to be visible inside another parent element
     * No standard Selenium method for this, so simple for loop to keep checking until it is visible
     */
    public WebElement waitForElementVisibleInParent(WebElement parentElement, By locator) {
        return waitForElementVisibleInParent(parentElement, locator, 20);
    }

    public WebElement waitForElementVisibleInParent(WebElement parentElement, By locator, int timeout) {
        WebElement element = null;
        for (int i = 0; i <= timeout; i ++) {
            // If reaches timeout, fail the test
            Assert.assertTrue("Waited for " + timeout + "seconds for element with locator '" + locator + "'", i != timeout);
            try {
                // If element can be found, break out of loop
                element = parentElement.findElement(locator);
                break;
            } catch (NoSuchElementException | ElementNotVisibleException e) {
                // If element can't yet be found, wait a second and try again
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    // Ignoring this exception as we'll just wait another second
                }
            }
        }
        return element;
    }

    /**
     * Scrolls the given element into view, so the top of the element aligns with the top of the page
     */
    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
