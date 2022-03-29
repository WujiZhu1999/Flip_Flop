package FlipFlop.flip.flop.Models;

/**
 * Used to store all information about an Image, for current state just store it with a static path
 *
 */
public class Image {
    private enum type {
        STATIC,
        AWS_S3,
        OTHER
    }

    private final String path;

    public Image(String imgPath) {
        this.path = imgPath;
    }

    public String getPath() {
        return path;
    }
}