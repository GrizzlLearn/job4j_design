package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;

public class Emulator {
    private static final String PATH_TO_FILES = "/Users/dvl/projects/job4j_design/data/";

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        DirFileCache dirFileCache = new DirFileCache(PATH_TO_FILES);
        System.out.println(dirFileCache.load("Names.txt"));
        System.out.println(System.lineSeparator());
        System.out.println(dirFileCache.load("Address.txt"));
    }
}
