package ui;

import org.junit.Assert;
import org.junit.Test;
import ui.pages.CheckoutPage;
import ui.pages.HomePage;
import ui.pages.SearchResultsPage;

/**
 * Test file for the Product flow
 */
public class ProductTest extends BaseUITest {

    /**
     * Test to search for product, add to cart, and verify total price
     */
    @Test
    public void testProductToCart() {
        // First, search for the product
        String productName = "Printed Chiffon Dress";
        HomePage homePage = new HomePage(this.driver);
        homePage.navigateTo();
        homePage.performSearch(productName);

        // Find product and add to cart, then get expected product price
        SearchResultsPage searchResultsPage = new SearchResultsPage(this.driver);
        searchResultsPage.addProductToCartAndCheckout(productName);
        Double expProductPrice = searchResultsPage.getRunningPrice();

        // On the Checkout page, get the product, shipping, and total price
        CheckoutPage checkoutPage = new CheckoutPage(this.driver);
        Double actProductPrice = checkoutPage.getTotalProductPrice();
        Double actShippingPrice = checkoutPage.getShippingPrice();
        Double actTotalPrice = checkoutPage.getTotalPrice();

        // Verifies total product price is correct
        Assert.assertEquals("Expected Product price of '" + expProductPrice + "' but saw '" + actProductPrice + "'", expProductPrice, actProductPrice);

        // Then verify that product price plus shipping equals the correct total price
        Double expTotalPrice = actShippingPrice + actProductPrice;
        Assert.assertEquals("Expected Total price of '" + expTotalPrice + "' but saw '" + actTotalPrice + "'", expTotalPrice, actTotalPrice);
    }
}
