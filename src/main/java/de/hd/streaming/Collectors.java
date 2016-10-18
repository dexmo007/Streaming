package de.hd.streaming;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Collectors {
    public static <E> Collector<E, String> join() {
        return join(Object::toString);
    }

    public static <T> Collector<T, String> join(Function<T, String> toString) {
        return new Collector<T, String>() {
            StringBuilder sb = new StringBuilder();
            @Override
            public void collect(T t) {
                sb.append(toString.apply(t));
            }

            @Override
            public String get() {
                return sb.toString();
            }
        };
    }

    public static <T> Collector<T, String> join(String delimiter) {
        return join(delimiter, Object::toString);
    }

    public static <T> Collector<T, String> join(String delimiter, Function<T, String> toString) {
        return new Collector<T, String>() {
            StringBuilder sb = new StringBuilder();

            @Override
            public void collect(T t) {
                sb.append(toString.apply(t)).append(delimiter);
            }

            @Override
            public String get() {
                sb.delete(sb.length() - delimiter.length(), sb.length());
                return sb.toString();
            }
        };
    }

    public static <T> Collector<T, List<T>> toList() {
        return new Collector<T, List<T>>() {
            List<T> list = new LinkedList<>();

            @Override
            public void collect(T t) {
                list.add(t);
            }

            @Override
            public List<T> get() {
                return list;
            }
        };
    }

    public static <K, T> Collector<T, Map<K, List<T>>> groupBy(Function<T, K> getKey) {
        return new Collector<T, Map<K, List<T>>>() {
            Map<K, List<T>> map = new HashMap<>();

            @Override
            public void collect(T t) {
                K key = getKey.apply(t);
                List<T> list = map.get(key);
                if (list == null) {
                    list = new LinkedList<>();
                    map.put(key, list);
                }
                list.add(t);
            }

            @Override
            public Map<K, List<T>> get() {
                return map;
            }
        };
    }
}
