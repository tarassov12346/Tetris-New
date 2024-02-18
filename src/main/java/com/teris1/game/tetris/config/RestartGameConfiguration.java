package com.teris1.game.tetris.config;

import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.SavedGame;
import com.tetris1.game.tetris.model.serviceImpl.Stage;
import com.tetris1.game.tetris.model.serviceImpl.State;
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
        Player player = new Player(savedGame.getPlayerName(), savedGame.getPlayerScore());
        return new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, player).
                restartWithNewTetramino().
                orElse(new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, new Player(savedGame.getPlayerName(), savedGame.getPlayerScore())));
    }
}
