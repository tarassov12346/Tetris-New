package com.tetris1.game.tetris.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public class SavedGame implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String playerName;
    private final int playerScore;
    private final char[][] cells;

    public SavedGame(String playerName, int playerScore, char[][] cells) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.cells = cells;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public char[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "SavedGame{" +
                "playerName='" + playerName + '\'' +
                ", playerScore=" + playerScore +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}
