package com.app.game.tetris.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
public class Player {
    private String playerName;
    private int playerScore;

    public void setPlayerScore(int collapsedLayersCount) {
        this.playerScore = collapsedLayersCount * 10;
    }

}
