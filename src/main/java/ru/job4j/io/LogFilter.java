package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    public List<String> filter(String file) {
        List<String> result = new ArrayList<>();
        String template = " 404 ";

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.lines().forEach((line) -> {
                if (line.contains(template)) {
                    result.add(line);
                    result.add(System.lineSeparator());
                }
            });
            result.add(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter();
        List<String> log = logFilter.filter("data/log.txt");
        System.out.println(log);
    }
}
