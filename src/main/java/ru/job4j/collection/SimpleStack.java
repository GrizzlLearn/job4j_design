package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleStack<T> {

    private ForwardLinked<T> linked = new ForwardLinked<T>();

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;
    private int modCount;

    SimpleStack() {
        lastNode = new Node<T>(null, null);
        firstNode = new Node<T>(null, lastNode);
    }

    private static class Node<T> {

        private T currentElement;
        private Node<T> nextElement;

        private Node(T currentElement, Node<T> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
        }

        public void setNextElement(Node<T> nextElement) {
            this.nextElement = nextElement;
        }

        public void setCurrentElement(T currentElement) {
            this.currentElement = currentElement;
        }

        public Node<T> getNextElement() {
            return nextElement;
        }

        public T getCurrentElement() {
            return currentElement;
        }
    }

    private void addFirst(T value) {
        Node<T> tmpNode = new Node<>(firstNode.getCurrentElement(), firstNode.getNextElement());
        firstNode = new Node<T>(value, tmpNode);
        size++;
        modCount++;
    }

    private T deleteFirst() {
        Node<T> result = new Node<>(firstNode.getCurrentElement(), firstNode.getNextElement());
        firstNode.setCurrentElement(result.getNextElement().getCurrentElement());
        firstNode.setNextElement(result.getNextElement().getNextElement());
        size--;
        modCount++;
        return result.getCurrentElement();
    }

    public T pop() {
        if (firstNode.getCurrentElement() == null) {
            throw new NoSuchElementException();
        }
        return deleteFirst();
    }

    public void push(T value) {
        addFirst(value);
    }

}
