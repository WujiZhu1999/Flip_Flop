package FlipFlop.flip.flop.models.flipFlopGameObjects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardFactoryUnitTest {

    @Test
    void testSetupEasyBoard(){
        Board board = new Board();
        BoardFactory.setupBoard(board, "easy");

        int expectedBoardSize = 4;
        int expectedFlipCount = 2;

        assertEquals(expectedBoardSize ,board.getBoardHeight());
        assertEquals(expectedBoardSize ,board.getBoardWidth());
        assertEquals(expectedFlipCount ,board.getFlipCount());
        testInitial(board);

    }

    @Test
    void testSetupNormalBoard(){
        Board board = new Board();
        BoardFactory.setupBoard(board, "normal");

        int expectedBoardSize = 6;
        int expectedFlipCount = 2;

        assertEquals(expectedBoardSize ,board.getBoardHeight());
        assertEquals(expectedBoardSize ,board.getBoardWidth());
        assertEquals(expectedFlipCount ,board.getFlipCount());
        testInitial(board);
    }

    @Test
    void testSetupHardBoard(){
        Board board = new Board();
        BoardFactory.setupBoard(board, "hard");

        int expectedBoardSize = 8;
        int expectedFlipCount = 2;

        assertEquals(expectedBoardSize ,board.getBoardHeight());
        assertEquals(expectedBoardSize ,board.getBoardWidth());
        assertEquals(expectedFlipCount ,board.getFlipCount());
        testInitial(board);
    }

    @Test
    void testSetupInsaneBoard(){
        Board board = new Board();
        BoardFactory.setupBoard(board, "insane");

        int expectedBoardSize = 10;
        int expectedFlipCount = 5;

        assertEquals(expectedBoardSize ,board.getBoardHeight());
        assertEquals(expectedBoardSize ,board.getBoardWidth());
        assertEquals(expectedFlipCount ,board.getFlipCount());
        testInitial(board);

    }

    @Test
    void testSetupHaBoard(){
        Board board = new Board();
        BoardFactory.setupBoard(board, "ha");

        int expectedBoardSize = 12;
        int expectedFlipCount = 8;

        assertEquals(expectedBoardSize ,board.getBoardHeight());
        assertEquals(expectedBoardSize ,board.getBoardWidth());
        assertEquals(expectedFlipCount ,board.getFlipCount());
        testInitial(board);

    }

    /**
     * Iterate all cells of a board and make sure it is not flipped
     * */
    private void testInitial(Board board){
        for(int i = 0; i < board.getBoardWidth(); i++){
            for(int j = 0; j < board.getBoardHeight(); j++){
                assertEquals(BoardCell.CellState.UNFLIPPEDE, board.getCell(i,j).getCellState());
            }
        }

    }
}
