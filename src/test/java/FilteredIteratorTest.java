import de.hd.streaming.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FilteredIteratorTest {

    private String[] array = {"a", "b", "c", "d"};
    private Stream<String> filtered;

    @Before
    public void init() {
        filtered = Stream.of(array).filter(s -> !s.equals("b") && !s.equals("d"));
    }

    @Test
    public void testFilterNormal() {
        String[] strings = filtered.toArray(new String[2]);
        assertArrayEquals(new String[]{"a", "c"}, strings);
    }

    @Test(expected = NoSuchElementException.class)
    public void testFilterNext() {
        Iterator<String> it = filtered.iterator();
        assertEquals("a", it.next());
        assertEquals("c", it.next());
        // should throw exception
        it.next();
    }

    @Test
    public void testMultipleHasNexts(){
        Iterator<String> it = filtered.iterator();
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertEquals("a",it.next());
        assertEquals("c",it.next());
        assertFalse(it.hasNext());
        assertFalse(it.hasNext());
//        new ArrayList<String>().stream().
    }

    @Test
    public void testSingle() {
        LinkedList<String> strings = new LinkedList<>();
        strings.add("a");
        strings.add("a");
        Assert.assertArrayEquals(new String[]{"a"}, Stream.of(strings).distinct().toArray());
        strings.add("a");
        Assert.assertArrayEquals(new String[]{"a"}, Stream.of(strings).distinct().toArray());
    }

    @Test
    public void testMultiple() {
        String[] strings = {"a", "c", "a", "d", "c"};
        Assert.assertArrayEquals(new String[]{"a", "c", "d"}, Stream.of(strings).distinct().toArray());

    }

}
