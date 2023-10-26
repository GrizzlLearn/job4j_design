package ru.job4j.cache.menu;

import ru.job4j.cache.AbstractCache;
import ru.job4j.cache.DirFileCache;
import java.util.Scanner;

public class Emulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input dir for caching ");
        String dir = scanner.nextLine();
        AbstractCache<String, String> dirFileCache = new DirFileCache(dir);
        System.out.println("Please input filename for caching or Exit");
        String file = scanner.nextLine();
        while (!"Exit".equals(file)) {
            System.out.println(dirFileCache.get(file));
            file = scanner.nextLine();
        }
    }
}
