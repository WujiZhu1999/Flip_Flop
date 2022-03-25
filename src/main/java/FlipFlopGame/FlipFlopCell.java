package FlipFlopGame;

public class FlipFlopCell {
    private final int imageId;
    private CellState cellState;
    public static enum CellState{
        FLIPPED,
        UNFLIPPEDE,
        TMPFLIPPED,
        UNUSED
    }

    public FlipFlopCell(int imageId, boolean using) {
        this(imageId, using ? CellState.UNFLIPPEDE : CellState.UNUSED);
    }

    private FlipFlopCell(int imageId, CellState state) {
        this.imageId = imageId;
        this.cellState = state;
    }

    public void flip() {
        if (this.cellState.equals(CellState.UNFLIPPEDE)) {
            this.cellState = CellState.TMPFLIPPED;
        }
    }

    public void flipDone() {
        if (this.cellState.equals(CellState.TMPFLIPPED)) {
            this.cellState = CellState.FLIPPED;
        }
    }

    public void flipBack() {
        if (this.cellState.equals(CellState.TMPFLIPPED)) {
            this.cellState = CellState.UNFLIPPEDE;
        }
    }

    public int getImage() {
        return this.imageId;
    }

    public CellState getCellState() {
        return this.cellState;
    }
}
