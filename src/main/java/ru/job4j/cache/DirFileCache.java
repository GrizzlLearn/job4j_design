package ru.job4j.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    /**
     * Принимает название файла, возвращает содержимое файла из кэша.
     *
     * @param key названия файла, который содержится в папке {@link #cachingDir}
     * @return Содержимое файла, указанного как параметр
     * @see AbstractCache
     */

    @Override
    protected String load(String key) {
        String result = "";
        try {
            result = Files.readString(Path.of(cachingDir, key));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
