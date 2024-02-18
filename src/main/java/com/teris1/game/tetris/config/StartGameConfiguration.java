package com.teris1.game.tetris.config;

import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.serviceImpl.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class StartGameConfiguration {

    @Bean
    public Player createPlayer() {
        List<String> list = new ArrayList<>();
        list.add("Oswaldo");
        list.add("Tommy");
        list.add("Dunny");
        list.add("Bonny");
        list.add("Ira");
        list.add("Wolfy");
        String playerName = list.get(new Random().nextInt(list.size()));
        return new Player(playerName, 0);
    }

    @Bean
    public State initiateState(Player player) {
        return State.createInitialState(player).start().createStateWithNewTetramino().orElse(State.createInitialState(player));
    }
}
