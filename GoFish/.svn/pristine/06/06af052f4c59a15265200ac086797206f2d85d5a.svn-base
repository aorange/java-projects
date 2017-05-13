import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Allen Tang on 3/6/2017.
 */
public class GameTest {

    Game goFishTest;
    int [] playerTypes = {0,0,0,1};

    @Before
    public void setUp() throws Exception {
        goFishTest = new Game(playerTypes);
        goFishTest.dealInitialCards();
    }

    @Test
    public void dealInitialCards() throws Exception {
        for (int i = 0; i < goFishTest.getPlayers().size(); i++){
            assertEquals(goFishTest.getPlayerHands()[i].size(), 5);
        }
    }

    @Test
    public void ifEmptyDraw() throws Exception {
        assertTrue(goFishTest.ifEmptyDraw());
    }

    @Test
    public void removeRankFromHand() throws Exception {
        int removedCard = goFishTest.getPlayerHands()[goFishTest.getCurrentPlayer()].getCard(0).getRank();
        assertEquals(goFishTest.removeRankFromHand(removedCard, 0).get(0).getRank(), removedCard);
        assertEquals(goFishTest.removeRankFromHand(32, 0).size(), 0);
    }

    @Test
    public void checkBook() throws Exception {
        assertEquals(goFishTest.checkBook(2), "");
    }

    @Test
    public void gameOver() throws Exception {
        assertFalse(goFishTest.gameOver());
    }

    @Test
    public void getNumBooksForPlayer() throws Exception {
        for (int i = 0; i < goFishTest.getPlayers().size(); i++) {
            assertEquals(goFishTest.getNumBooksForPlayer()[i], 0);
        }
    }

    @Test
    public void getWinner() throws Exception {
        assertEquals(goFishTest.getWinner(), 0);
    }

}