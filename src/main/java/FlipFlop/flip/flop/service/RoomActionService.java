package FlipFlop.flip.flop.service;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import FlipFlop.flip.flop.models.communicationObjects.CellObject;
import FlipFlop.flip.flop.models.flipFlopGameObjects.*;

import java.util.Objects;

/**
 * React users' action in room level
 * 1. Click to leave the room
 * 2. Click to refresh/start the board
 * */
public class RoomActionService {

    /**
     * handle user leave room action
     * */
    public void leave(User user, String roomKey){
        FlipFlopRoomLobby lobby = FlipFlopRoomLobby.getInstance();

        FlipFlopRoom room = lobby.getRoom(roomKey);

        if(Objects.isNull(room)) {
            return;
        }

        room.leave(user);
        if (room.isEmpty()) {
            lobby.removeRoom(room);
        }
    }

    /**
     * handle user refresh/start button board action
     * */
    public BoardObject getNewBoard(String roomKey){
        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);

        if(Objects.isNull(room)){
            return null;
        }

        Board board = room.getBoard();
        BoardFactory.setupBoard(board, "easy");

        int width = board.getBoardWidth();
        int height = board.getBoardHeight();

        BoardObject boardObject = new BoardObject();
        for (int x = 0; x < width; x++){
            boardObject.addRows();
            for (int y = 0; y < height; y++){
                BoardCell cell = board.getCell(x,y);
                boardObject.addCells(new CellObject(board.getImage(cell.getImage()), x, y));
            }
        }

        return boardObject;

    }

}
