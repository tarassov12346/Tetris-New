package com.tetris1.game.tetris.model.serviceImpl;

import com.tetris1.game.tetris.model.Dao;
import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.Tetramino;
import com.tetris1.game.tetris.model.service.GameLogic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Scope("prototype")
public class State implements GameLogic<Optional<State>> {
    public final Stage stage;
    public final boolean isRunning;
    public final Player player;
    static public final int[] stepDownArray = {1};

    ApplicationContext context =new AnnotationConfigApplicationContext("com.tetris1.game.tetris.model");
    Dao dao=  context.getBean(Dao.class);

    public State(Stage stage, boolean isRunning, Player player) {
        this.stage = Objects.requireNonNull(stage);
        this.isRunning = isRunning;
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return isRunning == state.isRunning && stage.equals(state.stage) && player.equals(state.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stage, isRunning, player);
    }

    public static State createInitialState(Player player) {
        return new State(Stage.createEmptyStage(), false, player);
    }

    public State start() {
        return new State(stage, true, player);
    }

    public void stop() {
        dao.recordScore(player);
        dao.retrieveScores();
        new State(stage, false, player);
    }

    @Override
    public Optional<State> moveLeft() {
        return !checkCollision(-1, 0, false) ? Optional.of(moveTetraminoLeft()) : Optional.empty();
    }

    @Override
    public Optional<State> moveRight() {
        return !checkCollision(1, 0, false) ? Optional.of(moveTetraminoRight()) : Optional.empty();
    }

    @Override
    public Optional<State> moveDown(int step) {
        int yToStepDown;
        for (yToStepDown = 0; (yToStepDown <= step) && (yToStepDown < Stage.HEIGHT); yToStepDown++) {
            if (checkCollision(0, yToStepDown, false)) break;
        }
        return !checkCollision(0, 1, false) ? Optional.of(moveTetraminoDown(yToStepDown - 1)) : Optional.empty();
    }

    @Override
    public Optional<State> rotate() {
        return !checkCollision(0, 0, true) ? Optional.of(rotateTetramino()) : Optional.empty();
    }

    @Override
    public void setPause() {
        stage.setPause();
    }

    @Override
    public void unsetPause() {
        stage.unsetPause();
    }

    @Override
    public Optional<State> setTetramino(Tetramino tetramino, int x, int y) {
        return Optional.of(new State(stage.setTetramino(tetramino, x, y), isRunning, player));
    }

    @Override
    public Optional<State> addTetramino() {
        return Optional.of(new State(stage.addTetramino(), isRunning, player));
    }

    @Override
    public Optional<State> collapseFilledLayers() {
        return Optional.of(new State(stage.collapseFilledLayers(), isRunning, player));
    }

    @Override
    public boolean checkCollision(int dx, int dy, boolean rotate) {
        return stage.checkCollision(dx, dy, rotate);
    }

    public Optional<State> createStateWithNewTetramino() {
        final Tetramino t = Tetramino.getRandomTetramino();
        final State newState = addTetramino().orElse(this)
                .collapseFilledLayers().orElse(this)
                .updatePlayerScore()
                .setTetramino(t, (Stage.WIDTH - t.getShape().length) / 2, 0).orElse(this);
        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
    }

    public Optional<State> restartWithNewTetramino() {
        final Tetramino t = Tetramino.getRandomTetramino();
        final State newState = addTetramino().orElse(this)
                .setTetramino(t, (Stage.WIDTH - t.getShape().length) / 2, 0).orElse(this);
        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
    }

    public Optional<State> dropDown() {
        int yToDropDown;
        for (yToDropDown = 0; yToDropDown < Stage.HEIGHT; yToDropDown++) {
            if (checkCollision(0, yToDropDown, false)) break;
        }
        return !checkCollision(0, yToDropDown - 1, false) ? Optional.of(moveTetraminoDown(yToDropDown - 1)) : Optional.empty();
    }

    private State updatePlayerScore() {
        player.setPlayerScore(stage.collapsedLayersCount);
        stepDownArray[0] = 1 + stage.collapsedLayersCount;
        return new State(stage.collapseFilledLayers(), isRunning, player);
    }

    private State moveTetraminoDown(int yToMoveDown) {
        return new State(stage.moveDown(yToMoveDown), isRunning, player);
    }

    private State moveTetraminoLeft() {
        return new State(stage.moveLeft(), isRunning, player);
    }

    private State moveTetraminoRight() {
        return new State(stage.moveRight(), isRunning, player);
    }

    private State rotateTetramino() {
        return new State(stage.rotate(), isRunning, player);
    }
}
