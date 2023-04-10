package ru.job4j.io;

import java.io.*;

public class Analysis {
    public void unavailable(String source, String target) {
        boolean server = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new PrintWriter(target))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (server && !checkServAvailable(line)) {
                    server = false;
                    writer.write(line.split(" ", 2)[1]);
                    writer.write(";");
                }
                if (!server && checkServAvailable(line)) {
                    server = true;
                    writer.write(line.split(" ", 2)[1]);
                    writer.write(System.lineSeparator());
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

