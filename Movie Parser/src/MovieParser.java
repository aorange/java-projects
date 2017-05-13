import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.URL;


/**
 * The Movie Parser program prints to the console a list of movies, user inputted size, which can be organized as follows:
 * 1. All movies
 * 2. Films of a certain genre
 * 3. Films that are above a certain score
 * 4. Films that's popularity are above a certain score
 * Formatting for command line calls as follows: java MovieParser.java <movie size><print type>[excess param]
 * @author Allen Tang
 * @version 2.0
 *
 */
public class MovieParser {

    private static final String JSON_URL_FOR_MOVIES = "https://api.themoviedb.org/3/movie/popular?" +
            "api_key=" + ApiKey.apiKey +
            "&page=1";
    private static final int MAX_MOVIES = 998;
    private static final int MOVIES_PER_PAGE = 20;
    private static final int ADDITION_PAGE_INDEX_BUFFER = 2;

    public static void main(String args[]) throws Exception {

        //load the initial JSON data from the url
        URL movieURL = new URL(JSON_URL_FOR_MOVIES);
        InputStreamReader movieURLReader = new InputStreamReader(movieURL.openStream());
        Gson firstPage = new Gson();
        MoviesList wholeList = firstPage.fromJson(movieURLReader, MoviesList.class);


        //append the rest of the movies to the initial aggregateMovieList using arraylist.add()
        int requestedNumMovies = Integer.parseInt(args[0]);
        if (requestedNumMovies <= 0){
            System.out.println("Please select a number or movies greater than 0");
            return;
        }
        int pagesToLoad = requestedNumMovies / MOVIES_PER_PAGE;
        if (pagesToLoad > MAX_MOVIES) {
            pagesToLoad = MAX_MOVIES;
        }
        for (int currentPage = 0; currentPage < pagesToLoad; currentPage++) {
            URL additionalPage = new URL("https://api.themoviedb.org/3/movie/popular?api_key=" +
                    ApiKey.apiKey + "&page=" + currentPage + ADDITION_PAGE_INDEX_BUFFER);
            InputStreamReader additionalPageReader = new InputStreamReader(additionalPage.openStream());
            Gson nextPage = new Gson();
            MoviesList appendedPage = nextPage.fromJson(additionalPageReader, MoviesList.class);
            wholeList.appendresults(appendedPage.getresults());
        }

        double moviePrintArgument = 0;
        if (!args[2].isEmpty()) {
            try {
                moviePrintArgument = Double.parseDouble(args[2]);
            } catch (NumberFormatException numberE) {
                System.out.println("Please enter a number instead");
            }
        }


        //method calls the requested part of the program
        switch(args[1]) {
            case "all":
                System.out.print(printAll(wholeList, requestedNumMovies));
                break;
            case "genre":
                System.out.print(genreSearch(wholeList, (int) moviePrintArgument, requestedNumMovies));
                break;
            case "score":
                System.out.print(votesAboveThreshold(wholeList, moviePrintArgument, requestedNumMovies));
                break;
            case "pop":
                System.out.print(popAboveThreshold(wholeList, moviePrintArgument, requestedNumMovies));
                break;
            default:
                System.out.println("Incorrect query type: " + args[1]);
                break;
        }
    }


    /**
     *
     * Simplest method for printing out the title of all movies
     * @param aggregateMovieList parsed JSON data that has all the movie titles we need to print
     */
    public static String printAll(MoviesList aggregateMovieList, int requestedNumMovies){
        String concat = "";
        for (int i = 0; i < requestedNumMovies; i++){
            String name = aggregateMovieList.getresults().get(i).getOriginal_title();
            concat = concat + name + "\n";
        }
        return concat;
    }
    /**
     * Method for printing out all movies with the specified genre code
     * @param id The genre code that we use to search through the movie list
     * @param aggregateMovieList parsed JSON data with all movie titles, and genre tags for each movie
     *
     */
    public static String genreSearch(MoviesList aggregateMovieList, int id, int requestedNumMovies){
        String concat = "";
        for (int i = 0; i < requestedNumMovies; i++){
            //aggregateMovieList.getresults().get(i).getGenre_ids() is our array of genres
            for (int j = 0; j < aggregateMovieList.getresults().get(i).getGenre_ids().length; j++) {
                if (aggregateMovieList.getresults().get(i).getGenre_ids()[j] == id) {
                    String name = aggregateMovieList.getresults().get(i).getOriginal_title();
                    concat = concat + name + "\n";
                    break;
                }
            }
        }
        return concat;
    }
    /**
     * Method for printing out all movies with a score above a certain number
     * @param threshold The cutoff level for scores given by the user
     * @param aggregateMovieList Json data with all movie titles, and vote scores for each
     *
     */
    public static String votesAboveThreshold(MoviesList aggregateMovieList, double threshold, int requestedNumMovies){
        String concat = "";
        for (int i = 0; i < requestedNumMovies; i++){
            //prints movies greater than threshold
            if (aggregateMovieList.getresults().get(i).getVote_count() >= threshold) {
                String name = aggregateMovieList.getresults().get(i).getOriginal_title();
                concat = concat + name + "\n";
            }
        }
        return concat;
    }
    /**
     * Method for printing out all movie with popularity above a certain number
     * @param threshold The cutoff level for popularity given by the user
     * @param aggregateMovieList Json data with all movie titles and popularity for each
     *
     */
    public static String popAboveThreshold(MoviesList aggregateMovieList, double threshold, int requestedNumMovies){
        String concat = "";
        for (int i = 0; i < requestedNumMovies; i++){
            //prints movies greater than threshold
            if (aggregateMovieList.getresults().get(i).getPopularity() >= threshold) {
                String name = aggregateMovieList.getresults().get(i).getOriginal_title();
                concat = concat + name + "\n";
            }
        }
        return concat;
    }
}
