package FlipFlop.flip.flop.models.communicationObjects;

import FlipFlop.flip.flop.models.flipFlopGameObjects.Image;

public class CellObject {
    private Image image;
    private int x;
    private int y;

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellObject(Image image, int x, int y) {
        this.image = image.copy();
        this.x = x;
        this.y = y;
    }
}
