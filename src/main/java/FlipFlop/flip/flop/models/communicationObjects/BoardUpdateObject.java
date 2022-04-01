package FlipFlop.flip.flop.models.communicationObjects;

import java.util.ArrayList;
import java.util.List;

public class BoardUpdateObject {
    private List<CellObject> cells;

    public BoardUpdateObject() {
        this.cells = new ArrayList<>();
    }

    public List<CellObject> getCells() {
        return cells;
    }

    public void setCells(CellObject cell) {
        this.cells.add(cell);
    }
}
