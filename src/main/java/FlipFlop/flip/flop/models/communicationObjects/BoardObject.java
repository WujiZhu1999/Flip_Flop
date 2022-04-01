package FlipFlop.flip.flop.models.communicationObjects;

import FlipFlop.flip.flop.models.flipFlopGameObjects.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardObject {

    private List<List<CellObject>> cells;

    public List<List<CellObject>> getCells() {
        return cells;
    }

    public void addCells(CellObject cellObject) {
        this.cells.get(cells.size()-1).add(cellObject);
    }

    public void addRows() {
        if (Objects.isNull(this.cells)){
            this.cells = new ArrayList<>();
        }
        this.cells.add(new ArrayList<>());
    }
}
