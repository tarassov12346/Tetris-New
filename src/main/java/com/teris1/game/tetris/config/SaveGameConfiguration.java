package com.teris1.game.tetris.config;

import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.SavedGame;
import com.tetris1.game.tetris.model.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SaveGameConfiguration {


    @Bean
    @Scope("prototype")
    public SavedGame saveGame(Player player, State state){

        return new SavedGame(player.getPlayerName(), player.getPlayerScore(),
                state.stage.getCells());
    }
}
