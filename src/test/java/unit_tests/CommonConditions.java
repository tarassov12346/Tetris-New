package unit_tests;

import com.app.game.tetris.model.Tetramino;
import com.app.game.tetris.serviceImpl.Stage;
import com.app.game.tetris.serviceImpl.State;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.app.game.tetris.service.GameLogic.HEIGHT;
import static com.app.game.tetris.service.GameLogic.WIDTH;

class CommonConditions {
    protected static final Logger log = Logger.getLogger(UnitTest.class.getName());
    int moveCount;

    @BeforeTest
    public void doBeforeTests() {
        log.info("UnitTests start");
    }

    @BeforeMethod
    public void doBeforeEachTestMethod() {
        log.info("Test Method  is called");
    }

    protected Stage makeStageWithOnlyLeftUnfilledRows(int collapsedLayerCount) {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, getTetramino0(), 0, 0, collapsedLayerCount);
    }

    protected Stage makeStageWith2FilledRows() {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 4).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 4, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'L';
                        case 2 -> c[y][x] = 'O';
                        default -> c[y][x] = 'I';
                    }
                })
        );
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, getTetramino0(), 0, 0, 0);
    }

    protected Stage makeStageWith3FilledRows() {
        final char[][] c = new char[HEIGHT][WIDTH];
        IntStream.range(0, HEIGHT - 5).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> c[y][x] = '0'));
        IntStream.range(HEIGHT - 5, HEIGHT - 2).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'O';
                        case 1 -> c[y][x] = 'S';
                        case 2 -> c[y][x] = 'I';
                        default -> c[y][x] = 'L';
                    }
                })
        );
        IntStream.range(HEIGHT - 2, HEIGHT).forEach(y -> IntStream.range(0, WIDTH).forEach(x -> {
                    switch (x % 3) {
                        case 0 -> c[y][x] = 'S';
                        case 1 -> c[y][x] = 'I';
                        default -> c[y][x] = '0';
                    }
                })
        );
        return new Stage(c, getTetramino0(), 0, 0, 0);
    }

    protected State moveFarRight(State state) {
        state = state.moveRight().orElse(state);
        log.info("move tetramino 1 step right");
        log.info("tetramino position x=" + state.getStage().getTetraminoX() + " y=" + state.getStage().getTetraminoY());
        moveCount++;
        if (moveCount > 12) return state;
        return moveFarRight(state);
    }

    protected State moveFarLeft(State state) {
        state = state.moveLeft().orElse(state);
        log.info("move tetramino 1 step left");
        log.info("tetramino position x=" + state.getStage().getTetraminoX() + " y=" + state.getStage().getTetraminoY());
        moveCount++;
        if (moveCount > 12) return state;
        return moveFarLeft(state);
    }

    protected State moveDeepDown(State state) {
        state = state.moveDown(1).orElse(state);
        log.info("move tetramino 1 step down");
        log.info("tetramino position x=" + state.getStage().getTetraminoX() + " y=" + state.getStage().getTetraminoY());
        moveCount++;
        if (moveCount > 25) return state;
        return moveDeepDown(state);
    }

    protected Tetramino getTetramino0() {
        return new Tetramino(new char[][]{{'0'}});
    }

    protected Character getShapeTypeByTetramino(Tetramino tetramino) {
        final Map<Character, Tetramino> tetraminoMap = new HashMap<>();
        tetraminoMap.put('0', new Tetramino(new char[][]{{'0'}}));
        tetraminoMap.put('I', new Tetramino(new char[][]{{'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}, {'0', 'I', '0', '0'}}));
        tetraminoMap.put('J', new Tetramino(new char[][]{{'0', 'J', '0'}, {'0', 'J', '0'}, {'J', 'J', '0'}}));
        tetraminoMap.put('L', new Tetramino(new char[][]{{'0', 'L', '0'}, {'0', 'L', '0'}, {'0', 'L', 'L'}}));
        tetraminoMap.put('O', new Tetramino(new char[][]{{'O', 'O'}, {'O', 'O'}}));
        tetraminoMap.put('S', new Tetramino(new char[][]{{'0', 'S', 'S'}, {'S', 'S', '0'}, {'0', '0', '0'}}));
        tetraminoMap.put('T', new Tetramino(new char[][]{{'0', '0', '0'}, {'T', 'T', 'T'}, {'0', 'T', '0'}}));
        tetraminoMap.put('Z', new Tetramino(new char[][]{{'Z', 'Z', '0'}, {'0', 'Z', 'Z'}, {'0', '0', '0'}}));
        tetraminoMap.put('K', new Tetramino(new char[][]{{'K', 'K', 'K'}, {'0', 'K', '0'}, {'0', 'K', '0'}}));
        for (Map.Entry<Character, Tetramino> entry : tetraminoMap.entrySet()) {
            if (Objects.equals(tetramino, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected int countFilledCells(State state) {
        char[][] cells = state.getStage().getCells();
        int count = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (cells[i][j] == '0') {
                    count++;
                    break;
                }
            }
        }
        return HEIGHT - count;
    }

    protected char[][] rotateMatrix(char[][] m) {
        final int h = m.length;
        final int w = m[0].length;
        final char[][] t = new char[h][w];
        IntStream.range(0, h).forEach(y -> IntStream.range(0, w).forEach(x -> t[w - x - 1][y] = m[y][x]));
        return t;
    }

    protected String matrixToString(char[][] m) {
        StringBuilder expectedStr = new StringBuilder();
        expectedStr.append("{");
        for (char[] strings : m) {
            expectedStr.append("{");
            for (char s : strings) {
                expectedStr.append('"');
                expectedStr.append(s);
                expectedStr.append('"');
                expectedStr.append(',');
            }
            expectedStr.deleteCharAt(expectedStr.length() - 1);
            expectedStr.append("},");
        }
        expectedStr.deleteCharAt(expectedStr.length() - 1);
        expectedStr.append("}");
        return expectedStr.toString();
    }

    @AfterMethod
    public void doAfterEachTestMethod() {
        log.info("Test Method  is finished");
    }

    @AfterTest
    public void doAfterTests() {
        log.info("UnitTests are finished");
    }
}
