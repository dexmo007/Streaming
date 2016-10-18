package de.hd.streaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ConditionalIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private T next = null;

    public ConditionalIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public abstract boolean accept(T t);

    @Override
    public boolean hasNext() {
        if (next != null)
            return true;

        if (iterator.hasNext()) {
            next = iterator.next();
            if (accept(next)) {
                return true;
            } else {
                // recursively test next element
                next = null;
                return hasNext();
            }
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
