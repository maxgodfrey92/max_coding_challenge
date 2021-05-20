package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.locators.HomePageLocators;
import utils.TestUtils;

/**
 * Page Object Class for the Home Page
 */
public class HomePage extends BasePage {

    /**
     * Constructor to have driver passed in
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Open the page to begin with
     */
    public void navigateTo() {
        String url = TestUtils.getProperty("UI_URL");
        this.driver.get(url);
    }

    /**
     * Performs a search in top search bar for specified search term
     */
    public void performSearch(String searchTerm) {
        // Type search term first
        WebElement searchBar = this.waitForElementVisible(HomePageLocators.searchBar);
        searchBar.sendKeys(searchTerm);

        // Click search button
        WebElement searchBtn = this.waitForElementVisible(HomePageLocators.searchButton);
        searchBtn.click();
    }
}
