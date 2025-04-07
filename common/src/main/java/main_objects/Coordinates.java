package main_objects;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Coordinates} class represents the coordinates of a location in a two-dimensional space.
 * It stores the x and y values as {@code Integer} objects.
 */
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private Integer id;
    private Integer x;
    private Integer y;


    public Coordinates() {}
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Returns the x-coordinate as a {@code String}.
     *
     * @return The x-coordinate as a {@code String}.
     */
    public Integer getX() {
        return x;
    }

    /**
     * Returns the y-coordinate as a {@code String}.
     *
     * @return The y-coordinate as a {@code String}.
     */
    public Integer getY() {
        return y;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId () {
        return id;
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
