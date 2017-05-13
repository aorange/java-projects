import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Allen Tang on 3/6/2017.
 */
public class MainTest {
    @Test
    public void initPlayerStyles() throws Exception {
        int [] expected = {0,0,0,1};
        String [] input = {"naive", "naive", "naive", "smart"};
        assertArrayEquals(expected, Main.initPlayerStyles(input));
        String [] input2 = {"naive", "naive", "naive", "potato"};
        assertArrayEquals(null, Main.initPlayerStyles(input2));
        String [] input3 = {"smart", "smart", "smart"};
        int [] expected3 = {1,1,1};
        assertArrayEquals(expected3, Main.initPlayerStyles(input3));
    }

}