package ru.job4j.io;

import java.io.*;

public class Analysis {
    public void unavailable(String source, String target) {
        boolean isServerOn = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new PrintWriter(target))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (isServerOn == !checkServAvailable(line)) {
                    isServerOn = !isServerOn;
                    writer.append(line.split(" ", 2)[1])
                            .append(";")
                            .append(isServerOn ? System.lineSeparator() : "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkServAvailable(String str) {
        return !str.startsWith("4") && !str.startsWith("5");
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}

