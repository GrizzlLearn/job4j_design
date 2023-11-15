package ru.job4j.kiss.fool;

import java.util.Optional;
import java.util.Scanner;

public class Fool {

    private static final String FIZZ_BUZZ = "FizzBuzz";
    private static final String FIZZ = "Fizz";
    private static final String BUZZ = "Buzz";

    public static void main(String[] args) {
        System.out.println("Игра FizzBuzz.");
        int startAt = 1;
        Scanner io = new Scanner(System.in);

        while (startAt < 100) {
            if (startAt % 3 == 0 && startAt % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (startAt % 3 == 0) {
                System.out.println("Fizz");
            } else if (startAt % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(startAt);
            }
            startAt++;
            String answer = io.nextLine();
            if (startAt % 3 == 0 && startAt % 5 == 0) {
                startAt = checkAnswer(FIZZ_BUZZ, answer, startAt);
            } else if (startAt % 3 == 0) {
                startAt = checkAnswer(FIZZ, answer, startAt);
            } else if (startAt % 5 == 0) {
                startAt = checkAnswer(BUZZ, answer, startAt);
            } else {
                startAt = checkAnswer(answer, answer, startAt);
            }
            startAt++;
        }
    }

    public static int checkAnswer(String text, String answer, int startAt) {
        int result = startAt;
        Optional<String> answerExists = Optional.ofNullable(answer);

        if (answerExists.isEmpty() || !text.equals(answerExists.get())) {
            System.out.println("Ошибка. Начинай снова.");
            result = 0;
        }
        return result;
    }
}

