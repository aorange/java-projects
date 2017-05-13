/**
 * Rooms adjacent to said Room, contains a direction relative to original room and the room name
 * @author Allen Tang
 */
public class AdjacentRoom {
    private String direction;
    private String room;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
