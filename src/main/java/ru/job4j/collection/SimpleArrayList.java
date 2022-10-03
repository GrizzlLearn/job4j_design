package ru.job4j.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        if (container.length == size) {
            container = increase();
        }
        container[size] = value;
        size++;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        T result = container[index];
        if (container.length == size) {
            container = increase();
        }
        System.arraycopy(container, index, container, index + 1, size - index);
        container[index] = newValue;
        modCount++;
        return result;
    }

    @Override
    public T remove(int index) {
        T result = container[index];
        int newSize = size - 1;
        if (newSize > index) {
            System.arraycopy(container, index + 1, container, index, newSize - index);
        }
        container[newSize] = null;
        size--;
        modCount++;
        return result;
    }

    @Override
    public T get(int index) {
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    private T[] increase() {
        T[] newContainer = (T[]) new Object[container.length * 2];
        System.arraycopy(container, 0, newContainer, 0, size);
        return newContainer;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return container[0];
            }
        };
    }
}
