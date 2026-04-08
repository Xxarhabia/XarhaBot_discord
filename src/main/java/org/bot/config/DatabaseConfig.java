package org.bot.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    PropertiesConfig props = new PropertiesConfig();

    public Connection connectDatabase() throws SQLException {
        return DriverManager.getConnection(
                props.getUrlDatabase(),
                props.getUserDatabase(),
                props.getPasswordDatabase()
        );
    }
}
