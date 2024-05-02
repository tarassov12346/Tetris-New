package com.app.game.tetris.config;

import com.app.game.tetris.model.Player;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.model.Tetramino;
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
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SavedGame savedGame = (SavedGame) objectInputStream.readObject();
        ApplicationContext contextPlayer = new AnnotationConfigApplicationContext("com.app.game.tetris.model");
        ApplicationContext contextService = new AnnotationConfigApplicationContext("com.app.game.tetris.serviceImpl");
        Player player = contextPlayer.getBean(Player.class, savedGame.getPlayerName(), savedGame.getPlayerScore());
        Stage recreatedStage = contextService.getBean(Stage.class, savedGame.getCells(), getTetramino0(), 0, 0, player.getPlayerScore() / 10);
        return contextService.getBean(State.class, recreatedStage, true, player).restartWithNewTetramino().orElse(contextService.getBean(State.class, recreatedStage, true, player));
    }

    private Tetramino getTetramino0(){
        ApplicationContext context =new AnnotationConfigApplicationContext("com.app.game.tetris.model");
        return context.getBean(Tetramino.class, (Object) new char[][]{{'0'}});
    }
}
