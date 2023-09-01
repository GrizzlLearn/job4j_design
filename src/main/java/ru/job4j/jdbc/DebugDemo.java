package ru.job4j.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DebugDemo {
    private Connection con;
    private String tableName = "cities";

    public DebugDemo() throws Exception {
        initConnection();
    }

    private void initConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/idea_db";
        String login = "dl";
        String password = "123456";
        con = DriverManager.getConnection(url, login, password);
    }

    public void createTable() {
        String sql = String.format("create table if not exists %s(%s, %s, %s);",
                this.tableName,
                "id serial primary key",
                "name text",
                "population int"
        );

        try (Statement statement = con.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public City insert(City city) {
        String sql = String.format("insert into %s(name, population) values (?, ?);",
                this.tableName
        );

        try (PreparedStatement statement = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, city.getName());
            statement.setInt(2, city.getPopulation());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        String sql = String.format("select * from %s;",
                this.tableName
        );
        try (PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cities.add(new City(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("population")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static void main(String[] args) throws Exception {
        City first = new City("CityOne", 100);
        City second = new City("CityTwo", 200);
        DebugDemo debugDemo = new DebugDemo();
        debugDemo.createTable();
        debugDemo.insert(first);
        debugDemo.insert(second);
        for (City city : debugDemo.findAll()) {
            System.out.println("city = " + city);
        }
    }
}
