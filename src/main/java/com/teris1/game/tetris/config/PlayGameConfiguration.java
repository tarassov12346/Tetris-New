package com.teris1.game.tetris.config;

import com.tetris1.game.tetris.model.State;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Optional;

@Configuration
public class PlayGameConfiguration {

    @Bean
    @Scope("prototype")
    public State dropDownState(State state){
        return state.tryDropDown().orElse(state);
    }

    @Bean
    @Scope("prototype")
    public State moveRightState(State state){
        return state.tryMoveRight().orElse(state);
    }

    @Bean
    @Scope("prototype")
    public State moveLeftState(State state){
        return state.tryMoveLeft().orElse(state);
    }

    @Bean
    @Scope("prototype")
    public State rotateState(State state){
        return state.tryRotate().orElse(state);
    }

    @Bean
    @Scope("prototype")
    public Optional<State> moveDownState(State state){
        return state.tryMoveDown(State.stepDownArray[0]);
    }

    @Bean
    @Scope("prototype")
    public Optional<State> newTetraminoState(State state){
        return state.newTetramino();
    }

}
