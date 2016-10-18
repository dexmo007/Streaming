package de.hd.streaming;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

class SkipWhileIterator<T> implements Iterator<T> {
    private Iterator<T> baseIterator;
    private T next = null;

    public SkipWhileIterator(Iterator<T> baseIterator, Predicate<? super T> predicate) {
        this.baseIterator = baseIterator;
        while (baseIterator.hasNext()) {
            T next = baseIterator.next();
            if (!predicate.test(next)) {
                this.next = next;
                break;
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (next != null)
            return true;

        if (baseIterator.hasNext()) {
            next = baseIterator.next();
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (next == null && !hasNext())
            throw new NoSuchElementException();

        T tmp = next;
        next = null;
        return tmp;
    }
}
