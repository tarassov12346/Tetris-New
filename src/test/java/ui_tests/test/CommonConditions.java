package ui_tests.test;

import api_tests.APITest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import ui_tests.driver.DriverSingleton;

public class CommonConditions {
    protected static final Logger log = Logger.getLogger(APITest.class);
    public WebDriver driver;

    @BeforeClass(description = "setUp() gets the driver type from the bundle")
    public void setUp() {
        driver = DriverSingleton.getDriver();
    }

    @BeforeTest
    public void doBeforeTests() {
        log.info("UITests start");
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterTest
    public void doAfterTests() {
        log.info("UITests are finished");
    }
}
