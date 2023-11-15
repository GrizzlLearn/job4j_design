package ru.job4j.kiss.fool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FoolTest {

    @Test
    void test1() {
        String text = "One";
        String answer = "Two";
        int startAt = 10;
        assertThat(Fool.checkAnswer(text, answer, startAt)).isEqualTo(0);
    }

    @Test
    void test2() {
        String text = "One";
        String answer = "One";
        int startAt = 10;
        assertThat(Fool.checkAnswer(text, answer, startAt)).isEqualTo(10);
    }

    @Test
    void test3() {
        String text = "One";
        String answer = null;
        int startAt = 10;
        assertThat(Fool.checkAnswer(text, answer, startAt)).isEqualTo(0);
    }

    @Test
    void test4() {
        String answer = "One";
        String text = String.valueOf(answer);
        int startAt = 10;
        assertThat(Fool.checkAnswer(text, answer, startAt)).isEqualTo(10);
    }
}
