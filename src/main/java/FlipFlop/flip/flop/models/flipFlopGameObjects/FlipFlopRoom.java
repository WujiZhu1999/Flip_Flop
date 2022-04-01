package FlipFlop.flip.flop.models.flipFlopGameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used to manage connection with users, each room should broadcast on a separate webSocket
 * */

public class FlipFlopRoom {
    private Board board;
    private List<User> users;
    private String roomName;
    private Boolean locked;
    private static int PD_LEN = 12;

    /**
     * Setters
    * */
    public String join(User user) {
        if (this.users.contains(user)) {
            return null;
        }
        this.users.add(user);
        this.locked = true;
        return roomName;
    }

    public void leave(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }
    }

    public void refreshBoard(String difficulty) {
        BoardFactory.setupBoard(this.board, difficulty);
    }

    //placeholder for more fancy startup option
    public void refreshBoard(String param1, String param2) {

    }

    public void generateNewName() {
        Random random = new Random();
        String range = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(PD_LEN);
        for (int i = 0; i< PD_LEN; i++) {
            sb.append(range.charAt(random.nextInt(range.length())));
        }

        this.roomName = sb.toString();
    }

    /**
     * Setters end
     * */

    public FlipFlopRoom(){
        this.board = new Board();
        this.users = new ArrayList<>();
        generateNewName();
        this.locked = false;
    }

    /**
     * Getters
     * */

    public boolean isFree(){
        return !this.locked;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public Board getBoard() { return this.board; }

    public boolean isEmpty() {return this.users.isEmpty(); }

    /**
     * Getters end
     * */
}
