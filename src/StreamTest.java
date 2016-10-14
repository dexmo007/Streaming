import java.util.Arrays;

/**
 * Created by henri on 10/14/2016.
 */
public class StreamTest {
    public static void main(String[] args) {
        String[] strings = {"a", "b", "c"};
        Object[] round = Stream.of(strings).toArray();
        String[] roundT = Stream.of(strings).toArrayT(new String[3]);
        System.out.println(Arrays.toString(round));
        System.out.println(Arrays.toString(roundT));


//        Object[] mapped = Stream.of(strings).map(s -> s + "2").toArray();
//        System.out.println(Arrays.toString(mapped));
    }
}
