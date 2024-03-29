package bdd_tests.runners;

import api_tests.APITest;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ui_tests.driver.DriverSingleton;

@CucumberOptions(plugin = {"pretty"},
        features = "src/test/resources/features/attributes", glue = "bdd_tests/steps",
        tags = "@save")
public class RunCucumberTest extends AbstractTestNGCucumberTests{
    protected static final Logger log = Logger.getLogger(RunCucumberTest.class);

    @BeforeClass
    public void doBeforeTests() {
        log.info("BDDTests start");
    }

    @AfterClass
    public void doAfterTests() {
        DriverSingleton.closeDriver();
        log.info("BDD Tests are finished");
    }
}
