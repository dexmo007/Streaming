package de.hd.streaming;

public interface Collector<T, U> {

    void collect(T t);
    U get();


}
