package ui_tests.test;

import javafx.util.Pair;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui_tests.service.GameService;

import java.util.List;

public class GameSaveTest extends CommonConditions {

    @Test(description = "does the restarted game have the same configuration of fallen tetraminos as the saved one")
    public void doesSavedGameRestart() {
        log.info("doesSavedGameRestart Test start");
        GameService gameService = new GameService(driver);
        List<Pair<Integer, Integer>> savedGameCells = gameService.getFilledCellsListSaved();
        List<Pair<Integer, Integer>> restartedGameCells = gameService.getFilledCellsListAfterRestart();
        Assert.assertEquals(savedGameCells, restartedGameCells);
    }
}
