package FlipFlop.flip.flop.service;

import FlipFlop.flip.flop.models.flipFlopGameObjects.FlipFlopRoom;
import FlipFlop.flip.flop.models.flipFlopGameObjects.FlipFlopRoomLobby;
import FlipFlop.flip.flop.models.flipFlopGameObjects.User;

import java.util.Objects;

/**
 * React to users' action in lobby level
 * Things can do:
 *  1. Click a button to join any free room
 *  2. Enter a room by room name
 * */
public class LobbyActionService {
    private static final int joinRoomTrial = 3;
    /**
     * Join any free room available, if no free room available, return null
     * */
    public String joinRoom(User user){
        int trials = 3;
        FlipFlopRoom room = new FlipFlopRoom();
        String roomKey = null;

        while(Objects.isNull(roomKey) && trials > 0){
            roomKey = FlipFlopRoomLobby.getInstance().addRoom(room);
            trials = trials - 1;
        }

        if (Objects.isNull(roomKey)) {
            return null;
        } else {

            return room.join(user);
        }
    }

    /**
     * Join room by room name, return room key
     * */
    public String joinRoom(User user, String roomKey) {
        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);
        if (Objects.isNull(room)) {
            return null;
        }

        return room.join(user);
    }

}
