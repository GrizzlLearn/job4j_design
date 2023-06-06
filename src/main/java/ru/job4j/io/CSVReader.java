package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
        validateArgs(argsName);
        String outType = argsName.get("out");
        File file = new File(argsName.get("path"));
        String filterDelimiter = ",";
        String delimiter = argsName.get("delimiter");
        List<String> filter = new ArrayList<>(Arrays.stream(argsName.get("filter").split(filterDelimiter)).toList());
        List<String> tmp = new ArrayList<>();
        List<List<String>> finalList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                for (String s : scanner.nextLine().split(System.lineSeparator())) {
                    finalList.add(new ArrayList<>(List.of(s.split(delimiter))));
                    if (s.contains(filter.get(0))) {
                        tmp.addAll(List.of(s.split(delimiter)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (List<String> stringList : finalList) {
            for (int i = 0; i < filter.size(); i++ ) {
                result.append(stringList.get(tmp.indexOf(filter.get(i))));

                if (i + 1 < filter.size()) {
                    result.append(delimiter);
                }
            }
            result.append(System.lineSeparator());
        }
        giveBackResult(result, outType);
    }

    private static void giveBackResult(StringBuilder result, String outType) {
        if ("stdout".equals(outType)) {
            System.out.println(result);
        } else {
            try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outType))) {
                out.write(result.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void validateArgs(ArgsName args) {
        File source = new File(args.get("path"));
        String delimiter = args.get("delimiter");
        if (!source.exists()) {
            throw new IllegalArgumentException("You need set EXIST FILE.");
        }
        if (!source.isFile()) {
            throw new IllegalArgumentException("You need set FILE, not DIRECTORY.");
        }
        if (!source.getName().endsWith("csv")) {
            throw new IllegalArgumentException("You need set CSV file.");
        }
        if (!";".equals(delimiter) && !",".equals(delimiter)) {
            throw new IllegalArgumentException("You need set ONE DELIMITER ',' or ';'");
        }
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}
