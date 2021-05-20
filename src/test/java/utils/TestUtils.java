package utils;

import org.openqa.selenium.By;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtils {
    public static String getProperty(String propName) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream("config.properties");
            assert inputStream != null;
            properties.load(inputStream);
            inputStream.close();
            return properties.getProperty(propName);
        } catch (IOException e) {
            System.out.println("Unable to load property '" + propName + "' from 'config.properties'");
        }
        return "";
    }

    public static By formatXpathLocator(By locator, String formatStr) {
        String locatorString = locator.toString();
        String[] locatorStringSplit = locatorString.split(": ");
        return By.xpath(String.format(locatorStringSplit[1], formatStr));
    }
}
