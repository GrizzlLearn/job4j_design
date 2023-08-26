package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() {
        connection = null;
    }

    //TODO
    /*
    создает пустую таблицу без столбцов с указанным именем;
     */
    public void createTable(String tableName) {
    }

    /*
    удаляет таблицу по указанному имени;
     */
    public void dropTable(String tableName) {
    }

    /*
    добавляет столбец в таблицу;
     */
    public void addColumn(String tableName, String columnName, String type) {
    }

    /*
    удаляет столбец из таблицы;
     */
    public void dropColumn(String tableName, String columnName) {
    }

    /*
    переименовывает столбец.
     */
    public void renameColumn(String tableName, String columnName, String newColumnName) {
    }


    public String getTableScheme(String tableName) throws Exception {
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

    //TODO
    public static void main(String[] args) {

    }
}
