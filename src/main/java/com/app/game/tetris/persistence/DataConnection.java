package com.app.game.tetris.persistence;

import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class DataConnection {
    private Connection con;

    public Connection getConnection() throws SQLException {
        String fileLocation = System.getProperty("user.dir") + "\\db_Credentials.properties";
        Properties propertiesDB = new Properties();
        try {
            propertiesDB.load(new FileReader(fileLocation));
            if (con == null)
                con = DriverManager.getConnection(propertiesDB.getProperty("url"), propertiesDB.getProperty("user"), propertiesDB.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }
}
