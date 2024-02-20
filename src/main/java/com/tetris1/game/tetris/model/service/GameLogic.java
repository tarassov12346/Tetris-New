package com.tetris1.game.tetris.model.service;

import com.tetris1.game.tetris.model.Tetramino;

public interface GameLogic <T>{
    T moveDown(int step);
    T rotate();
    T moveRight();
    T moveLeft();
    T collapseFilledLayers();
    T addTetramino();
    T setTetramino(Tetramino tetramino, int x, int y);
    void setPause();
    void unsetPause();
    boolean checkCollision(int dx, int dy, boolean rotate);
}