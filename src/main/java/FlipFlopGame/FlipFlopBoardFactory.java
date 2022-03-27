package FlipFlopGame;

import java.util.Objects;

public class FlipFlopBoardFactory {
    private static FlipFlopBoard board= null;

    public static FlipFlopBoard getBoard(){
        if (Objects.isNull(FlipFlopBoardFactory.board)) {
            FlipFlopBoardFactory.board = new FlipFlopBoard(4,4,2);
        }

        return board;
    }
}
