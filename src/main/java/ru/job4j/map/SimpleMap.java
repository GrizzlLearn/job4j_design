package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
        if (table[index] == null) {
            table[index] = tmp;
            count++;
            modCount++;
            result = true;
        }

        return result;
    }

    private int hash(int hashCode) {
        return (hashCode) ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] tmpTable = new MapEntry[capacity];

        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                tmpTable[keyIndex(entry.key)] = entry;
            }
            modCount++;
        }

        table = tmpTable;
    }

    private int keyIndex(K key) {
        return key == null ? 0 : indexFor(hash(Objects.hashCode(key)));
    }

    private boolean checkEquals(K key, int index) {
        boolean result = false;

        if (!Objects.equals(table[index], null)) {
            if (Objects.hashCode(key) == Objects.hashCode(table[index].key)) {
                result = Objects.equals(table[index].key, key);
            }
        }

        return result;
    }

    @Override
    public V get(K key) {
        int index = keyIndex(key);

        return checkEquals(key, index) ? table[index].value : null;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = keyIndex(key);

        if (checkEquals(key, index)) {
            table[index] = null;
            modCount++;
            count--;
            result = true;
        }

        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int index;
            final int iModCount = modCount;

            @Override
            public boolean hasNext() {
                if (iModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                while (index < capacity && table[index] == null) {
                    index++;
                }

                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
