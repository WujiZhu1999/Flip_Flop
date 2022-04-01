package FlipFlop.flip.flop.models.flipFlopGameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains a lot of rooms
 * */
public class FlipFlopRoomLobby {
    private static final int ROOM_LIMIT = 10;
    private List<FlipFlopRoom> rooms;
    private static FlipFlopRoomLobby lobby = null;

    private FlipFlopRoomLobby(){
        this.rooms = new ArrayList<>();
    }

    public static FlipFlopRoomLobby getInstance(){
        if (Objects.isNull(lobby)) {
            lobby = new FlipFlopRoomLobby();
        }
        return lobby;
    }

    /**
     * Getters
     * */
    public List<String> getRoomKeys() {
        return this.rooms.stream()
                .map(room -> room.getRoomName())
                .collect(Collectors.toList());
    }

    public FlipFlopRoom getRoom(String roomKey) {
        return this.rooms.stream()
                .filter(room -> room.getRoomName().equals(roomKey))
                .findFirst()
                .orElse(null);
    }


    /**
     * Getter ends
     * */

    /**
     * Setters
     * */
    //synchronized this so room of same name won't be added
    public synchronized String addRoom(FlipFlopRoom room) {
        if (this.getRoomKeys().contains(room.getRoomName()) || this.rooms.size() >= ROOM_LIMIT){
            return null;
        }

        this.rooms.add(room);
        return room.getRoomName();
    }

    public void removeRoom(FlipFlopRoom room) {
        if(this.rooms.contains(room)){
            this.rooms.remove(room);
        }
    }
    /**
     * Setter ends
     * */
}
