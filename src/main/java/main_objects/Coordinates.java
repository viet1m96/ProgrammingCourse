package main_objects;

import java.util.Objects;

/**
 * The {@code Coordinates} class represents a point in a two-dimensional space with integer coordinates.
 * It encapsulates the x and y coordinates of a point.
 */
public class Coordinates {
    private Integer x;
    private Integer y;

    /**
     * Constructs a new {@code Coordinates} object with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the point as a String.
     *
     * @return The x-coordinate as a String.
     */
    public String getX() {
        return x.toString();
    }

    /**
     * Returns the y-coordinate of the point as a String.
     *
     * @return The y-coordinate as a String.
     */
    public String getY() {
        return y.toString();
    }

    /**
     * Returns a string representation of the {@code Coordinates} object in the format "(x, y)".
     *
     * @return A string representation of the coordinates.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates that)) return false;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}