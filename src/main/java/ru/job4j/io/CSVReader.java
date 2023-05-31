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
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            for (String s : scanner.nextLine().split(System.lineSeparator())) {
                finalList.add(new ArrayList<>(List.of(s.split(delimiter))));
                if (s.contains(filter.get(0))) {
                    tmp.addAll(List.of(s.split(delimiter)));
                }
            }
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
        /* здесь добавьте валидацию принятых параметров*/
        ArgsName argsName = ArgsName.of(args);
        if (validateArgs(argsName)) {
            handle(argsName);
        }
    }
}
