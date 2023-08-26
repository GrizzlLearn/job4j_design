package ru.job4j.jdbc;

import ru.job4j.io.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDemo {
    private static final String path = "./src/main/resources/app.properties";
    private static final Config config = new Config(path);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        config.load();
        String url = config.value("url");
        String login = config.value("login");
        String password = config.value("password");

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
