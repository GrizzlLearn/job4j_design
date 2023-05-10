package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        if (validate(args)) {
            Path start = Paths.get(args[0]);
            search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
        }
    }

    private static boolean validate(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException(String.format("Wrong number of parameters, must be %s", 2));
        }

        File file = new File(args[0]);

        if (!file.exists()) {
            throw new IllegalArgumentException("You must set EXIST ROOT DIRECTORY.");
        }

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("You must set ROOT DIRECTORY, not FILE.");
        }

        if (!args[1].startsWith(".")) {
            throw new IllegalArgumentException("You must use \".filetype\" pattern.");
        }

        if (!args[1].matches("(.[a-z]*)")) {
            throw new IllegalArgumentException("You must set filetypes");
        }

        return true;
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static class SearchFiles implements FileVisitor<Path> {
        private Predicate<Path> condition;
        private List<Path> found = new ArrayList<>();

        SearchFiles(Predicate<Path> condition) {
            this.condition = condition;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (condition.test(file)) {
                found.add(file);
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
        
        public List<Path> getPaths() {
            return this.found;
        }
    }
}


