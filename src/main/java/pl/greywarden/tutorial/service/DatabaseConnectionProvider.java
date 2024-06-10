package pl.greywarden.tutorial.service;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseConnectionProvider {
    @Inject
    private DatabaseProperties databaseProperties;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseProperties.url, databaseProperties.username, databaseProperties.password);
    }

    @ConfigurationProperties("database")
    public static class DatabaseProperties {
        String url;
        String username;
        String password;
    }
}
