package bdd_tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin = {"pretty"},
        features = "src/test/resources/features/attributes", glue = "bdd_tests/attributes",
        tags = "@save")
public class RunCucumberTest extends AbstractTestNGCucumberTests{

}
