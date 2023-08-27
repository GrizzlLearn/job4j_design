package ru.job4j.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TableEditor implements AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(TableEditor.class.getName());

    private Connection connection;

    private final Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection(){
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД");
            LOG.error("An SQLException occurred:", e);
        }
    }

    private String execute(String sql, String tableName) {
        String result = "";
        int executeresult = 0;
        try (Statement statement = connection.createStatement()) {
            executeresult = statement.executeUpdate(sql);
            if (executeresult == 0 && sql.startsWith("CREATE TABLE")) {
                result = "Table is created!";
            }
            if (executeresult == 0 && sql.startsWith("DROP TABLE")) {
                result = "Table is dropped!";
            }
            if (executeresult == 0 && sql.contains("ADD COLUMN")) {
                result = "Column is added!";
            }
            if (executeresult == 0 && sql.contains("DROP COLUMN")) {
                result = "Column is dropped!";
            }
            if (executeresult == 0 && sql.contains("RENAME COLUMN")) {
                result = "Column is renamed!";
            }
            if (!sql.contains("DROP TABLE")) {
                System.out.println(getTableScheme(connection, tableName));
            }
        } catch (Exception e) {
            LOG.error("An Exception occurred:", e);
        }
        return result;
    }

    /*
    создает пустую таблицу без столбцов с указанным именем;
     */
    public void createTable(String tableName) {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s(%s);",
                tableName,
                "id serial primary key"
        );
        System.out.println(execute(sql, tableName));
    }

    /*
    удаляет таблицу по указанному имени;
     */
    public void dropTable(String tableName) {
        String sql = String.format(
                "DROP TABLE IF EXISTS %s;",
                tableName
        );
        System.out.println(execute(sql, tableName));
    }

    /*
    добавляет столбец в таблицу;
     */
    public void addColumn(String tableName, String columnName, String type) {
        String sql = String.format(
                "ALTER TABLE %s ADD COLUMN %S %s;",
                tableName,
                columnName,
                type
        );
        System.out.println(execute(sql, tableName));
    }

    /*
    удаляет столбец из таблицы;
     */
    public void dropColumn(String tableName, String columnName) {
        String sql = String.format(
                "ALTER TABLE %s DROP COLUMN %S;",
                tableName,
                columnName
        );
        System.out.println(execute(sql, tableName));
    }

    /*
    переименовывает столбец.
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) {
        String sql = String.format(
                "ALTER TABLE %s RENAME COLUMN %S TO %s;",
                tableName,
                columnName,
                newColumnName
        );
        System.out.println(execute(sql, tableName));
    }

    public String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM %s LIMIT 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        try (InputStream in = TableEditor.class.getClassLoader().getResourceAsStream("app.properties")) {
            config.load(in);
        } catch (IOException e) {
            LOG.error("An IOException occurred:", e);
        }

        try (TableEditor tableEditor = new TableEditor(config)) {
            tableEditor.createTable("TableEditor_tableName");
            tableEditor.dropTable("TableEditor_tableName");
            tableEditor.createTable("TableEditor_tableName");
            tableEditor.addColumn("TableEditor_tableName", "Test_columnName", "text");
            tableEditor.addColumn("TableEditor_tableName", "Test_columnName1", "text");
            tableEditor.addColumn("TableEditor_tableName", "Test_columnName2", "text");
            tableEditor.dropColumn("TableEditor_tableName", "Test_columnName");
            tableEditor.renameColumn("TableEditor_tableName", "Test_columnName2", "Brand_new_name");
        } catch (SQLException e) {
            System.out.println("Возникла какая-то проблема в процессе работы с БД");
            LOG.error("An SQLException occurred:", e);
        }
    }
}
