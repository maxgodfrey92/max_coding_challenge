package ui;

import org.junit.Assert;
import org.junit.Test;
import ui.pages.CheckoutPage;
import ui.pages.HomePage;
import ui.pages.SearchResultsPage;

public class ProductTest extends BaseUITest {

    @Test
    public void testProductToCart() {
        String productName = "Printed Chiffon Dress";
        HomePage homePage = new HomePage(this.driver);
        homePage.navigateTo();
        homePage.performSearch(productName);

        SearchResultsPage searchResultsPage = new SearchResultsPage(this.driver);
        searchResultsPage.addProductToCartAndCheckout(productName);
        Double expProductPrice = searchResultsPage.getRunningPrice();

        CheckoutPage checkoutPage = new CheckoutPage(this.driver);
        Double actProductPrice = checkoutPage.getTotalProductPrice();
        Double actShippingPrice = checkoutPage.getShippingPrice();
        Double actTotalPrice = checkoutPage.getTotalPrice();

        Assert.assertEquals(expProductPrice, actProductPrice);
        Assert.assertEquals((Double)(actShippingPrice + actProductPrice), actTotalPrice);
    }
}
