package com.app.game.tetris.config;

import com.app.game.tetris.model.Player;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.serviceImpl.Stage;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Configuration
public class RestartGameConfiguration {

    @Bean
    public State recreateState() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\JavaProjects\\2\\Tetris-New\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SavedGame savedGame = (SavedGame) objectInputStream.readObject();
        ApplicationContext contextPlayer =
                new AnnotationConfigApplicationContext("com.app.game.tetris.model");
        ApplicationContext contextState =
                new AnnotationConfigApplicationContext("com.app.game.tetris.serviceImpl");
        Player player =contextPlayer.getBean(Player.class,savedGame.getPlayerName(), savedGame.getPlayerScore());
        return contextState.getBean(State.class,Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, player).
        restartWithNewTetramino().
                orElse(contextState.getBean(State.class,Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, player));
    }
}
