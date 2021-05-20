package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.locators.CheckoutLocators;

/**
 * Page Object Class for the Checkout page
 */
public class CheckoutPage extends BasePage {

    /**
     * Constructor to have driver passed in
     */
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Simple method to get text from element with given locator, and parse it into a Double
     */
    public Double getPrice(By priceLocator) {
        WebElement priceElement = this.waitForElementVisible(priceLocator);
        return Double.parseDouble(priceElement.getText().replace("$",  ""));
    }

    public Double getTotalProductPrice() {
        return this.getPrice(CheckoutLocators.totalProductPrice);
    }

    public Double getShippingPrice() {
        return this.getPrice(CheckoutLocators.shippingPrice);
    }

    public Double getTotalPrice() {
        return this.getPrice(CheckoutLocators.totalPrice);
    }
}
