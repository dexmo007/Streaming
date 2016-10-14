import de.hd.streaming.Stream;

import java.util.Arrays;

/**
 * Stream tests
 */
public class StreamTest {
    public static void main(String[] args) {
        String[] strings = {"a", "b", "c"};
        // to array round trip
        String[] roundTrip = Stream.of(strings).toArray(new String[3]);
        System.out.println(Arrays.toString(roundTrip));
        // map
        String[] mapped = Stream.of(strings).map(s -> s + "_mapped").toArray(new String[3]);
        System.out.println(Arrays.toString(mapped));
        // filter
        String[] filtered = Stream.of(strings).filter(s -> !s.equals("b")).toArray(new String[2]);
        System.out.println(Arrays.toString(filtered));
        // reduce
        String reduce = Stream.of(strings).reduce("", String::concat);
        System.out.println(reduce);
    }
}
