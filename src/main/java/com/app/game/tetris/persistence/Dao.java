package com.app.game.tetris.persistence;

import com.app.game.tetris.model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class Dao {
    public String bestPlayer = "To be shown";
    public int bestScore;

    @Autowired
    HibernateSessionFactoryUtil hibernateSessionFactoryUtil;

    public void recordScore(Player player) {
        Session session = hibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(player);
        tx1.commit();
        session.close();
    }

    public void retrieveScores() {
        Session session = hibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Player> playerList = (List<Player>) session.createQuery("From Player").list();
        session.close();
        Collections.sort(playerList, Comparator.comparingInt(Player::getPlayerScore));
        bestPlayer = playerList.get(playerList.size() - 1).getPlayerName();
        bestScore = playerList.get(playerList.size() - 1).getPlayerScore();
    }
}
