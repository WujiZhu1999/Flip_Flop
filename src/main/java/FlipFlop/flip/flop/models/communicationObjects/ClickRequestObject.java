package FlipFlop.flip.flop.models.communicationObjects;

public class ClickRequestObject {
    private String roomKey;
    private int x;
    private int y;
    private int version;

    public ClickRequestObject(String roomKey, int x, int y, int version){
        this.roomKey = roomKey;
        this.x = x;
        this.y = y;
        this.version = version;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getVersion() {
        return version;
    }
}
