package allenjt2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Allen Tang on 1/22/2017.
 * Tests for sim3 method
 */
public class Similarity3Test {
    @Test
    public void trueTests() throws Exception {
        // example cases
        assertTrue(Similarity3.sim3("abc","cba"));
        assertTrue(Similarity3.sim3("love", "cello"));
        assertTrue(Similarity3.sim3("abcdef12345", "f5bxyzf5bxyz"));

        //punctuation chars
        assertTrue(Similarity3.sim3("asjndakxvc.,!?", "?.,"));

        //overuse of repeating characters
        assertTrue(Similarity3.sim3("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabc", "abc"));

    }

    @Test
    public void falseTests() throws Exception {
        //example cases
        assertFalse(Similarity3.sim3("abc", "ccc"));
        assertFalse(Similarity3.sim3("abcdef12345", "12345"));

        //case sensitivity
        assertFalse(Similarity3.sim3("abc","Cba"));
        assertFalse(Similarity3.sim3("lOvE", "cello"));

        //empty string
        assertFalse(Similarity3.sim3("abc", ""));

        //over3
        assertFalse(Similarity3.sim3("abcd", "dcba"));

        //under3
        assertFalse(Similarity3.sim3("acdf", "ac"));
    }

}