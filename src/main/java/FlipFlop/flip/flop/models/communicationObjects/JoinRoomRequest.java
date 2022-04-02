package FlipFlop.flip.flop.models.communicationObjects;

public class JoinRoomRequest {
    private String userName;
    private String roomKey;

    public JoinRoomRequest(String userName, String roomKey){
        this.userName = userName;
        this.roomKey = roomKey;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public String getUserName() {
        return userName;
    }
}
