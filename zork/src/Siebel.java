
/**
 * Siebel class that will store our initial room's name and the rest of the rooms
 * @author Allen Tang
 */
public class Siebel {
    private String initialRoom;
    private Room [] rooms;

    public String getInitialRoom() {
        return initialRoom;
    }

    public void setInitialRoom(String initialRoom) {
        this.initialRoom = initialRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

}
