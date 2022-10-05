package ru.job4j.collection;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    private int point = 0;

    private int expectedModCount;

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
        return result;
    }

    @Override
    public T remove(int index) {
        if (checkIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
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
        if (checkIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    private boolean checkIndex(int index) {
        return index < 0 || index >= size;
    }

    private T[] increase() {
        T[] newContainer = (T[]) new Object[container.length * 2];
        System.arraycopy(container, 0, newContainer, 0, size);
        return newContainer;
    }

    @Override
    public Iterator<T> iterator() {
        expectedModCount = modCount;
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return point != size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return container[point++];
            }
        };
    }
}
