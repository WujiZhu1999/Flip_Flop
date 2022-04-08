package FlipFlop.flip.flop.models.flipFlopGameObjects;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoardCellUnitTest {

    @Test
    void TestCellFlipFunction(){
        BoardCell cell = new BoardCell(0, true);

        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.UNFLIPPEDE;
        cell.flip();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.TMPFLIPPED;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

    @Test
    void TestCellFlipWhenAlreadyFlippedFunction(){
        BoardCell cell = new BoardCell(0, true);


        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.UNFLIPPEDE;
        cell.flip();
        cell.flip();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.TMPFLIPPED;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

    @Test
    void TestCellFlipDone(){
        BoardCell cell = new BoardCell(0, true);
        cell.flip();


        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.TMPFLIPPED;
        cell.flipDone();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.FLIPPED;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

    @Test
    void TestCellAlreadyFlipDone(){
        BoardCell cell = new BoardCell(0, true);
        cell.flip();


        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.TMPFLIPPED;
        cell.flipDone();
        cell.flip();
        cell.flipBack();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.FLIPPED;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

    @Test
    void TestCellFlipBack(){
        BoardCell cell = new BoardCell(0, true);
        cell.flip();


        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.TMPFLIPPED;
        cell.flipBack();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.UNFLIPPEDE;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

    @Test
    void TestCellMultipleFlipBack(){
        BoardCell cell = new BoardCell(0, true);
        cell.flip();


        BoardCell.CellState startState = cell.getCellState();
        BoardCell.CellState expectedStartState = BoardCell.CellState.TMPFLIPPED;
        cell.flipBack();
        cell.flipBack();

        BoardCell.CellState endState = cell.getCellState();
        BoardCell.CellState expectedEndState = BoardCell.CellState.UNFLIPPEDE;

        assertEquals(expectedStartState, startState);
        assertEquals(expectedEndState, endState);
    }

}
