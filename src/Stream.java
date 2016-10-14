import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream
 */
public class Stream<T> {

    private Iterator<T> iterator;

    private Stream(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public static <T> Stream<T> of(Iterable<T> iterable) {
        return new Stream<>(iterable.iterator());
    }

    public static <T> Stream<T> of(T[] array){
        return new Stream<>(new ArrayIterator<>(array));
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

    public Stream<T> filter(Predicate<T> predicate) {
        return new Stream<>(new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return null;
            }
        });
    }

    public Object[] toArray(){
        List<T> list = new LinkedList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list.toArray();
    }
    public T[] toArrayT(T[] a){
        List<T> list = new LinkedList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list.toArray(a);
    }
}
