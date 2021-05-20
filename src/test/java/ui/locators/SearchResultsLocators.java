package ui.locators;

import org.openqa.selenium.By;

/**
 * Page locators for the Search Results page
 */
public class SearchResultsLocators {
    public static By productContainer = By.xpath("//div[@class='product-container' and .//a[@title='%s']]");
    public static By productPrice = By.xpath("//span[@itemprop='price']");
    public static By productAddToCart = By.xpath("//a[@title='Add to cart']");
    public static By proceedToCheckout = By.xpath("//a[@title='Proceed to checkout']");
}
