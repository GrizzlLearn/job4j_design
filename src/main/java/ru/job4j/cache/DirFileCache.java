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
     * Вызывает {@link #loadFile(String)}, возвращает содержимое файла из кэша
     *
     * @param key названия файла, который содержится в папке {@link #cachingDir}
     * @return Содержимое файла, указанного как параметр
     * @see AbstractCache
     */

    @Override
    public String load(String key) {
        loadFile(key);
        return get(key);
    }

    /**
     * Принимает название файла, добавляет в карту как ключ, значением - содержимое файла.
     *
     * @param fileName Название файла
     */

    private void loadFile(String fileName) {
        Path filePath = Path.of(String.format("%s%s", cachingDir, fileName));
        try {
            String fileContent = readFileContent(filePath);
            SoftReference<String> softReference = new SoftReference<>(fileContent);
            put(fileName, softReference.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
