package com.app.game.tetris.config;

import com.app.game.tetris.model.Player;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SaveGameConfiguration {

    @Bean
    @Scope("prototype")
    public SavedGame saveGame(Player player, State state) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.app.game.tetris.model");
        return context.getBean(SavedGame.class,player.getPlayerName(), player.getPlayerScore(), state.getStage().getCells());
    }
}
