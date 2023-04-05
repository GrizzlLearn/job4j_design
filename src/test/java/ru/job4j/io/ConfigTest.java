package ru.job4j.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ConfigTest {
    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairWithComment() {
        String path = "./data/pair_with_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name2")).isEqualTo("Qwerty");
    }

    @Test
    void whenKeyDontHavePair() {
        String path = "./data/broken_pairs.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Config file is broken!");
    }

    @Test
    void whenValueContainEquals1() {
        String path = "./data/pair_with_multiple_equals.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Qwerty=1");
    }

    @Test
    void whenValueContainEquals2() {
        String path = "./data/pair_with_multiple_equals.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name1")).isEqualTo("Qwerty1=");
    }
}
