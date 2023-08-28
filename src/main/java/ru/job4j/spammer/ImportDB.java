package ru.job4j.spammer;

import java.io.*;
import java.sql.*;
import java.util.*;

public class ImportDB {

    static private Properties cfg;
    private String dump;

    public ImportDB(Properties cfg, String dump) {
        ImportDB.cfg = cfg;
        this.dump = dump;
    }

    /**
     * Подготавливает базу данных с указанным именем и таблицей для использования.
     *
     * @param dbName Имя базы данных, которая должна быть создана.
     * @param tableName Имя таблицы в базе данных, которая должна быть создана.
     * @throws ClassNotFoundException Если драйвер базы данных не может быть найден.
     * @throws SQLException Если происходит ошибка при создании базы данных или таблицы.
     */
    static private void prepareDB(String dbName, String tableName) throws ClassNotFoundException, SQLException {
        String createDB = String.format("CREATE DATABASE IF NOT EXISTS %s;",
                dbName
        );
        String createTable = String.format("CREATE TABLE IF NOT EXISTS %s(%s, %s, %s);",
                tableName,
                "id serial primary key",
                "username VARCHAR(255)",
                "email VARCHAR(255)"
                );
        List<String> sqlList = new ArrayList<>() {{
            add(createDB);
            add(createTable);
        }};
        Class.forName(cfg.getProperty("jdbc.driver"));
        try (Connection connection = getConnection(cfg)) {
            for (String st : sqlList) {
                try (Statement s = connection.createStatement()) {
                   s.execute(st);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Устанавливает соединение с базой данных на основе предоставленных настроек.
     *
     * @param cfg Настройки для установки соединения с базой данных.
     * @return Объект Connection, представляющий соединение с базой данных.
     * @throws ClassNotFoundException Если драйвер базы данных не может быть найден.
     * @throws SQLException Если происходит ошибка при установке соединения.
     */
    private static Connection getConnection(Properties cfg) throws ClassNotFoundException, SQLException {
        Class.forName(cfg.getProperty("jdbc.driver"));
        return DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        );
    }

    //TODO
    // Сделать проверку, что в массиве точно 2 элемента и они не пустые
    // Если не проходит – выбрасывайте IllegalArgumentException

    /**
     * Формирует список пользователей.
     * @return Список пользователей.
     * @throws IOException если ошибка чтения конфига.
     * @throws IllegalArgumentException если ошибка добавления пользователя в список.
     */
    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(dump))) {
            rd.lines().forEach( line -> {
                        String[] tmp = line.split(";");
                        if (tmp.length > 1) {
                            users.add(new User(tmp[0], tmp[1]));
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
            );
        }
        return users;
    }

    /**
     * Сохраняет имеющихся пользователь в указанной таблице.
     * @param users список пользователей, сформированный see #load() .
     * @param tableName название таблицы в которую необходимо сохранить пользователей.
     * @throws ClassNotFoundException Если драйвер базы данных не может быть найден.
     * @throws SQLException Если происходит ошибка при сохранении данных в таблицу.
     */
    public void save(List<User> users, String tableName) throws ClassNotFoundException, SQLException {
        String sql = String.format("INSERT INTO %s value(?, ?)",
                tableName
        );
        try (Connection connection = getConnection(cfg)) {
            for (User user : users) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, user.name);
                    ps.setString(2, user.email);
                    ps.execute();
                }
            }
        }
    }

    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }


    public static void main(String[] args) throws Exception {
        Properties cfg = new Properties();
        String dbName = "spammer";
        String tableName = "spammer_table";
        try (InputStream in = ImportDB.class.getClassLoader().getResourceAsStream("app.properties")) {
            cfg.load(in);
        }
        ImportDB db = new ImportDB(cfg, "./src/main/java/ru/job4j/spammer/dump.txt");
        db.save(db.load(), tableName);
    }
}


