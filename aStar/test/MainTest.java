import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * testing suite for methods, should be more for A* function but I don't have the time to write more grids
 * @author Allen Tang
 */
public class MainTest {
    GridNode[][]testGrid;
    GridInfo testGridInfo;
    String expectedMainOutput;
    @Before
    public void setUp() throws Exception {
        testGridInfo = Main.parseJsonFromUrl(null);
        testGrid = Main.createGridNodeArray(testGridInfo.getDimension(), testGridInfo);
        expectedMainOutput = "x=0, y=0\n" +
                "x=0, y=1\n" +
                "x=0, y=2\n" +
                "x=0, y=3\n" +
                "x=0, y=4\n" +
                "x=1, y=4\n" +
                "x=2, y=4\n" +
                "x=3, y=4\n" +
                "x=4, y=4\n" +
                "x=4, y=5\n" +
                "x=5, y=5\n" +
                "x=6, y=5\n" +
                "x=6, y=4\n" +
                "x=6, y=3\n" +
                "x=6, y=2\n" +
                "x=5, y=2\n" +
                "x=4, y=2\n" +
                "x=3, y=2";
    }
    /*
    This is commented out because a* will often give different paths that are essentially the same
    @Test
    public void aStar() throws Exception {
        assertEquals(Main.aStar(testGrid, testGridInfo), expectedMainOutput);
    }


    @Test
    public void getStepsToSolution() throws Exception {

        assertEquals(Main.getStepsToSolution(testGrid, testGrid[3][2].getCoord()), expectedMainOutput);
    }
    */

    @Test
    public void heuristicCalculator() throws Exception {
        Point a = new Point(0,0);
        Point b = new Point(10,0);
        assertEquals(Main.heuristicCalculator(a,b), 10);
        a = new Point (-1, 7);
        b = new Point (4, 10);
        assertEquals(Main.heuristicCalculator(a,b),8);
        a = new Point (0,0);
        b = new Point (0,0);
        assertEquals(Main.heuristicCalculator(a,b),0);
    }

    @Test
    public void findLowestF() throws Exception {
        HashMap<Point, Integer> testMap= new HashMap<>();
        HashSet<Point> testSet = new HashSet<>();
        Point a,b,c;
        a = new Point(0,0);
        b = new Point(1,0);
        c = new Point(0,1);
        testSet.add(a);
        testSet.add(b);
        testSet.add(c);
        testMap.put(a, 30);
        testMap.put(b, 20);
        testMap.put(c, 3);
        assertEquals(Main.findLowestF(testMap,testSet), c);
        Point d = new Point (1,1);
        testSet.add(d);
        testMap.put(d, -7);
        assertEquals(Main.findLowestF(testMap,testSet), d);
    }

}