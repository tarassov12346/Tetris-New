package com.app.game.tetris.persistence;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataConnection {
    private Connection con;
    private String user = "postgres";
    private String password = "mine";
    private String url = "jdbc:postgresql:game";

    public Connection getConnection() throws SQLException {
        if (con==null) con=DriverManager.getConnection(url, user, password);
        return con;
    }
}
