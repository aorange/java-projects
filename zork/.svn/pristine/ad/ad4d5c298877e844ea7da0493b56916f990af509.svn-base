import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Miniature room traversal game that takes input from a user to move from room to room and prints out the description
 * of each room.
 * @author Allen Tang
 */
public class Main {

    private final static int MIN_ROOMS_FOR_OR = 2;
    private final static int SUBSTRING_AFTER_GO = 3;

    /**
     * Main method which will execute all of our methods that do the work
     * @param args args[0] will be our only parameter, if it exists then we pass it as a url to be parsed, if not
     *             we use null
     */
    public static void main(String args[]){
        Siebel layout;
        if(args.length == 0){
            layout = parseJsonFromUrl(null);
        } else{
            layout = parseJsonFromUrl(args[0]);
        }
        if (layout == null){ //check here in case of an exception earlier
            return;
        }
        Map<String, Room> roomMap = initializeHashmap(layout);
        String currentRoom = layout.getInitialRoom();
        oneCycleOfZork(roomMap, currentRoom);
    }



    /**
     * The meat of the whole program. Each call of the method acts as a cycle of the zork game, or rather one room
     * and the other rooms you can go to from there. The method takes in command line arguments for movement.
     * @param roomMap our hash map that allows us to traverse the room objects to grab data
     * @param currentRoom the current room we are on for this cycle of the zork game, this changes for each call usually
     */
    public static void oneCycleOfZork(Map<String, Room> roomMap, String currentRoom){
        //initial print statements
        System.out.println(roomMap.get(currentRoom).getDescription());
        String possibleDirections = "";
        int numAdjacentRooms = roomMap.get(currentRoom).getDirections().length;

        //some advanced logic here for punctuation sake
        for (int numDirections = 0; numDirections < numAdjacentRooms; numDirections++){
            String currentDirection = roomMap.get(currentRoom).getDirections()[numDirections].getDirection();
            if (numAdjacentRooms >= MIN_ROOMS_FOR_OR && (numDirections == numAdjacentRooms -1)){
                possibleDirections += ("or " + currentDirection);
            } else if (numAdjacentRooms == 1){
                possibleDirections += currentDirection;
            } else{
                possibleDirections += (currentDirection + ", ");
            }

        }
        System.out.println("From here you can go: " + possibleDirections);

        //our scanner that takes in the command
        Scanner cmdParser = new Scanner(System.in);
        String cmd = cmdParser.nextLine();
        String lowerCaseCmd = cmd.toLowerCase();
        if (lowerCaseCmd.equals("done") | lowerCaseCmd.equals("exit") | lowerCaseCmd.equals("quit")) {
            return;//guarding return
        } else if (lowerCaseCmd.contains("go")){
            //if we see "go", then enter the next level of logic
            String nextRoom = checkCmdValidityAndFindRoom(lowerCaseCmd, roomMap.get(currentRoom).getDirections());
            if (nextRoom != null){ //recursively call the next iteration
                oneCycleOfZork(roomMap, nextRoom);
            } else{ //re-call this iteration since an invalid command was given
                System.out.println("I can't go " + cmd);
                oneCycleOfZork(roomMap, currentRoom);
            }
        } else{ //re-call current iteration due to invalid command
            System.out.println("I don't understand: '" + cmd + "'");
            oneCycleOfZork(roomMap, currentRoom);
        }
    }



    /**
     *
     * @param cmd the user command which has been modified to be all lower case
     * @param directionsToCheck the adjacent room array with the different possibilities
     * @return if we find the right room then we return its name, otherwise just null it
     */
    public static String checkCmdValidityAndFindRoom(String cmd, AdjacentRoom[] directionsToCheck){
        for (AdjacentRoom currentCheck : directionsToCheck){
            if (currentCheck.getDirection().toLowerCase().equals(cmd.substring(SUBSTRING_AFTER_GO))){
                return currentCheck.getRoom();
            }
        }
        return null;
    }



    /**
     * Method for initializing our hash map which matches Room names to the Room objects
     * @param layout the building we are making map for, a parsed json file
     * @return completed hash map with names matched to the respective room objects
     */
    public static Map<String, Room> initializeHashmap(Siebel layout){
        Map<String, Room> roomMap = new HashMap<>();
        for (Room current : layout.getRooms()){
            roomMap.put(current.getName(), current); //match each room name to the room
        }
        return roomMap;
    }



    /**
     * Method that parses the Json from a url using Gson, handles two exceptions internally. The url is supplied by
     * the user. If no url is supplied our default one is loaded instead.
     * @param userInputtedUrl url to load json from provided by user, may be null which invokes our default url
     * @return the parsed Siebel object
     */
    public static Siebel parseJsonFromUrl(String userInputtedUrl){
        try {
            //read JSON from said url and store in object
            String urlToParse;
            if (userInputtedUrl == null){ //if the user supplies no url then use this one
                urlToParse = "https://courses.engr.illinois.edu/cs126/resources/siebel.json";
            } else{
                urlToParse = userInputtedUrl;
            }
            URL jsonUrl = new URL(urlToParse);
            InputStreamReader reader = new InputStreamReader(jsonUrl.openStream());
            Gson buildingGson = new Gson();
            return buildingGson.fromJson(reader, Siebel.class);
        } catch(MalformedURLException urlE){
            System.out.println("Bad URL");
            return null;
        } catch(IOException ioE){
            System.out.println("Exception: " + ioE);
            return null;
        }
    }
}
