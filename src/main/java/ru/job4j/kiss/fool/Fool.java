package ru.job4j.kiss.fool;

import java.util.Scanner;

public class Fool {

    private static final String FIZZ_BUZZ = "FizzBuzz";
    private static final String FIZZ = "Fizz";
    private static final String BUZZ = "Buzz";
    private static final int THREE = 3;
    private static final int FIVE = 5;

    public static void main(String[] args) {
        System.out.println("Игра FizzBuzz.");
        var startAt = 1;
        var io = new Scanner(System.in);

        while (startAt < 100) {
            if (startAt % THREE == 0 || startAt % FIVE == 0) {
                System.out.println(getExpectedAnswer(startAt));
            } else {
                System.out.println(startAt);
            }

            startAt++;
            String answer = io.nextLine();

            if (!answer.equals(getExpectedAnswer(startAt))) {
                System.out.println("Ошибка. Начинай снова.");
                startAt = 0;
            }

            startAt++;
        }
    }

    public static String getExpectedAnswer(int number) {
        String result;
        if (number % THREE == 0 && number % FIVE == 0) {
            result = FIZZ_BUZZ;
        } else if (number % THREE == 0) {
            result = FIZZ;
        } else if (number % FIVE == 0) {
            result = BUZZ;
        } else {
            result = String.valueOf(number);
        }

        return result;
    }
}

