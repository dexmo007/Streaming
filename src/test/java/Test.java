import de.hd.streaming.Collectors;
import de.hd.streaming.Stream;

import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Map<Integer, List<String>> map = Stream.of("a", "bb", "ccc", "d", "ee", "fff").collect(Collectors.groupBy(String::length));
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + Stream.of(entry.getValue()).collect(Collectors.join(", ")));
        }
    }

}
