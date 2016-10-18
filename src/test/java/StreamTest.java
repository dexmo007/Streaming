import de.hd.streaming.Stream;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * Stream tests
 */
public class StreamTest {

    @Test
    public void testCount() {
        Assert.assertEquals(0, Stream.of(new String[]{}).count());
        Assert.assertEquals(1, Stream.of(new String[]{"1"}).count());
        Assert.assertEquals(10, Stream.of(new String[10]).count());
    }

    @Test
    public void testAllMatch() {
        Assert.assertTrue(Stream.of("a", "a", "b", "t").allMatch(s -> s.length() == 1));
        Assert.assertFalse(Stream.of("a", "t", "tt", "t").allMatch(s -> s.length() == 1));
    }

    @Test
    public void testAnyMatch() {
        Assert.assertTrue(Stream.of("a", "b", "c", "d").anyMatch(s -> s.equals("b")));
        Assert.assertFalse(Stream.of("a", "b", "c", "d").anyMatch(s -> s.equals("z")));
    }

    @Test
    public void testForEach() {
        LinkedList<String> list = new LinkedList<>();
        String[] strings = {"a", "b", "c"};
        Stream.of(strings).forEach(list::add);
        Assert.assertArrayEquals(strings, list.toArray(new String[3]));
    }

    @Test
    public void testMap() {
        Stream<Integer> map = Stream.of("a", "bb", "ccc", "d").map(String::length);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 1}, map.toArray(new Integer[4]));
        Stream<Integer> map1 = Stream.of(new String[0]).map(String::length);
        Assert.assertArrayEquals(new Integer[0], map1.toArray(new Integer[0]));
    }

    @Test
    public void testReduce() {
        Assert.assertEquals("hello", Stream.of("h", "e", "l", "l", "o").reduce("", String::concat));
        Assert.assertEquals(new Integer(10), Stream.of(1, 2, 3, 4).reduce(0, (i, j) -> i + j));
    }

    @Test
    public void testLimit() {
        Supplier<Stream<Integer>> streamSupplier = () -> Stream.of(1, 2, 3, 4, 5);
        Assert.assertEquals(5, streamSupplier.get().count());
        Assert.assertEquals(3, streamSupplier.get().limit(3).count());
    }

    @Test
    public void testMax() {
        Assert.assertEquals(new Integer(8), Stream.of(1, 2, 3, 6, 3, 8, 5, 0, 1).max());
    }

    @Test
    public void testSkipWhile() {
        Integer[] actual = Stream.of(1, 2, 3, 4, 5, 6, 1, 7, 8, 9).skipWhile(n -> n < 6).toArray(new Integer[3]);
        Assert.assertArrayEquals(new Integer[]{6, 1, 7, 8, 9}, actual);
    }

    @Test
    public void testTakeWhile() {
        String[] actual = Stream.of("a", "b", "cc", "dd", "eee", "ff").takeWhile(s -> s.length() < 3).toArray(new String[3]);
        Assert.assertArrayEquals(new String[]{"a", "b", "cc", "dd"}, actual);
    }

}
