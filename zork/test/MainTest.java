import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Allen Tang on 2/13/2017.
 */
public class MainTest {
    Siebel building;
    Map<String, Room> roomMap;
    @Before
    public void setUpSiebelObject() throws Exception{
        building = Main.parseJsonFromUrl(null);
        roomMap = Main.initializeHashmap(building);
    }

    @Test
    public void checkCmdValidityAndFindRoomTest() throws Exception {
        String expected = "SiebelEntry";
        String actual = Main.checkCmdValidityAndFindRoom("go east", roomMap.get("MatthewsStreet").getDirections());
        assertEquals(expected, actual);
        expected = "SiebelNorthHallway";
        actual = Main.checkCmdValidityAndFindRoom("go north", roomMap.get("SiebelEntry").getDirections());
        assertEquals(expected, actual);
        expected = null;
        actual = Main.checkCmdValidityAndFindRoom("go south", roomMap.get("SiebelEntry").getDirections());
        assertEquals(expected, actual);
    }

    @Test
    public void initializeHashmapTest() throws Exception {
        String expected = "SiebelEntry";
        String actual = roomMap.get("SiebelEntry").getName();
        assertEquals(actual, expected);
        expected = "AcmOffice";
        actual = roomMap.get("AcmOffice").getName();
        assertEquals(actual, expected);
        expected = "MatthewsStreet";
        actual = roomMap.get("MatthewsStreet").getName();
        assertEquals(actual, expected);
    }
}