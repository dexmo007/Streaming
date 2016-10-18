package de.hd.streaming;


import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
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

    @SafeVarargs
    public static <T> Stream<T> of(T... elements) {
        return new Stream<>(ArrayIterator.forArray(elements));
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
//        return new Stream<>(new FilteredIterator<>(iterator, predicate));
        return new Stream<>(new ConditionalIterator<T>(iterator) {
            @Override
            public boolean accept(T t) {
                return predicate.test(t);
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

    public boolean allMatch(Predicate<? super T> predicate) {
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean anyMatch(Predicate<? super T> predicate) {
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    public void forEach(Consumer<? super T> action) {
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }

    public long count() {
        long count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public Stream<T> distinct() {
        return new Stream<>(new ConditionalIterator<T>(iterator) {
            private HashSet<T> elements = new HashSet<>();

            @Override
            public boolean accept(T t) {
                return !elements.contains(t);
            }

            @Override
            public T next() {
                T next = super.next();
                elements.add(next);
                return next;
            }
        });
    }

    /**
     * @return the first value in iteration, if none, null is returned
     */
    public T first() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * @param predicate to be checked
     * @return the first value satisfying the given predicate
     */
    public T first(Predicate<? super T> predicate) {
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (predicate.test(next)) {
                return next;
            }
        }
        return null;
    }

    public T min(Comparator<? super T> comparator) {
        if (!iterator.hasNext()) {
            return null;
        }
        T currentMin = iterator.next();
        while (iterator.hasNext()) {
            T next = iterator.next();
            // if next < currentMin
            if (comparator.compare(next, currentMin) < 0) {
                currentMin = next;
            }
        }
        return currentMin;
    }

    public T max(Comparator<? super T> comparator) {
        if (!iterator.hasNext()) {
            return null;
        }
        T currentMax = iterator.next();
        while (iterator.hasNext()) {
            T next = iterator.next();
            // if next > currentMax
            if (comparator.compare(next, currentMax) > 0) {
                currentMax = next;
            }
        }
        return currentMax;
    }

    /**
     * returns the maximum element, generic type must implement {@link Comparable<T>}
     *
     * @return maximum element
     * @throws ClassCastException if the generic type if this stream does not implement interface {@link Comparable<T>}
     */
    @SuppressWarnings("unchecked")
    public T max() {
        if (!iterator.hasNext()) {
            return null;
        }
        try {
            Comparable<T> currentMax = (Comparable<T>) iterator.next();
            while (iterator.hasNext()) {
                Comparable<T> next = (Comparable<T>) iterator.next();
                if (next.compareTo((T) currentMax) > 0) {
                    currentMax = next;
                }
            }
            return (T) currentMax;
        } catch (ClassCastException e) {
            throw new ClassCastException("generic type T must implement Comparable<T>");
        }
    }

    public Stream<T> limit(long max) {
        return new Stream<>(new Iterator<T>() {
            long count = 0;

            @Override
            public boolean hasNext() {
                return iterator.hasNext() && count < max;
            }

            @Override
            public T next() {
                if (count >= max)
                    throw new NoSuchElementException();

                count++;
                return iterator.next();
            }
        });
    }

    public Stream<T> skip(long n) {
        long count = 0;
        while (iterator.hasNext() && count++ < n) {
            iterator.next();
        }
        return this;
    }

    public Stream<T> skipWhile(Predicate<? super T> predicate) {
        return new Stream<>(new SkipWhileIterator<>(iterator, predicate));
    }

    public Stream<T> take(long n) {
        return new Stream<>(new Iterator<T>() {
            long count = 0;

            @Override
            public boolean hasNext() {
                return iterator.hasNext() && count < n;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                return iterator.next();
            }
        });
    }

    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return new Stream<>(new Iterator<T>() {
            T next = null;
            boolean taking = true;

            @Override
            public boolean hasNext() {
                if (taking && iterator.hasNext()) {
                    next = iterator.next();
                    if (predicate.test(next)) {
                        return true;
                    }
                    next = null;
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
        });
    }

    public <U> U collect(Collector<T, U> collector) {
        while (iterator.hasNext()) {
            collector.collect(iterator.next());
        }
        return collector.get();
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
