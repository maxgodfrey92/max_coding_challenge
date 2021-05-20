package utils;

import org.openqa.selenium.By;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Some helpful utility functions to be used as part of tests
 */
public class TestUtils {
    /**
     * Get a property from config.properties based on property name specified
     */
    public static String getProperty(String propName) {
        try {
            // Get config.properties file first
            Properties properties = new Properties();
            InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream("config.properties");
            assert inputStream != null;
            properties.load(inputStream);
            inputStream.close();

            // Return the property specified
            return properties.getProperty(propName);
        } catch (IOException e) {
            // Print to console if can't access config.properties
            System.out.println("Unable to load property '" + propName + "' from 'config.properties'");
        }
        // Return an empty string if any problems. This will need to be considered when using this method
        return "";
    }

    /**
     * Formats any xpath locators with variable text to the text specified
     * For example, locators for a specific product
     */
    public static By formatXpathLocator(By locator, String formatStr) {
        // Split the locator into its 2 parts, By and Locator
        String locatorString = locator.toString();
        String[] locatorStringSplit = locatorString.split(": ");

        // Return newly formatted Locator in a new By
        return By.xpath(String.format(locatorStringSplit[1], formatStr));
    }
}
