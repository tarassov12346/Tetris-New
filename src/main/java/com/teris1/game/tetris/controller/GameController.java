package com.teris1.game.tetris.controller;

import com.tetris1.game.tetris.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class GameController {

    static HttpSession currentSession;
    static Player player;
    static State state;



    @RequestMapping(value = "/")
    public ModelAndView test(HttpServletResponse response) throws IOException {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/login")
    public ModelAndView loginView() {
        System.out.println("zzzzzzzzzzzzz!!!!!!");

        return new ModelAndView("login");
    }

    @RequestMapping(value = "/signup")
    public ModelAndView signUpView() {
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/gameScreen")
    public ModelAndView gameView() {
        return new ModelAndView("gameScreen");
    }

    @RequestMapping(value = "/start")
    public ModelAndView gameStart() {

        System.out.println("start");
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        currentSession =attr.getRequest().getSession(true);


            player = new Player().createPlayer();
        System.out.println(player.getPlayerName());
            state = State.initialState(player).start().newTetramino().orElse(State.initialState(player));
            initiateView();


        return new ModelAndView("index");
    }

    @RequestMapping("/{moveId}")
    public ModelAndView gamePlay(@PathVariable Integer moveId) {

        switch (moveId) {
            case 0 -> {
                if (GameController.state.tryMoveDown(State.stepDownArray[0]).isEmpty()) {
                    if (GameController.state.newTetramino().isEmpty()) {
                        currentSession.setAttribute("isGameOn", false);
                        currentSession.setAttribute("gameStatus", "Game over");
                        GameController.state.stop();
                    } else GameController.state = GameController.state.newTetramino().orElse(GameController.state);
                }
                if (GameController.state.tryMoveDown(State.stepDownArray[0]).isPresent()) GameController.state =
                        GameController.state.tryMoveDown(State.stepDownArray[0]).orElse(GameController.state);
            }
            case 1 -> GameController.state = GameController.state.tryRotate().orElse(GameController.state);
            case 2 -> GameController.state = GameController.state.tryMoveLeft().orElse(GameController.state);
            case 3 -> GameController.state = GameController.state.tryMoveRight().orElse(GameController.state);
            case 4 -> GameController.state = GameController.state.tryDropDown().orElse(GameController.state);
        }
        makeView();
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/save")
    public ModelAndView gameSave() throws IOException {
        state.setPause();
        currentSession.setAttribute("isGameOn", false);
        SavedGame savedGame =
                new SavedGame(player.getPlayerName(), player.getPlayerScore(),
                        state.stage.getCells());
        FileOutputStream outputStream = new FileOutputStream("C:\\JavaProjects\\2\\Tetris-New\\save.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(savedGame);
        objectOutputStream.close();
        currentSession.setAttribute("gameStatus", "Game Saved");
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/restart")
    public ModelAndView gameRestart() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\JavaProjects\\2\\Tetris-New\\save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        try {
            SavedGame savedGame = (SavedGame) objectInputStream.readObject();
            player = new Player(savedGame.getPlayerName(), savedGame.getPlayerScore());
            state =
                    new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, player).
                            restartWithNewTetramino().
                            orElse(new State(Stage.recreateStage(savedGame.getCells(), player.getPlayerScore() / 10), true, new Player(savedGame.getPlayerName(), savedGame.getPlayerScore())));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        State.stepDownArray[0] = player.getPlayerScore() / 10 + 1;
        initiateView();
        makeView();
        return new ModelAndView("index");
    }

    private void initiateView(){
        currentSession.setAttribute("gameStatus", "Game is ON");
        currentSession.setAttribute("isGameOn", true);
        state.unSetPause();
    }

    private void makeView(){
        char[][] cells = state.stage.drawTetraminoOnCells();
        currentSession.setAttribute("player", player.getPlayerName());
        currentSession.setAttribute("score", player.getPlayerScore());
        currentSession.setAttribute("bestplayer", Dao.bestPlayer);
        currentSession.setAttribute("bestscore", Dao.bestScore);
        currentSession.setAttribute("stepdown", State.stepDownArray[0]);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                currentSession.setAttribute(new StringBuilder("cells").append(i).append("v").append(j).toString(),
                        new StringBuilder("/img/").append(cells[i][j]).append(".png").toString());
            }
        }
    }


}
