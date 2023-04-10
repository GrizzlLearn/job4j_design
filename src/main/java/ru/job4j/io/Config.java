package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            values.putAll(read.lines()
                    .filter(s -> !s.isBlank() || !s.startsWith("#"))
                            .filter(this::validateBlank)
                            .filter(this::validateStart)
                    .filter(this::validate)
                    .map(s -> s.split("=", 2))
                    .collect(Collectors.toMap(
                            s -> s[0],
                            s -> s[1],
                            (e, n) -> e)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateBlank(String str) {
        return !str.isBlank();
    }

    private boolean validateStart(String str) {
        return !str.startsWith("#");
    }

    private boolean validate(String str) {
        if (!str.contains("=")) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain the symbol \"=\"", str));
        }
        if (str.startsWith("=")) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain a key", str));
        }
        if (str.indexOf("=") == str.length() - 1) {
            throw new IllegalArgumentException(
                    String.format("this name: %s does not contain a value", str));
        }
        return true;
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
