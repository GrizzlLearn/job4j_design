package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    private static final String PATH = "./src/main/resources/app.properties";
    private static final Config CONFIG = new Config(PATH);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        CONFIG.load();
        String url = CONFIG.value("url");
        String login = CONFIG.value("login");
        String password = CONFIG.value("password");

        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
        } catch (SQLException e) {
            System.out.println("Ошибка подключение к БД");
            e.printStackTrace();
        }
    }
}
