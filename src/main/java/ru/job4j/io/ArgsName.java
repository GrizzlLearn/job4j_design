package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        String result = "";

        if (validateKey(key)) {
            result = values.get(key);
        }

        return result;
    }

    private boolean validateKey(String key) {

        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(
                    String.format("This key: '%s' is missing", key)
            );
        }

        if (values.get(key).length() == 0) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain a value", key)
            );
        }

        return true;
    }

    private boolean validateArg(String arg) {

        if (!arg.startsWith("-")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not start with a '-' character", arg)
            );
        }

        if (arg.startsWith("-=")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain a key", arg)
            );
        }

        if (!arg.contains("=")) {
            throw new IllegalArgumentException(
                    String.format("Error: This argument '%s' does not contain an equal sign", arg)
            );
        }

        if (arg.startsWith("-") && arg.endsWith("=")) {
            char[] chars = arg.toCharArray();
            int count = 0;

            for (char c : chars) {
                if (c == '=') {
                    count++;
                }
            }

            if (count == 1) {
                throw new IllegalArgumentException(
                        String.format("Error: This argument '%s' does not contain a value", arg)
                );
            }
        }

        return true;
    }

    private void parse(String[] args) {

        if (args.length < 1) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }

        String regForSplitKey = "-*=";

        for (String arg : args) {
            if (validateArg(arg)) {
                String[] keys = arg.split(regForSplitKey, 2);
                this.values.putIfAbsent(keys[0].substring(1), keys[1]);
            }
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
