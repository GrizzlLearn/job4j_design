package ru.job4j.map;

import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Objects;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        MapEntry<K, V> tmp = new MapEntry<>(key, value);
        int index = indexFor(hash(key.hashCode()));
        if (table[index].value != null) {
            result = false;
        } else {
            table[index] = tmp;
            count++;
            modCount++;
            result = true;
        }
        return result;
    }

    private int hash(int hashCode) {
        return hashCode % capacity;
    }

    private int indexFor(int hash) {
        return hash & (capacity * 2);
    }

    private void expand() {
    }

    @Override
    public V get(K key) {
        V result = null;
        for (MapEntry<K, V> entry : table) {
            if (entry.key.equals(key)) {
                result = entry.value;
            }
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            MapEntry<?, ?> entry = (MapEntry<?, ?>) o;

            if (!Objects.equals(key, entry.key)) {
                return false;
            }
            return Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }
}
