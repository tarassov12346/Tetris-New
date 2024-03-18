package ui_tests.service;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import ui_tests.driver.DriverSingleton;
import ui_tests.page.TetrisPage;
import unit_tests.UnitTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameService {
    private final Logger log = Logger.getLogger(UnitTest.class.getName());
    private final WebDriver driver = DriverSingleton.getDriver();
    private TetrisPage tetrisPage;

    public GameService(WebDriver driver) {
        tetrisPage = new TetrisPage(driver);
    }

    public List<Pair<Integer, Integer>> getFilledCellsListSaved() {
        runScenarioBeforeGameSaving();
        List<Pair<Integer, Integer>> filledCellsListSaved = getFilledCells();
        log.info("filledCellsList saved:");
        log.info(Arrays.toString(filledCellsListSaved.toArray()));
        return filledCellsListSaved;
    }

    public List<Pair<Integer, Integer>> getFilledCellsListAfterRestart() {
        runScenarioAfterGameSaving();
        List<Pair<Integer, Integer>> filledCellsListAfterRestart = getFilledCells();
        log.info("filledCellsList after restart:");
        log.info(Arrays.toString(filledCellsListAfterRestart.toArray()));
        return filledCellsListAfterRestart;
    }

    private List<Pair<Integer, Integer>> getFilledCells() {
        List<Pair<Integer, Integer>> filledCellsCoordinatesList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                if (!tetrisPage.getCellValue(i, j).equals("0.png")) {
                    filledCellsCoordinatesList.add(new Pair(i, j));
                }
            }
        }
        return getFilledCellsToCompare(filledCellsCoordinatesList);
    }

    private List<Pair<Integer, Integer>> getFilledCellsToCompare(List<Pair<Integer, Integer>> cellsList) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Pair<Integer, Integer> coordinate : cellsList) {
            if (coordinate.getKey() >= 10) list.add(coordinate);
        }
        return list;
    }

    private void runScenarioBeforeGameSaving() {
        tetrisPage = tetrisPage.openPage();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickLeftButton().clickLeftButton().clickLeftButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickSaveButton();
    }

    private void runScenarioAfterGameSaving() {
        tetrisPage = tetrisPage.openPage();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRightButton().clickRightButton().clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickDropButton();
        makeUserWait(3);
        tetrisPage = tetrisPage.clickRestartButton().clickRestartButton();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("clearInterval(myTimer);");
    }

    private void makeUserWait(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
