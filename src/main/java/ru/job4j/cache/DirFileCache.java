package ru.job4j.cache;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    /**
     * Принимает название файла, добавляет в карту как ключ, значением содержимое файла.
     *
     * @param key названия файла, который содержится в папке {@link #cachingDir}
     * @return Содержимое файла, указанного как параметр
     * @see AbstractCache
     */

    @Override
    public String load(String key) {
        Path filePath = Path.of(String.format("%s%s", cachingDir, key));
        try {
            String fileContent = readFileContent(filePath);
            SoftReference<String> softReference = new SoftReference<>(fileContent);
            put(key, softReference.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return get(key);
    }

    /**
     * Принимает полный путь к файлу
     *
     * @param filePath пусть к файлу
     * @return Строчное значение содержимого файла
     * @throws IOException если произошла ошибка чтения файла
     */

    private String readFileContent(Path filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
