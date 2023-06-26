package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;
    private List<String> log = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        List<String> botAnswers = readPhrases();
        Random random = new Random();
        String humanInput = "";
        String greetings = "Hello! Let's try to chat!";
        String stopAnswer = "You stopped me(";
        String contAnswer = "We can communicate again!";
        System.out.println(greetings);
        log.add(greetings);

        while (!OUT.equals(humanInput)) {
            String botAnswer = botAnswers.get(random.nextInt(botAnswers.size()));
            humanInput = scanner.nextLine();

            if (STOP.equals(humanInput)) {
                log.add(String.format("Ввод пользователя: %s", humanInput));
                System.out.println(stopAnswer);
                log.add(String.format("Ответ бота: %s", stopAnswer));

                while (!CONTINUE.equals(humanInput)) {
                    humanInput = scanner.nextLine();
                    log.add(String.format("Ввод пользователя: %s", humanInput));

                    if (CONTINUE.equals(humanInput)) {
                        System.out.println(contAnswer);
                        log.add(String.format("Ответ бота: %s", contAnswer));
                    }
                }
            }

            if (!OUT.equals(humanInput) && !CONTINUE.equals(humanInput)) {
                log.add(String.format("Ввод пользователя: %s", humanInput));
                System.out.println(botAnswer);
                log.add(String.format("Ответ бота: %s", botAnswer));
            }
        }

        log.add(String.format("Ввод пользователя: %s", humanInput));
        saveLog(log);
    }

    private List<String> readPhrases() {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.botAnswers))) {
            br.lines().forEach(result::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, Charset.defaultCharset(), true))) {
            log.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("./data/ConsoleChat_log.txt", "./data/ConsoleChat_botAnswers.txt");
        cc.run();
    }
}
