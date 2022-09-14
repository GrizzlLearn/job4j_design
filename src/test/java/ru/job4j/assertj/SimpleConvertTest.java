package ru.job4j.assertj;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.data.Index;

class SimpleConvertTest {

    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).isNotEmpty()
                .hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> array = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(array).isNotEmpty()
                .hasSize(5)
                .contains("five")
                .contains("three", Index.atIndex(2))
                .containsAnyOf("zero", "second", "three")
                .doesNotContain("second", Index.atIndex(4))
                .startsWith("first")
                .containsExactly("first", "second", "three", "four", "five")
                .filteredOn(e -> e.length() > 4)
                .first().isEqualTo("first");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> array = simpleConvert.toSet("first", "second", "three", "four", "five");
        assertThat(array).isNotEmpty()
                .hasSize(5)
                .filteredOn(e -> e.length() > 4)
                .isNotEmpty()
                .hasSize(3)
                .doesNotContain("qwerty")
                .containsExactlyInAnyOrder("second", "first", "three");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> array = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(array).isNotEmpty()
                .hasSize(5)
                .containsKey("three")
                .containsValue(0)
                .doesNotContainKey("qwerty")
                .doesNotContainValue(18)
                .containsEntry("four", 3);
    }
}
