package ru.job4j.tree;

import java.util.*;
import java.util.function.Predicate;

public class SimpleTree<E> implements Tree<E> {
    private final Node<E> root;

    public SimpleTree(final E root) {
        this.root = new Node<>(root);
    }

    private Optional<Node<E>> findByPredicate(Predicate<Node<E>> condition) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);

        while (!data.isEmpty()) {
            Node<E> el = data.poll();

            if (condition.test(el)) {
                result = Optional.of(el);
                break;
            }
            data.addAll(el.children);
        }

        return result;
    }

    public boolean isBinary() {
        return findByPredicate(el -> el.children.size() > 2).isEmpty();
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        return findByPredicate(el -> el.value.equals(value));
    }

    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> findParent = findBy(parent);
        Optional<Node<E>> findChild = findBy(child);
        boolean result = false;

        if (findParent.isPresent() && findChild.isEmpty()) {
            Node<E> newChild = new Node<>(child);
            findParent.get().children.add(newChild);
            result = true;
        }

        return result;
    }
}
