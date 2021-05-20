package ui.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.TestUtils;

/**
 * Class for any Driver functions needed
 * Currently just holds method to start driver
 */
public class DriverUtils {
    /**
     * Starts a new webdriver based on browser entered in config.properties
     */
    public static WebDriver startDriver() {
        WebDriver driver;
        // Get the browser specified in config.properties
        String browser = TestUtils.getProperty("BROWSER").toLowerCase();
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", TestUtils.getProperty("FIREFOX_DRIVER_PATH"));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                // Falls through
            default:
                // Default is Chrome Driver
                System.setProperty("webdriver.chrome.driver", TestUtils.getProperty("CHROME_DRIVER_PATH"));
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }
}
