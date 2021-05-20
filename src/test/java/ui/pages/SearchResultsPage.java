package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ui.locators.SearchResultsLocators;
import utils.TestUtils;

public class SearchResultsPage extends BasePage {

    Double runningPrice;

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

    public void addProductToCart(String productName) {
        By productLocator = TestUtils.formatXpathLocator(SearchResultsLocators.productContainer, productName);
        WebElement productContainer = this.waitForElementVisible(productLocator);
        this.scrollIntoView(productContainer);

        Actions actions = new Actions(this.driver);
        actions.moveToElement(productContainer).perform();

        String productPriceStr = this.waitForElementVisibleInParent(productContainer, SearchResultsLocators.productPrice).getText();
        Double productPrice = Double.parseDouble(productPriceStr.replace("$", ""));
        this.addToRunningPrice(productPrice);

        WebElement productAddToCart = this.waitForElementVisibleInParent(productContainer, SearchResultsLocators.productAddToCart);
        productAddToCart.click();
    }

    public void addProductToCartAndCheckout(String productName) {
        this.addProductToCart(productName);

        WebElement proceedToCheckout = this.waitForElementVisible(SearchResultsLocators.proceedToCheckout);
        proceedToCheckout.click();
    }
}
