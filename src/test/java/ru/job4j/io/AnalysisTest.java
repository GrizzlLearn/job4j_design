package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnalysisTest {
    @Test
    void unavailableTest(@TempDir Path tempDir) throws IOException {

        File source = tempDir.resolve("source.txt").toFile();
        try (PrintWriter writer = new PrintWriter(source)) {
            writer.println("200 10:56:01");
            writer.println("500 10:57:01");
            writer.println("400 10:58:01");
            writer.println("300 10:59:01");
            writer.println("500 11:01:02");
            writer.println("200 11:02:02");
            writer.println(System.lineSeparator());
        }
        File target = tempDir.resolve("target.txt").toFile();

        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            reader.lines().forEach(result::append);
        }
        assertThat("10:57:01;10:59:01;11:01:02;11:02:02;").isEqualTo(result.toString());
    }

    @Test
    void unavailableTest1(@TempDir Path tempDir) throws IOException {

        File source = tempDir.resolve("source.txt").toFile();
        try (PrintWriter writer = new PrintWriter(source)) {
            writer.println("200 10:56:01");
            writer.println("500 10:57:01");
            writer.println("400 10:58:01");
            writer.println("500 10:59:01");
            writer.println("400 11:01:02");
            writer.println("300 11:02:02");
            writer.println(System.lineSeparator());
        }
        File target = tempDir.resolve("target.txt").toFile();

        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            reader.lines().forEach(result::append);
        }
        assertThat("10:57:01;11:02:02;").isEqualTo(result.toString());
    }
}
