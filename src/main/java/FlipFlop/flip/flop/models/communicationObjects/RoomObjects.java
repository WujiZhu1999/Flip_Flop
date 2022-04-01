package FlipFlop.flip.flop.models.communicationObjects;

import java.util.ArrayList;
import java.util.List;

public class RoomObjects {
    private List<String> roomKeys;

    public RoomObjects(){
        this.roomKeys = new ArrayList<>();
    }

    public void addRoom(String roomKey) {
        this.roomKeys.add(roomKey);
    }

    public List<String> getRooms(){
        return this.roomKeys;
    }
}
