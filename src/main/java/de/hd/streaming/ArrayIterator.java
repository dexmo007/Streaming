package de.hd.streaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    private T[] array;
    private int pos = 0;

    private ArrayIterator(T[] array) {
        this.array = array;
    }

    public static <T> ArrayIterator<T> forArray(T[] array){
        return new ArrayIterator<>(array);
    }

    public boolean hasNext() {
        return pos < array.length;
    }

    public T next() throws NoSuchElementException {
        if (hasNext())
            return array[pos++];
        else
            throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}


