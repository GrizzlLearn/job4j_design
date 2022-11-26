package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.function.Predicate;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIf() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Predicate<Integer> pred = i -> i % 2 != 0;
        ListUtils.removeIf(list, pred);
        assertThat(list).hasSize(5).containsSequence(2, 4, 6, 8, 10);
    }

    @Test
    void whenReplaceIf() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Predicate<Integer> pred = i -> i % 2 != 0;
        int value = 0;
        ListUtils.replaceIf(list, pred, value);
        assertThat(list).hasSize(10).containsSequence(0, 2, 0, 4, 0, 6, 0, 8, 0, 10);
    }

    @Test
    void whenRemoveAllStart5Elements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListUtils.removeAll(list, elements);
        assertThat(list).hasSize(5).containsSequence(6, 7, 8, 9, 10);
    }

    @Test
    void whenRemoveAllEnd5Elements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> elements = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10));
        ListUtils.removeAll(list, elements);
        assertThat(list).hasSize(5).containsSequence(1, 2, 3, 4, 5);
    }

    @Test
    void whenRemoveAllMiddle5Elements() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> elements = new ArrayList<>(Arrays.asList(4, 5, 6, 7, 8));
        ListUtils.removeAll(list, elements);
        assertThat(list).hasSize(5).containsSequence(1, 2, 3, 9, 10);
    }
}
