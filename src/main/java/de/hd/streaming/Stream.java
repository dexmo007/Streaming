package de.hd.streaming;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * de.hd.streaming.Stream
 */
public class Stream<T> {

    private Iterator<T> iterator;

    private Stream(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public static <T> Stream<T> of(Iterable<T> iterable) {
        return new Stream<>(iterable.iterator());
    }

    public static <T> Stream<T> of(T[] array) {
        return new Stream<>(ArrayIterator.forArray(array));
    }

    public <U> Stream<U> map(Function<? super T, ? extends U> mapper) {
        return new Stream<>(new Iterator<U>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public U next() {
                return mapper.apply(iterator.next());
            }
        });
    }

    public Stream<T> filter(Predicate<? super T> predicate) {
        return new Stream<>(new Iterator<T>() {
            T next;

            @Override
            public boolean hasNext() {
                if (next != null)
                    return true;

                if (iterator.hasNext()) {
                    next = iterator.next();
                    if (predicate.test(next)) {
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
                if (next == null) {
                    if (hasNext()) {
                        T rnext = next;
                        next = null;
                        return rnext;
                    } else {
                        throw new NoSuchElementException();
                    }
                } else {
                    T rnext = next;
                    next = null;
                    return rnext;
                }
            }
        });
    }

    public T reduce(T seed, BinaryOperator<T> accumulator) {
        T res = seed;
        while (iterator.hasNext()) {
            res = accumulator.apply(res, iterator.next());
        }
        return res;
    }

    public Iterator<T> iterator() {
        return iterator;
    }

    public Object[] toArray() {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list.toArray();
    }

    public T[] toArray(T[] a) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list.toArray(a);
    }
}
