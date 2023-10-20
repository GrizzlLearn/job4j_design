package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    /**
     * Метод добавляет в HashMap Элемент key - new SoftReference<V>(value)
     *
     * @param key Название файла
     * @param value Содержимое файла
     */

    public final void put(K key, V value) {
        cache.putIfAbsent(key, new SoftReference<V>(value));
    }

    /**
     * Метод принимает название файла, добавляет запись в карту 'название файла - содержимое файла', возвращает содержимое файла.
     *
     * @param key Название файла
     * @return значение SoftReference<V> содержимое файла
     * @see SoftReference
     * @see Optional
     * @see DirFileCache#load(String)
     */

    public final V get(K key) {
        V result = null;
        Optional<V> fileContentExits = Optional.ofNullable(load(key));

        if (fileContentExits.isPresent()) {
            put(key, fileContentExits.get());
            result = cache.getOrDefault(key, new SoftReference<>(fileContentExits.get())).get();
        }

        return result;
    }

    protected abstract V load(K key);

}
