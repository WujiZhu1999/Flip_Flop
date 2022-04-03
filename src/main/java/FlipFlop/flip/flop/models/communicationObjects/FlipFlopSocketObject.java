package FlipFlop.flip.flop.models.communicationObjects;

public class FlipFlopSocketObject {
    private BoardObject boardObject;
    private BoardUpdateObject boardUpdateObject;

    public BoardObject getBoardObject() {
        return boardObject;
    }

    public void setBoardObject(BoardObject boardObject) {
        this.boardObject = boardObject;
    }

    public BoardUpdateObject getBoardUpdateObject() {
        return boardUpdateObject;
    }

    public void setBoardUpdateObject(BoardUpdateObject boardUpdateObject) {
        this.boardUpdateObject = boardUpdateObject;
    }
}
