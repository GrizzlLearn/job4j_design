package ru.job4j.assertj;

import org.assertj.core.api.AssertProvider;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkNamesArrayIsEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("array is empty")
                .hasMessage("Names array is empty");

    }

    @Test
    void checkNotContainTheSymbolEqual() {
        NameLoad nameLoad = new NameLoad();
        String name = "test1";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not contain the symbol \"=\"")
                .hasMessage(String.format("this name: %s does not contain the symbol \"=\"", name));
    }

    @Test
    void checkNotContainAKey() {
        NameLoad nameLoad = new NameLoad();
        String name = "=1";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not contain a key")
                .hasMessage(String.format("this name: %s does not contain a key", name));
    }

    @Test
    void checkNotContainAValue() {
        NameLoad nameLoad = new NameLoad();
        String name = "Test=";
        assertThatThrownBy(() -> nameLoad.parse(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not contain a value")
                .hasMessage(String.format("this name: %s does not contain a value", name));
    }
}
