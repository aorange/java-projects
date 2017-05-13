import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Program that takes in url input for a json file, if one is not given it defaults to the constant JSON_GRID_URL.
 * After, the program parses it into a POJO using Gson and then creates a grid based on JSON specs. The A* algorithm
 * is then used to path from start to end, avoiding the given obstacles.
 * @author Allen Tang
 */
public class Main {

    private static final String JSON_GRID_URL = "https://courses.engr.illinois.edu/cs126/resources/grid.json";
    private static final int MOVE_COST = 10;

    public static void main(String args[]){
        //json parsing
        GridInfo gridData;
        if(args.length == 0){
            gridData = parseJsonFromUrl(null);
        } else{
            gridData = parseJsonFromUrl(args[0]);
        }
        if (gridData == null){ //check here in case of an exception earlier
            System.out.println("Program failed due to lack of json data at URL");
            return;
        }
        //data structure and start point inits
        GridNode[][] processedGrid = createGridNodeArray(gridData.getDimension(), gridData);

        //A* and output
        System.out.print(aStar(processedGrid, gridData));
    }

    /**
     * A* algorithm created according to pseudocode at https://en.wikipedia.org/wiki/A*_search_algorithm.
     * Note that g and movement cost are often used interchangeably. This function will traverse through a grid and
     * find the shortest path from start to finish, avoiding obstacles.
     * @param processedGrid our grid with all the obstacles and heuristics
     * @param gridData the original POJO that has info we need such as end and start coords
     * @return a string representation of the path we take to get to the end in the form "x=0, y=0"
     */
    public static String aStar(GridNode[][] processedGrid, GridInfo gridData){
        HashSet<Point> openSet = new HashSet<>();
        HashSet<Point> closedSet = new HashSet<>();
        HashMap<Point, Integer> movementCost = new HashMap<>();
        HashMap<Point, Integer> fScore = new HashMap<>(); //movement+heuristic

        Point start = processedGrid[gridData.getStart().getX()][gridData.getStart().getY()].getCoord();
        movementCost.put(start, 0);
        fScore.put(start, processedGrid[start.getX()][start.getY()].getHeuristic());
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Point current = findLowestF(fScore, openSet);
            //if end is found
            if (current.getX() == gridData.getEnd().getX() && current.getY() == gridData.getEnd().getY()) {
                return getStepsToSolution(processedGrid, current);
            }
            openSet.remove(current);
            closedSet.add(current);
            Point test = new Point(0,0);

            //init adjacent nodes for easy for each looping
            Point[] neighbors = new Point[4];
            if (current.getX() < 9 && processedGrid[current.getX()+1][current.getY()] != null)
                neighbors[0] = processedGrid[current.getX()+1][current.getY()].getCoord();
            if (current.getX() > 0 && processedGrid[current.getX()-1][current.getY()] != null)
                neighbors[1] = processedGrid[current.getX()-1][current.getY()].getCoord();
            if (current.getY() < 9 && processedGrid[current.getX()][current.getY()+1] != null)
                neighbors[2] = processedGrid[current.getX()][current.getY()+1].getCoord();
            if (current.getY() > 0 && processedGrid[current.getX()][current.getY()-1] != null)
                neighbors[3] = processedGrid[current.getX()][current.getY()-1].getCoord();

            for (Point neighbor : neighbors) {
                if (neighbor == null){
                    continue;
                }
                //the null indicates an obstacle
                if (closedSet.contains(neighbor) || processedGrid[neighbor.getX()][neighbor.getY()] == null) {
                    continue;
                }
                int tentativeGScore = movementCost.get(current) + MOVE_COST;
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (movementCost.containsKey(neighbor) || tentativeGScore >= movementCost.get(neighbor)) {
                    continue;
                }
                //record best score
                processedGrid[neighbor.getX()][neighbor.getY()].setParent(current);
                movementCost.put(neighbor, tentativeGScore);
                fScore.put(neighbor, tentativeGScore + heuristicCalculator(neighbor, gridData.getEnd()));
            }
        }
        return "no path";
    }

    /**
     * Traverses from the end to the start along our parent node path and appends the result to a string. The string
     * is then reverse sliced by coordinate pair to create the path from start to finish.
     * @param gridData Our grid which contains the parents we need to traverse
     * @param traverse The pointer that we use to move through the parents, starts at the end
     * @return the reversed coordinate pairs that are in order from start to finish
     */
    public static String getStepsToSolution(GridNode[][] gridData, Point traverse){
        String totalPath = "";
        while (traverse != null){
            //split using s since \n triggers on space
            totalPath += "x=" + traverse.getX() + ", y=" + traverse.getY() + "s";
            traverse = gridData[traverse.getX()][traverse.getY()].getParent();
        }
        String[] coordPairs = totalPath.split("s");
        totalPath = "";
        for (int i = 0; i < coordPairs.length; i++){
            totalPath += coordPairs[coordPairs.length-i-1] + "\n";
        }
        return totalPath;
    }

    /**
     * Calculates heuristic distance using the manhattan distance method
     * @param start first coordinate pair
     * @param goal end coordinate pair
     * @return the manhattan distance between them
     */
    public static int heuristicCalculator(Point start, Point goal){
        return Math.abs(start.getX()-goal.getX()) + Math.abs(start.getY()-goal.getY());
    }

    /**
     * Finds the lowest f score, or the lowest sum of heuristic and movement cost
     * @param fScore hashmap with fscores mapped to points
     * @param openSet openset of all possible candidates for the next move
     * @return the point we will check next because it has the lowest f score
     */
    public static Point findLowestF(HashMap<Point, Integer> fScore, HashSet<Point> openSet){
        Point lowest = null;
        for (Point currentCheck : openSet){
            if (lowest == null || fScore.get(lowest) > fScore.get(currentCheck)){
                lowest = currentCheck;
            }
        }
        return lowest;
    }

    /**
     * Initializes our grid that we use throughout the program
     * @param dimension The dimensions of the grid will be dimension x dimension
     * @param gridData The data of where we should put our obstacles
     * @return The processed grid with an organized representation of our json parsing
     */
    public static GridNode[][] createGridNodeArray(int dimension, GridInfo gridData){
        GridNode[][] processedGrid = new GridNode[dimension][dimension];
        for (int x = 0; x < dimension; x++){
            for (int y = 0; y < dimension; y++){
                //using manhattan distance formula for the heuristic
                Point currCoord = new Point(x, y);
                int heuristic = heuristicCalculator(currCoord, gridData.getEnd());
                processedGrid[x][y] = new GridNode(currCoord, heuristic);
            }
        }
        //putting in obstacles
        for (Point obstacle: gridData.getObstacles()) {
            processedGrid[obstacle.getX()][obstacle.getY()] = null;
        }
        return processedGrid;
    }



    /**
     * Method that parses the Json from a url using Gson, handles two exceptions internally. The url is supplied by
     * the user. If no url is supplied our default one is loaded instead.
     * @param userInputtedUrl url to load json from provided by user, may be null which invokes our default url
     * @return the parsed GridInfo object which is returned to main for processing
     */
    public static GridInfo parseJsonFromUrl(String userInputtedUrl){
        try {
            String urlToParse;
            if (userInputtedUrl == null){
                urlToParse = JSON_GRID_URL;
            } else{
                urlToParse = userInputtedUrl;
            }
            URL jsonUrl = new URL(urlToParse);
            InputStreamReader reader = new InputStreamReader(jsonUrl.openStream());
            Gson gridGson = new Gson();
            return gridGson.fromJson(reader, GridInfo.class);
        } catch(MalformedURLException urlE){
            System.out.println("Bad URL");
            return null;
        } catch(IOException ioE){
            System.out.println("Exception: " + ioE);
            return null;
        }
    }
}
