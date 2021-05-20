package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.locators.HomePageLocators;
import utils.TestUtils;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        String url = TestUtils.getProperty("UI_URL");
        this.driver.get(url);
    }

    public void performSearch(String searchTerm) {
        WebElement searchBar = this.waitForElementVisible(HomePageLocators.searchBar);
        searchBar.sendKeys(searchTerm);

        WebElement searchBtn = this.waitForElementVisible(HomePageLocators.searchButton);
        searchBtn.click();
    }
}
