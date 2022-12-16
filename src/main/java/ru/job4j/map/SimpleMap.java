package ru.job4j.map;

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
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        MapEntry<K, V> tmp = new MapEntry<>(key, value);
        int index = keyIndex(key);
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
        MapEntry<K, V>[] tmpTable = new MapEntry[capacity * 2];
        capacity *= 2;
        count = 0;
        modCount = 0;
        for (MapEntry<K, V> entry : table) {
            tmpTable[keyIndex(entry.key)] = entry;
            count++;
            modCount++;
        }
        table = tmpTable;
    }

    private int keyIndex(K key) {
        return indexFor(hash(key.hashCode()));
    }

    private boolean checkEquals(K key) {
        boolean result = false;
        int index = keyIndex(key);
        if (table[index].key.hashCode() == key.hashCode() && table[index].key.equals(key)) {
            result = true;
        }
        return result;
    }

    @Override
    public V get(K key) {
        V result = null;
        int index = keyIndex(key);
        if (checkEquals(key)) {
            result = table[index].value;
        }
        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        if (checkEquals(key)) {
            table[keyIndex(key)] = null;
            modCount++;
            count--;
        }
        return result;
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