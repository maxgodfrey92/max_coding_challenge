package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui.locators.SearchResultsLocators;
import utils.TestUtils;

/**
 * Page Object class for Search Results page
 */
public class SearchResultsPage extends BasePage {

    // Running price to keep track of total price if more than 1 product added
    Double runningPrice;

    /**
     * Constructor to have driver passed in
     * Also sets running price to 0
     */
    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.runningPrice = 0.0;
    }

    public Double getRunningPrice() {
        return this.runningPrice;
    }

    public void addToRunningPrice(Double runningPrice) {
        this.runningPrice += runningPrice;
    }

    /**
     * Finds specified product on the page, adds price to running price, and adds to cart
     */
    public void addProductToCart(String productName) {
        // Scroll the specified product into view first
        By productLocator = TestUtils.formatXpathLocator(SearchResultsLocators.productContainer, productName);
        WebElement productContainer = this.waitForElementVisible(productLocator);
        this.scrollIntoView(productContainer);

        // Hovers over the specified product, which then brings Add to Cart button into view
        Actions actions = new Actions(this.driver);
        actions.moveToElement(productContainer).perform();

        // Gets the specified product price, and adds it to the running total for the Page Object
        String productPriceStr = this.waitForElementVisibleInParent(productContainer, SearchResultsLocators.productPrice).getText();
        Double productPrice = Double.parseDouble(productPriceStr.replace("$", ""));
        this.addToRunningPrice(productPrice);

        // Add the product to the Cart
        WebElement productAddToCart = this.waitForElementVisibleInParent(productContainer, SearchResultsLocators.productAddToCart);
        productAddToCart.click();
    }

    /**
     * Uses above method to add product to cart, then choose to Checkout rather than continue shopping
     */
    public void addProductToCartAndCheckout(String productName) {
        // Add product to cart using above method
        this.addProductToCart(productName);

        // Selects the Proceed to Checkout button in the resultant popup
        WebElement proceedToCheckout = this.waitForElementVisible(SearchResultsLocators.proceedToCheckout);
        proceedToCheckout.click();
    }

    // If needed, further method could be added to continue shopping instead
}
