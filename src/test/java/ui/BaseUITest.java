package ui;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ui.utils.DriverUtils;

/**
 * Base Class for UI Tests to inherit
 * Contains Before and After methods to start and stop Driver respectively
 */
public class BaseUITest {

    WebDriver driver;

    @Before
    public void setUp() {
        this.driver = DriverUtils.startDriver();
    }

    @After
    public void tearDown() {
        this.driver.quit();
    }

}
