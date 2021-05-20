package ui.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.TestUtils;

public class DriverUtils {
    public static WebDriver startDriver() {
        WebDriver driver;
        String browser = TestUtils.getProperty("BROWSER").toLowerCase();
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", TestUtils.getProperty("FIREFOX_DRIVER_PATH"));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                // Falls through to default too
            default:
                System.setProperty("webdriver.chrome.driver", TestUtils.getProperty("CHROME_DRIVER_PATH"));
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }
}
