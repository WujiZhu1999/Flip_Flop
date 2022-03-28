package FlipFlop.flip.flop;

import FlipFlopGame.FlipFlopBoard;
import FlipFlopGame.FlipFlopBoardFactory;

import java.util.Objects;

public class FlipFlopB {
    private static FlipFlopBoard board= null;

    public static void restart(){
        FlipFlopB.board = null;
    }

    public static FlipFlopBoard getBoard(){
        if (Objects.isNull(FlipFlopB.board)) {

            FlipFlopB.board = new FlipFlopBoard(6,6,2);
            FlipFlopB.board.addImage("/image/01.png");
            FlipFlopB.board.addImage("/image/02.png");
            FlipFlopB.board.addImage("/image/03.png");
            FlipFlopB.board.addImage("/image/04.png");
            FlipFlopB.board.addImage("/image/06.png");
            FlipFlopB.board.addImage("/image/07.png");
            FlipFlopB.board.addImage("/image/08.png");
            FlipFlopB.board.addImage("/image/09.png");
            FlipFlopB.board.addImage("/image/10.png");

            FlipFlopB.board.generateBoard();

        }

        return board;
    }
}
