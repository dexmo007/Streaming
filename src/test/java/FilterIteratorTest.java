import de.hd.streaming.Stream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by henri on 10/15/2016.
 */
public class FilterIteratorTest {

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

}
