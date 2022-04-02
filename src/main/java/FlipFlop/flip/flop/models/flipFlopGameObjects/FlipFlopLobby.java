package FlipFlop.flip.flop.models.flipFlopGameObjects;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import FlipFlop.flip.flop.service.BoardActionService;
import FlipFlop.flip.flop.service.LobbyActionService;
import FlipFlop.flip.flop.service.RoomActionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlipFlopLobby {
    private static List<FlipFlopRoom> rooms;
    private static List<String> roomNames;
    private static int ROOM_NUMBER = 10;

    private static FlipFlopLobby lobby;

    /**
     * Getters
     * */
    public static List<FlipFlopRoom> getRooms(){
        return FlipFlopLobby.rooms;
    }

    public static List<String> getRoomNames(){
        return FlipFlopLobby.rooms.stream()
                .map(room -> room.getRoomName())
                .collect(Collectors.toList());
    }

    public static List<String> getFreeRoomKeys(){
        return FlipFlopLobby.getRooms().stream()
                .filter(room -> room.isFree())
                .map(room -> room.getRoomName())
                .collect(Collectors.toList());
    }

    public static FlipFlopRoom getRoom(String roomKey){
        if (FlipFlopLobby.getRoomNames().contains(roomKey)) {
            return FlipFlopLobby.getRooms().stream()
                    .filter(room -> room.getRoomName().equals(roomKey))
                    .findFirst()
                    .get();
        } else {
            return null;
        }
    }

    /**
     * Getter ends
     * */

    /**
     * Setters
     * */
    public String createRoom(){
        return null;
    }
    /**
     * Setter ends
     * */


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
    }

    /**
    public FlipFlopRoom getRoom(String roomName) {
        List<FlipFlopRoom> roomWithSameName = FlipFlopLobby.rooms.stream().filter(r -> r.getRoomName().equals(roomName)).collect(Collectors.toList());
        if(roomWithSameName.isEmpty()){
            return null;
        }

        FlipFlopRoom room = roomWithSameName.get(0);
        return room;
    }
     */

    public static void main(String args[]){
        RoomActionService roomActionManager = new RoomActionService();
        LobbyActionService lobbyActionManager = new LobbyActionService();
        BoardActionService boardActionService = new BoardActionService();

        FlipFlopRoomLobby flipFlopRoomLobby = FlipFlopRoomLobby.getInstance();
        User u1 = new User("LUC");
        User u2 = new User("LUC2");

        String roomKey1 = lobbyActionManager.joinRoom(u1);
        String roomKey2 = lobbyActionManager.joinRoom(u2, roomKey1);

        Board board = FlipFlopRoomLobby.getInstance().getRoom(roomKey1).getBoard();
        FlipFlopRoom room =FlipFlopRoomLobby.getInstance().getRoom(roomKey1);
        BoardObject boardObject = roomActionManager.getNewBoard(roomKey1);
        boardActionService.click(roomKey1, 1, 1);
        boardActionService.click(roomKey1, 1, 2);
        boardActionService.click(roomKey1, 2, 1);
        boardActionService.click(roomKey1, 2, 2);
        boardActionService.click(roomKey1, 3, 1);
        boardActionService.click(roomKey1, 3, 2);

        roomActionManager.leave(u1, roomKey2);
        roomActionManager.leave(u2, roomKey2);
        roomActionManager.leave(u2, roomKey2);
        System.out.println("aaa");



    }

}
