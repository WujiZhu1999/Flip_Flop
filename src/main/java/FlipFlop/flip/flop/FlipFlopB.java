package FlipFlop.flip.flop;

import FlipFlopGame.FlipFlopBoard;
import FlipFlopGame.FlipFlopBoardFactory;

import java.util.Objects;

public class FlipFlopB {
    private static FlipFlopBoard board= null;

    public static FlipFlopBoard getBoard(){
        if (Objects.isNull(FlipFlopB.board)) {

            FlipFlopB.board = new FlipFlopBoard(4,4,2);
            FlipFlopB.board.addImage("a");
            FlipFlopB.board.addImage("b");
            FlipFlopB.board.addImage("c");

            FlipFlopB.board.generateBoard();

        }

        return board;
    }
}
