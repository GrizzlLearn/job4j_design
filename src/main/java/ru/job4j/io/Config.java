package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        StringJoiner out = new StringJoiner(System.lineSeparator());

        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
            String[] tmp = out.toString().split(System.lineSeparator());

            for (String item : tmp) {
                addToMap(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToMap(String item) {
        String regex = "=";
        char sample = '=';
        String[] tmp = new String[2];

        if (item.length() > 0 && item.charAt(0) != '#') {
            if (countSamplesInString(item, sample) >= 1) {
                tmp[0] = item.split(regex, 2)[0];
                tmp[1] = item.split(regex, 2)[1];
                values.putIfAbsent(tmp[0], tmp[1]);
            } else {
                checkForBrokenConfigString(item, regex, sample);
            }
        }
    }

    private void checkForBrokenConfigString(String item, String regex, char sample) {
        if (item.charAt(0) == sample
                || item.charAt(item.length() - 1) == sample
                || !item.contains(regex)) {
            throw new IllegalArgumentException("Config file is broken!");
        }
    }

    private int countSamplesInString(String item, char sample) {
        int result = 0;
        char[] tmp = item.toCharArray();

        for (char c : tmp) {
            if (c == sample) {
                result++;
            }
        }

        return result;
    }

    public String value(String key) {
        return values.getOrDefault(key, "No such key");
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
