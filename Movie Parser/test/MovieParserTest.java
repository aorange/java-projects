import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * test suite for methods
 * @author Allen Tang
 */
public class MovieParserTest {
    MoviesList myList;
    Gson movieJesus;
    @Before public void init() {

        String ohGod = "{  \n" +
                "   \"page\":1,\n" +
                "   \"results\":[  \n" +
                "      {  \n" +
                "         \"poster_path\":\"\\/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg\",\n" +
                "         \"adult\":false,\n" +
                "         \"overview\":\"From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.\",\n" +
                "         \"release_date\":\"2016-08-02\",\n" +
                "         \"genre_ids\":[  \n" +
                "            28,\n" +
                "            80,\n" +
                "            14\n" +
                "         ],\n" +
                "         \"id\":297761,\n" +
                "         \"original_title\":\"Suicide Squad\",\n" +
                "         \"original_language\":\"en\",\n" +
                "         \"title\":\"Suicide Squad\",\n" +
                "         \"backdrop_path\":\"\\/34dxtTxMHGKw1njHpTjDqR8UBHd.jpg\",\n" +
                "         \"popularity\":174.019262,\n" +
                "         \"vote_count\":4033,\n" +
                "         \"video\":false,\n" +
                "         \"vote_average\":5.9\n" +
                "      },\n" +
                "      {  \n" +
                "         \"poster_path\":\"\\/ylXCdC106IKiarftHkcacasaAcb.jpg\",\n" +
                "         \"adult\":false,\n" +
                "         \"overview\":\"Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.\",\n" +
                "         \"release_date\":\"2016-12-01\",\n" +
                "         \"genre_ids\":[  \n" +
                "            10749,\n" +
                "            35,\n" +
                "            18,\n" +
                "            10402\n" +
                "         ],\n" +
                "         \"id\":313369,\n" +
                "         \"original_title\":\"La La Land\",\n" +
                "         \"original_language\":\"en\",\n" +
                "         \"title\":\"La La Land\",\n" +
                "         \"backdrop_path\":\"\\/fp6X6yhgcxzxCpmM0EVC6V9B8XB.jpg\",\n" +
                "         \"popularity\":124.171997,\n" +
                "         \"vote_count\":715,\n" +
                "         \"video\":false,\n" +
                "         \"vote_average\":8.1\n" +
                "      },\n" +
                "      {  \n" +
                "         \"poster_path\":\"\\/tIKFBxBZhSXpIITiiB5Ws8VGXjt.jpg\",\n" +
                "         \"adult\":false,\n" +
                "         \"overview\":\"Lynch discovers he is a descendant of the secret Assassins society through unlocked genetic memories that allow him to relive the adventures of his ancestor, Aguilar, in 15th Century Spain. After gaining incredible knowledge and skills he’s poised to take on the oppressive Knights Templar in the present day.\",\n" +
                "         \"release_date\":\"2016-12-21\",\n" +
                "         \"genre_ids\":[  \n" +
                "            28,\n" +
                "            12,\n" +
                "            14,\n" +
                "            878\n" +
                "         ],\n" +
                "         \"id\":121856,\n" +
                "         \"original_title\":\"Assassin's Creed\",\n" +
                "         \"original_language\":\"en\",\n" +
                "         \"title\":\"Assassin's Creed\",\n" +
                "         \"backdrop_path\":\"\\/r8aRipzR4wlDx0m7Bpi43Q84imc.jpg\",\n" +
                "         \"popularity\":114.52664,\n" +
                "         \"vote_count\":812,\n" +
                "         \"video\":false,\n" +
                "         \"vote_average\":5.2\n" +
                "      }\n" +
                "   ],\n" +
                "   \"total_results\":19652,\n" +
                "   \"total_pages\":983}";
        movieJesus = new Gson();
        myList = movieJesus.fromJson(ohGod, MoviesList.class);
    }
    @Test
    public void all() throws Exception {
        String returned = MovieParser.printAll(myList, 3);
        String expected = "Suicide Squad" + "\n" + "La La Land" + "\n" + "Assassin's Creed" + "\n";
        assertEquals(expected, returned);

    }

    @Test
    public void genre() throws Exception {
        String returned = MovieParser.genreSearch(myList, 12,3 );
        String expected = "Assassin's Creed" + "\n";
        assertEquals(expected, returned);
        returned = MovieParser.genreSearch(myList, 28,3 );
        String expected2 = "Suicide Squad" + "\n" + "Assassin's Creed" + "\n";
        assertEquals(expected2, returned);
        returned = MovieParser.genreSearch(myList, 8562,3 );
        assertEquals("", returned);
    }

    @Test
    public void votes() throws Exception {
        String returned = MovieParser.votesAboveThreshold(myList, 812, 3);
        String expected = "Suicide Squad" + "\n" + "Assassin's Creed" + "\n";
        assertEquals(expected, returned);
        returned = MovieParser.votesAboveThreshold(myList, 5000, 3);
        assertEquals("", returned);
    }

    @Test
    public void pop() throws Exception {
        String returned = MovieParser.popAboveThreshold(myList, 100, 3);
        String expected = "Suicide Squad" + "\n" + "La La Land" + "\n" + "Assassin's Creed" + "\n";
        assertEquals(expected, returned);
        returned = MovieParser.popAboveThreshold(myList, 700, 3);
        assertEquals("", returned);
    }

}