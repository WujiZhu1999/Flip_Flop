package FlipFlop.flip.flop.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlipFlopLobby {
    private static List<FlipFlopRoom> rooms;
    private static List<String> roomNames;
    private static int ROOM_NUMBER = 10;

    private static FlipFlopLobby lobby;

    public static FlipFlopLobby getLobby() {
        if(Objects.isNull(FlipFlopLobby.lobby)){
            FlipFlopLobby.lobby = new FlipFlopLobby();
            FlipFlopLobby.lobby.rooms = new ArrayList<>();
            FlipFlopLobby.lobby.roomNames = new ArrayList<>();

            for(int i = 0; i< ROOM_NUMBER; i++){
                FlipFlopRoom room = new FlipFlopRoom();
                //ensure they don't share same room name
                while(FlipFlopLobby.roomNames.contains(room.getRoomName())){
                    room.generateNewName();
                }
                FlipFlopLobby.lobby.rooms.add(room);
                FlipFlopLobby.lobby.roomNames.add(room.getRoomName());


            }
        }
        return lobby;
    }

    public String joinFreeRoom(User user) {
        String freeRoomNames = getFreeRoom();
        if (Objects.isNull(freeRoomNames)) {
            return null;
        }

        return (this.joinRoom(freeRoomNames, user));


    }

    public static String getFreeRoom(){
        List<FlipFlopRoom> roomWithSameName = FlipFlopLobby.rooms.stream().filter(r -> r.isFree()).collect(Collectors.toList());
        if (roomWithSameName.isEmpty()){
            return null;
        } else {
            return roomWithSameName.get(0).getRoomName();
        }
    }


    public static String joinRoom(String roomName, User user){
        List<FlipFlopRoom> roomWithSameName = FlipFlopLobby.rooms.stream().filter(r -> r.getRoomName().equals(roomName)).collect(Collectors.toList());
        if(roomWithSameName.isEmpty()){
            return null;
        }

        FlipFlopRoom room = roomWithSameName.get(0);
        return room.join(user);
    }

    public static void leaveRoom(String roomName,User user) {
        List<FlipFlopRoom> roomWithSameName = FlipFlopLobby.rooms.stream().filter(r -> r.getRoomName().equals(roomName)).collect(Collectors.toList());
        if(roomWithSameName.isEmpty()){
            return;
        }

        FlipFlopRoom room = roomWithSameName.get(0);
        room.leave(user);

        //check if needs cleaning
        if (room.needsClean()){
            FlipFlopLobby.roomNames.remove(room.getRoomName());
            synchronized (roomNames){
                room.generateNewName();
                while (FlipFlopLobby.roomNames.contains(room.getRoomName())){
                    room.generateNewName();
                }
                FlipFlopLobby.roomNames.add(room.getRoomName());
            }

            room.cleaning();

        }
    }

    public FlipFlopRoom getRoom(String roomName) {
        List<FlipFlopRoom> roomWithSameName = FlipFlopLobby.rooms.stream().filter(r -> r.getRoomName().equals(roomName)).collect(Collectors.toList());
        if(roomWithSameName.isEmpty()){
            return null;
        }

        FlipFlopRoom room = roomWithSameName.get(0);
        return room;
    }

    public static void main(String args[]){
        FlipFlopLobby lo = FlipFlopLobby.getLobby();

        User u1 = new User("LUC");
        User u2 = new User("LUC2");

        String name = lo.joinFreeRoom(u1);
        String name2 = lo.joinRoom(name, u2);

        FlipFlopRoom r = lo.getRoom(name2);
        BoardFactory.setupBoard(r.getBoard(), "easy");
        r.getBoard().click(1,1);
        r.getBoard().click(1,2);
        r.getBoard().click(2,1);
        r.getBoard().click(2,2);
        r.getBoard().click(3,1);
        r.getBoard().click(3,2);

        lo.leaveRoom(name2,u1);
        lo.leaveRoom(name2,u2);
        lo.leaveRoom(name2,u1);



    }

}
