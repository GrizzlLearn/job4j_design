package ru.job4j.kiss.fool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FoolTest {

    @Test
    void test1() {
        String expected = "FizzBuzz";
        int startAt = 15;
        assertThat(Fool.getExpectedAnswer(startAt)).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = "Fizz";
        int startAt = 3;
        assertThat(Fool.getExpectedAnswer(startAt)).isEqualTo(expected);
    }

    @Test
    void test3() {
        String expected = "Buzz";
        int startAt = 5;
        assertThat(Fool.getExpectedAnswer(startAt)).isEqualTo(expected);
    }

    @Test
    void test4() {
        String expected = "7";
        int startAt = 7;
        assertThat(Fool.getExpectedAnswer(startAt)).isEqualTo(expected);
    }
}
