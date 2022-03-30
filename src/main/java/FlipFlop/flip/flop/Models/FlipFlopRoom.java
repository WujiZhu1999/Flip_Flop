package FlipFlop.flip.flop.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Used to manage connection with users, each room should broadcast on a separate webSocket
 * */

public class FlipFlopRoom {
    private Board board;
    private List<User> users;
    private String roomName;
    private Boolean locked;
    private Boolean needsCleaning;
    private static int PD_LEN = 12;

    /**
     * User may join or leave the room at any time, if success, will return room name.
     * */
    public String join(User user) {
        if (this.users.contains(user)) {
            return null;
        }
        this.users.add(user);
        this.locked = true;
        return roomName;
    }

    /**
     * Boolean indicate whether the room is empty
     * */
    public void leave(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }

        if (this.users.isEmpty()) {
            this.needsCleaning = true;
        }
    }

    /**
     * When all users leave the room, reset the roomPassword so others could join
     * */
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

    public FlipFlopRoom(){
        this.board = new Board();
        this.users = new ArrayList<>();
        generateNewName();
        this.locked = false;
        this.needsCleaning = false;
    }

    public void cleaning() {
        this.board = new Board();
        this.needsCleaning = false;
        this.locked = false;
    }

    public boolean isFree(){
        return !this.locked;
    }

    public boolean needsClean() {return this.needsCleaning; }

    public String getRoomName(){
        return this.roomName;
    }

    public Board getBoard() {return this.board; }
}
