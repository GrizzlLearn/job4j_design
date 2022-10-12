package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    private Node<E> firstNode;
    private Node<E> lastNode;
    private int size;
    private int modCount;

    public SimpleLinkedList() {

        lastNode = new Node<E>(null, null);
        firstNode = new Node<E>(null, lastNode);
    }

    private static class Node<E> {

        private E currentElement;
        private Node<E> nextElement;

        private Node(E currentElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
        }

        public void setNextElement(Node<E> nextElement) {
            this.nextElement = nextElement;
        }

        public void setCurrentElement(E currentElement) {
            this.currentElement = currentElement;
        }

        public Node<E> getNextElement() {
            return nextElement;
        }

        public E getCurrentElement() {
            return currentElement;
        }
    }

    @Override
    public void add(E value) {

        Node<E> newNode = lastNode;
        newNode.setCurrentElement(value);
        lastNode = new Node<E>(null, null);
        newNode.setNextElement(lastNode);
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {

        Objects.checkIndex(index, size);

        Node<E> target = firstNode.getNextElement();

        if (index != 0) {
            for (int i = 0; i < index; i++) {
                target = target.getNextElement();
            }
        }
        return target.getCurrentElement();
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {
            final int expectedModCount = modCount;
            Node<E> target = firstNode;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return target.getNextElement().getCurrentElement() != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                target = target.getNextElement();
                return target.getCurrentElement();
            }
        };
    }
}
