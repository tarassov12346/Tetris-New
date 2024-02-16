package com.tetris1.game.tetris.model;

import com.tetris1.game.tetris.model.service.GameLogic;

import java.util.Objects;
import java.util.Optional;

public class State implements GameLogic<Optional<State> > {
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
        final State newState = addTetramino().orElse(this)
                .collapseFilledLayers().orElse(this)
                .updatePlayerScore()
                .setTetramino(t, (Stage.WIDTH - t.getShape().length) / 2,0).orElse(this);
        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
    }

    public Optional<State> restartWithNewTetramino() {
        final Tetramino t = Tetramino.getRandomTetramino();
        final State newState = addTetramino().orElse(this)
                .setTetramino(t, (Stage.WIDTH - t.getShape().length) / 2,0).orElse(this);
        return !newState.checkCollision(0, 0, false) ? Optional.of(newState) : Optional.empty();
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





    public Optional<State> dropDown() {
        int yToDropDown;
        for (yToDropDown = 0; yToDropDown < Stage.HEIGHT; yToDropDown++) {
            if (checkCollision(0, yToDropDown, false)) break;
        }
        return !checkCollision(0, yToDropDown - 1, false) ? Optional.of(moveTetraminoDown(yToDropDown - 1)) : Optional.empty();
    }



    @Override
    public Optional<State> rotate() {
        return !checkCollision(0, 0, true) ? Optional.of(rotateTetramino()) : Optional.empty();
    }




    public void setPause() {
        stage.setPause();
    }

    public void unSetPause() {
        stage.unSetPause();
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




    private State updatePlayerScore() {
        player.setPlayerScore(stage.collapsedLayersCount);
        stepDownArray[0] = 1 + stage.collapsedLayersCount;
        return new State(stage.collapseFilledLayers(), isRunning, player);
    }

    private boolean checkCollision(int dx, int dy, boolean rotate) {
        return stage.checkCollision(dx, dy, rotate);
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
