package main_objects;

import java.util.Objects;

/**
 * The {@code Coordinates} class represents the coordinates of a location in a two-dimensional space.
 * It stores the x and y values as {@code Integer} objects.
 */
public class Coordinates {
    private final Integer x;
    private final Integer y;

    /**
     * Constructs a new {@code Coordinates} object with the specified x and y values.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate as a {@code String}.
     *
     * @return The x-coordinate as a {@code String}.
     */
    public String getX() {
        return x.toString();
    }

    /**
     * Returns the y-coordinate as a {@code String}.
     *
     * @return The y-coordinate as a {@code String}.
     */
    public String getY() {
        return y.toString();
    }

    /**
     * Returns a string representation of the {@code Coordinates} object in the format "(x, y)".
     *
     * @return A string representation of the {@code Coordinates} object.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares this {@code Coordinates} object to another object for equality.
     *
     * @param o The object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates that)) return false;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    /**
     * Returns a hash code value for this {@code Coordinates} object.
     *
     * @return A hash code value for this {@code Coordinates} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
