package com.teris1.game.tetris.config;


import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.SavedGame;
import com.tetris1.game.tetris.model.Stage;
import com.tetris1.game.tetris.model.State;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class RestartGameConfiguration {

    @Bean
    public State recreateState( ) throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("C:\\JavaProjects\\2\\Tetris-New\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SavedGame savedGame = (SavedGame) objectInputStream.readObject();

        Player player=new Player(savedGame.getPlayerName(), savedGame.getPlayerScore());

        return  new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, player).
                restartWithNewTetramino().
                orElse(new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, new Player(savedGame.getPlayerName(), savedGame.getPlayerScore())));
    }
}
