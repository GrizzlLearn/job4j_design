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
     * @param key
     * @param value
     */
    public final void put(K key, V value) {
        cache.putIfAbsent(key, new SoftReference<V>(value));
    }

    /**
     * Метод позволяет получить значение из кэша по ключу key
     *
     * @param key
     * @return значение SoftReference<V>
     * @see SoftReference
     * @see Optional
     */

    public final V get(K key) {
        V result = null;
        Optional<SoftReference<V>> refExist = Optional.ofNullable(cache.get(key));
        if (refExist.isPresent()) {
            result = refExist.get().get();
        }
        return result;
    }

    protected abstract V load(K key);

}
