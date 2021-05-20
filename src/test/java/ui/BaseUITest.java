package ui;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ui.utils.DriverUtils;

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
