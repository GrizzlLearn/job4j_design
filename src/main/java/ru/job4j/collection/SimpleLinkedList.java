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

        lastNode = new Node<E>(null, firstNode, null);
        firstNode = new Node<E>(null, null, lastNode);
    }

    private static class Node<E> {

        private E currentElement;
        private final Node<E> prevElement;
        private Node<E> nextElement;

        private Node(E currentElement, Node<E> prevElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.prevElement = prevElement;
            this.nextElement = nextElement;
        }

        public void setNextElement(Node<E> nextElement) {
            this.nextElement = nextElement;
        }

        public void setCurrentElement(E currentElement) {
            this.currentElement = currentElement;
        }

        public Node<E> getPrevElement() {
            return prevElement;
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
        lastNode = new Node<E>(null, newNode, null);
        newNode.setNextElement(lastNode);
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {

        Objects.checkIndex(index, size);

        Node<E> target = firstNode.getNextElement();

        E result;

        if (index == 0) {
            result = target.getCurrentElement();
        } else if (index == size) {
            result = lastNode.getPrevElement().getCurrentElement();
        } else {
            for (int i = 0; i < index; i++) {
                target = target.getNextElement();
            }
            result = target.getCurrentElement();
        }
        return result;
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
