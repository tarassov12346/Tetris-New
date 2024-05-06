package com.app.game.tetris.persistence;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataConnection {
    private Connection con;

    public Connection getConnection() throws SQLException {
        if (con == null)
            con = DriverManager.getConnection(System.getenv("url"), System.getenv("user"), System.getenv("password"));
        return con;
    }
}
