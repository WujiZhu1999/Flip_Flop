package FlipFlop.flip.flop.service;

import FlipFlop.flip.flop.models.communicationObjects.BoardObject;
import FlipFlop.flip.flop.models.communicationObjects.CellObject;
import FlipFlop.flip.flop.models.flipFlopGameObjects.*;

import java.util.Objects;

/**
 * React users' action in room level
 * 1. Click to leave the room
 * 2. Click to refresh/start the board
 * 3. When join if game already start then load new board
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

        return getBoard(board);

    }

    /**
     * handle get board option only
     */
    public BoardObject getCurrentBoard(String roomKey){
        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);

        if(Objects.isNull(room)){
            return null;
        }

        Board board = room.getBoard();

        return getBoard(board);
    }
    /**
     * Check if game started already
     * */
    public Boolean gameIsStart(String roomKey){
        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);

        if(Objects.isNull(room)){
            return false;
        }

        Board board = room.getBoard();
        return (board.getBoardHeight() != -1) && (board.getBoardWidth() != -1);
    }

    /**
     * Transfer Board to BoardObject
     * */
    private BoardObject getBoard(Board board){
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

        boardObject.setVersion(board.getVersion());

        return boardObject;

    }
}
