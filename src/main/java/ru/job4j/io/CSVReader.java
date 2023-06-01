package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
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

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(argsName.get("out")))) {
            out.write(result.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validateArgs(ArgsName args) {
        File file = new File(args.get("path"));

        if (!file.exists()) {
            throw new IllegalArgumentException("You must set EXIST FILE.");
        }

        if (!file.isFile()) {
            throw new IllegalArgumentException("You must set FILE, not DIRECTORY.");
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = ArgsName.of(args);
        if (validateArgs(argsName)) {
            handle(argsName);
        }
    }
}
