package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFilter {
    public List<String> filter(String file) {
        List<String> result = new ArrayList<>();
        String template = "404";
        String regexp = " ";

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.lines().forEach((line) -> {
                String[] tmp = line.split(regexp);
                if (tmp[tmp.length - 2].equals(template)) {
                    result.add(line);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogFilter logFilter = new LogFilter();
        List<String> log = logFilter.filter("data/log.txt");
        save(log, "data/logFilter.txt");
        log.forEach(System.out::println);
    }
}
