package com.tetris1.game.tetris.model;

import java.util.Objects;
import java.util.Optional;

public class State {
    public final Stage stage;
    public final boolean isRunning;
    public final Player player;
    public Dao dao = new Dao();
    static public final int[] stepDownArray = {1};

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

    public static State initialState(Player player) {
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

    public Optional<State> newTetramino() {
        final Tetramino t = Tetramino.getRandomTetramino();
        final State newState = addTetraminoToState()
                .collapseFullLayers()
                .updatePlayerScore()
                .setTetraminoToState(t, (Stage.WIDTH - t.getShape().length) / 2);

        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
    }

    public Optional<State> restartWithNewTetramino() {
        final Tetramino t = Tetramino.getRandomTetramino();
        final State newState = addTetraminoToState()

                .setTetraminoToState(t, (Stage.WIDTH - t.getShape().length) / 2);

        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
    }

    public Optional<State> tryMoveLeft() {
        return !checkCollision(-1, 0, false) ? Optional.of(moveTetraminoLeft()) : Optional.empty();
    }

    public Optional<State> tryMoveRight() {
        return !checkCollision(1, 0, false) ? Optional.of(moveTetraminoRight()) : Optional.empty();
    }

    public Optional<State> tryMoveDown(int stepDown) {
        int yToStepDown;
        for (yToStepDown = 0; (yToStepDown <= stepDown) && (yToStepDown < Stage.HEIGHT); yToStepDown++) {
            if (checkCollision(0, yToStepDown, false)) break;
        }
        return !checkCollision(0, 1, false) ? Optional.of(moveTetraminoDown(yToStepDown - 1)) : Optional.empty();
    }

    public Optional<State> tryDropDown() {
        int yToDropDown;
        for (yToDropDown = 0; yToDropDown < Stage.HEIGHT; yToDropDown++) {
            if (checkCollision(0, yToDropDown, false)) break;
        }
        return !checkCollision(0, yToDropDown - 1, false) ? Optional.of(moveTetraminoDown(yToDropDown - 1)) : Optional.empty();
    }

    public Optional<State> tryRotate() {
        return !checkCollision(0, 0, true) ? Optional.of(rotateTetramino()) : Optional.empty();
    }

    public void setPause() {
        stage.setPause();
    }

    public void unSetPause() {
        stage.unSetPause();
    }

    private State setTetraminoToState(Tetramino tetramino, int x) {
        return new State(stage.setTetraminoToStage(tetramino, x, 0), isRunning, player);
    }

    private State addTetraminoToState() {
        return new State(stage.addTetraminoToStage(), isRunning, player);
    }

    public State collapseFullLayers() {

        return new State(stage.collapseFullLayers(), isRunning, player);
    }

    private State updatePlayerScore() {
        player.setPlayerScore(stage.collapsedLayersCount);
        stepDownArray[0] = 1 + stage.collapsedLayersCount;
        return new State(stage.collapseFullLayers(), isRunning, player);
    }

    private boolean checkCollision(int dx, int dy, boolean rotate) {
        return stage.checkCollision(dx, dy, rotate);
    }

    private State moveTetraminoDown(int yToMoveDown) {
        return new State(stage.moveTetraminoDown(yToMoveDown), isRunning, player);
    }

    private State moveTetraminoLeft() {
        return new State(stage.moveTetraminoLeft(), isRunning, player);
    }

    private State moveTetraminoRight() {
        return new State(stage.moveTetraminoRight(), isRunning, player);
    }

    private State rotateTetramino() {
        return new State(stage.rotateCcw(), isRunning, player);
    }
}
