package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleStack<T> extends ForwardLinked<T> {
    private ForwardLinked<T> linked = new ForwardLinked<>();

    public T pop() {
        return linked.deleteFirst();
    }

    public void push(T value) {
        if (!linked.iterator().hasNext()) {
            linked.add(value);
        } else {
            linked.addFirst(value);
        }
    }
}
