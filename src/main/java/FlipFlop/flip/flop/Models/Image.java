package FlipFlop.flip.flop.Models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(path, image.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public Image copy() {
        return new Image(this.path);
    }
}