package FlipFlop.flip.flop.Models;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to manage connection with users, each room should broadcast on a separate webSocket
 * */

public class FlipFlopRoom {
    private Board board;
    private List<User> users;
    private String roomName;
    private String roomPassword;

    //Get a new board, either on default value or based on some other params
    public void newBoard() {

    }

    //placeholder for other board settings
    private void newBoard(int param1, int param2) {
    }

    /**
     * User may join or leave the room at any time
     * */
    public User join(User user) {
        if (this.users.contains(user)) {
            return null;
        }
        this.users.add(user);
        return user;
    }

    public void leave(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
        }
    }

    //ToDo: start from here tomorrow
}
