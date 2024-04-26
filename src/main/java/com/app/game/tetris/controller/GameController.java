package com.app.game.tetris.controller;

import com.app.game.tetris.config.PlayGameConfiguration;
import com.app.game.tetris.config.RestartGameConfiguration;
import com.app.game.tetris.config.SaveGameConfiguration;
import com.app.game.tetris.config.StartGameConfiguration;
import com.app.game.tetris.model.Player;
import com.app.game.tetris.model.SavedGame;
import com.app.game.tetris.serviceImpl.State;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

@Controller
public class GameController {
    private HttpSession currentSession;
    private Player player;
    private State state;

    @RequestMapping(value = "/start")
    public ModelAndView gameStart() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession = attr.getRequest().getSession(true);
        ApplicationContext context = new AnnotationConfigApplicationContext(StartGameConfiguration.class);
        player = context.getBean(Player.class);
        state = context.getBean(State.class);
        initiateView();
        makeView();
        return new ModelAndView("index");
    }

    @RequestMapping("/{moveId}")
    public ModelAndView gamePlay(@PathVariable Integer moveId) {
        ApplicationContext context = new AnnotationConfigApplicationContext(PlayGameConfiguration.class);
        switch (moveId) {
            case 0 -> {
                Optional<State> moveDownState= (Optional<State>) context.getBean("moveDownState",state);
                if (moveDownState.isEmpty()) {
                    Optional<State> newTetraminoState= (Optional<State>) context.getBean("newTetraminoState",state);
                    if (newTetraminoState.isEmpty()) {
                        currentSession.setAttribute("isGameOn", false);
                        currentSession.setAttribute("gameStatus", "Game over");
                        state.stop();
                    } else state = newTetraminoState.orElse(state);
                }
                state = moveDownState.orElse(state);
            }
            case 1 -> state = (State) context.getBean("rotateState",state);
            case 2 -> state = (State) context.getBean("moveLeftState",state);
            case 3 -> state = (State) context.getBean("moveRightState",state);
            case 4 -> state = (State) context.getBean("dropDownState",state);
        }
        makeView();
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/save")
    public ModelAndView gameSave() throws IOException {
        state.setPause();
        currentSession.setAttribute("isGameOn", false);
        ApplicationContext context = new AnnotationConfigApplicationContext(SaveGameConfiguration.class);
        SavedGame savedGame = (SavedGame) context.getBean("saveGame", player, state);
        FileOutputStream outputStream = new FileOutputStream("C:\\JavaProjects\\2\\Tetris-New\\save.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(savedGame);
        objectOutputStream.close();
        currentSession.setAttribute("gameStatus", "Game Saved");
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/restart")
    public ModelAndView gameRestart() {
        ApplicationContext context = new AnnotationConfigApplicationContext(RestartGameConfiguration.class);
        state = context.getBean(State.class);
        initiateView();
        makeView();
        return new ModelAndView("index");
    }

    private void initiateView() {
        currentSession.setAttribute("gameStatus", "Game is ON");
        currentSession.setAttribute("isGameOn", true);
        state.unsetPause();
    }

    private void makeView() {
        char[][] cells = state.getStage().drawTetraminoOnCells();
        player = state.getPlayer();
        state.setStepDown(player.getPlayerScore() / 10 + 1);
        currentSession.setAttribute("player", player.getPlayerName());
        currentSession.setAttribute("score", player.getPlayerScore());
        currentSession.setAttribute("bestplayer", state.dao.bestPlayer);
        currentSession.setAttribute("bestscore", state.dao.bestScore);
        currentSession.setAttribute("stepdown", state.getStepDown());
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                currentSession.setAttribute(new StringBuilder("cells").append(i).append("v").append(j).toString(),
                        new StringBuilder("/img/").append(cells[i][j]).append(".png").toString());
            }
        }
    }
}
