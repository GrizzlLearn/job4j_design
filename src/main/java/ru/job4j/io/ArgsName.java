package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        /* TODO add the necessary checks. */
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(
                    new StringBuilder().append("This key: ")
                            .append("\'")
                            .append(key)
                            .append("\'")
                            .append(" is missing")
                            .toString()
            );
        }

        return values.get(key);
    }

    private void parse(String[] args) {
        /* TODO parse args to values. */

        if (args.length < 1) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }

        String regForSplitValue = "^.*=";
        String regForSplitKey = "=.*";



        for (String arg : args) {
            String[] keys = arg.split(regForSplitKey);
            String[] values = arg.split(regForSplitValue);
            this.values.putIfAbsent(keys[0].substring(1), values[1]);
        }

    }

    public static ArgsName of(String[] args) {
        /* TODO add the necessary checks. */
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
