import java.awt.*;

public enum ColorSets{
    BROWN(new Color(139,69,19)),
    CYAN(Color.CYAN),
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE);

    private final Color color;

    ColorSets(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
