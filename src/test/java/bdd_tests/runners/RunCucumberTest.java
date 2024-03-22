package bdd_tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin = {"pretty"},
        features = "src/test/resources/features/attributes", glue = "bdd_tests/steps",
        tags = "@save")
public class RunCucumberTest extends AbstractTestNGCucumberTests{
}
