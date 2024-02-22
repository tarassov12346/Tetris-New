package com.tetris1.game.tetris.model.persistence;

import com.tetris1.game.tetris.model.Player;
import com.tetris1.game.tetris.model.serviceImpl.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Dao {
    List<Player> playerList=new ArrayList<>();
    static public String bestPlayer = "To be shown";
    static public int bestScore;

    @Autowired
    DataConnection dataCon;

    public void recordScore(Player player) {
        String recordScoreQuery = "INSERT INTO player2(name, score) VALUES(?, ?)";
        try (PreparedStatement pst = dataCon.getConnection().prepareStatement(recordScoreQuery)) {
            pst.setString(1, player.getPlayerName());
            pst.setInt(2, player.getPlayerScore());
            pst.executeUpdate();
            System.out.println(player.getPlayerName()+player.getPlayerScore());
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(State.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void retrieveScores() {
        String retrieveScoresQuery = "SELECT * FROM player2";
        try (PreparedStatement pst = dataCon.getConnection().prepareStatement(retrieveScoresQuery);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                playerList.add(new Player(rs.getString(1),rs.getInt(2)));
                Collections.sort(playerList,Comparator.comparingInt(Player::getPlayerScore));
                bestPlayer=playerList.get(playerList.size()-1).getPlayerName();
                bestScore=playerList.get(playerList.size()-1).getPlayerScore();
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(State.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        System.out.println("Best player " + bestPlayer + " with score " + bestScore);
    }

}
