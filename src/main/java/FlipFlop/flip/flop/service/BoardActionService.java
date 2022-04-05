package FlipFlop.flip.flop.service;

import FlipFlop.flip.flop.models.communicationObjects.BoardUpdateObject;
import FlipFlop.flip.flop.models.communicationObjects.CellObject;
import FlipFlop.flip.flop.models.flipFlopGameObjects.*;

import java.util.List;
import java.util.Objects;

/**
 * Used to react to users' operation after game starts
 * 1. Click
 * 2. Check if game over
 * */

public class BoardActionService {

    //Click
    public BoardUpdateObject click(String roomKey, int x, int y, int version){

        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);

        if(Objects.isNull(room)){
            return null;
        }

        Board board = room.getBoard();

        if(board.getVersion()!=version){
            return null;
        }

        BoardCell cell = board.getCell(x,y);


        //should think of another way to deal with edge cases (click parallel)
        synchronized (board){
            if (Objects.isNull(cell) || !cell.getCellState().equals(BoardCell.CellState.UNFLIPPEDE)) {
                return null;
            }

            //if not, have 3 possibility
            /**
             * If not have 3 possibility:
             *   1. Flip Success, and nothing else
             *   2. Flip Failed, all flipped back
             *   3. Flip Success, all temp flipped mark as flipped done
             * */
            BoardUpdateObject updates = new BoardUpdateObject();

            //case 2
            if (board.getOnHoldImageId()!= -1 && board.getOnHoldImageId()!= cell.getImage()){
                board.setOnHoldImageId(-1);
                List<Board.Coor> onSelect = board.getOnSelect();
                Board.Coor coor = null;
                Image bgImage = board.getBackgroundImage();
                while (!onSelect.isEmpty()) {
                    coor = onSelect.remove(0);
                    board.getCell(coor.w, coor.h).flipBack();
                    updates.setCells(new CellObject(bgImage, coor.w, coor.h));
                }
                return updates;
            }

            cell.flip();
            board.setOnHoldImageId(cell.getImage());
            board.addOnSelect(new Board.Coor(x, y));

            //case 1
            if (board.getOnSelect().size() < board.getFlipCount()) {
                updates.setCells(new CellObject(board.getImage(cell.getImage()),x,y));
                return updates;
            }

            if (board.getOnSelect().size() >= board.getFlipCount()) {
                board.setOnHoldImageId(-1);
                List<Board.Coor> onSelect = board.getOnSelect();
                Board.Coor coor = null;
                while (!onSelect.isEmpty()) {

                    coor = onSelect.remove(0);
                    board.getCell(coor.w, coor.h).flipDone();
                    board.flipOneMore();

                    //Only the one submitted this time needs to get updated
                    //updates.setCells(new CellObject(board.getImage(board.getCell(coor.w,coor.h).getImage()), coor.w, coor.h));
                }
                updates.setCells(new CellObject(board.getImage(cell.getImage()),x,y));
                return updates;
            }

        }


        return null;

    }

    public boolean gameOver(String roomKey){
        FlipFlopRoom room = FlipFlopRoomLobby.getInstance().getRoom(roomKey);

        if(Objects.isNull(room)){
            return false;
        }

        Board board = room.getBoard();
        return board.gameOver();
    }
}
