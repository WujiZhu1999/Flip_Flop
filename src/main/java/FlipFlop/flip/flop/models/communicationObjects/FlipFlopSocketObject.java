package FlipFlop.flip.flop.models.communicationObjects;

public class FlipFlopSocketObject {
    private BoardObject boardObject;
    private BoardUpdateObject boardUpdateObject;
    private Boolean gameOver;

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

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
