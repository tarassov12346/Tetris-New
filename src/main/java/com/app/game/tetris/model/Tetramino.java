package com.app.game.tetris.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Scope("prototype")
public class Tetramino {
    private final char[][] shape;

    public Tetramino(char[][] shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tetramino tetramino = (Tetramino) o;
        return Arrays.deepEquals(shape, tetramino.shape);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(shape);
    }

    public char[][] getShape() {
        return this.shape;
    }
}
