package ui_tests.test;

import api_tests.APITest;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import ui_tests.driver.DriverSingleton;
import ui_tests.service.GameService;

import java.util.List;

public class CommonConditions {
    protected static final Logger log = Logger.getLogger(APITest.class);
    public WebDriver driver;
    List<Pair<Integer, Integer>> savedGameCells;
    List<Pair<Integer, Integer>> restartedGameCells;
    String savedPLayerName;
    String savedPlayerScore;
    String restartedPLayerName;
    String restartedPlayerScore;

    @BeforeClass
    public void doBeforeTests() {
        log.info("UITests start");
        driver = DriverSingleton.getDriver();
        GameService gameService = new GameService(driver);
        savedGameCells = gameService.getFilledCellsListSaved();
        restartedGameCells = gameService.getFilledCellsListAfterRestart();
        savedPLayerName = gameService.savedPLayerName;
        savedPlayerScore = gameService.savedPlayerScore;
        restartedPLayerName = gameService.restartedPLayerName;
        restartedPlayerScore = gameService.restartedPlayerScore;
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterClass
    public void doAfterTests() {
        log.info("UITests are finished");
    }
}
