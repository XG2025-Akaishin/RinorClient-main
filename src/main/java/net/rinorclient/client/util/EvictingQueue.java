package net.rinorclient.client.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;


public class EvictingQueue<E> extends ArrayDeque<E> {
    private final int limit;
    private int size;

    public EvictingQueue(int limit) {
        this.limit = limit;
        size = 0;
    }

    @Override
    public boolean add(@NotNull E element) {
        boolean add = super.add(element);
        while (add && size() > limit) {
            super.remove();
            size--;
        }
        size++;
        return add;
    }

    @Override
    public void addFirst(@NotNull E element) {
        super.addFirst(element);
        size++;
        while (size() > limit) {
            super.removeLast();
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    public int limit() {
        return limit;
    }
}
