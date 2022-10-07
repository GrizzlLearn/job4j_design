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
            //container = increase();
            increase();
        }
        container[size++] = value;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T result = container[index];
        container[index] = newValue;
        return result;
    }

    @Override
    public T remove(int index) {
        T result = get(index);
        size--;
        if (size > index) {
            System.arraycopy(container, index + 1, container, index, size - index);
        }
        container[size] = null;
        modCount++;
        return result;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    private void increase() {
        T[] newContainer = (T[]) new Object[container.length != 0
                ? container.length * 2
                : 1];
        container = newContainer;
    }

    @Override
    public Iterator<T> iterator() {
        expectedModCount = modCount;
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return point != size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[point++];
            }
        };
    }
}
