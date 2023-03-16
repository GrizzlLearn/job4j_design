package ru.job4j.io;

public class MathTab {
    public static String printMathTab() {
        StringBuffer result = new StringBuffer();

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                result.append(i * j);
                if (j < 10) {
                    result.append("\t");
                }
            }
            result.append(System.lineSeparator());
        }

        return result.toString();
    }
}
