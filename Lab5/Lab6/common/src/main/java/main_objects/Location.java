package main_objects;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Location} class represents a location in a three-dimensional space.
 * It stores the x, y, and z values as {@code long}, {@code int}, and {@code int} respectively,
 * and the name of the location as a {@code String}.
 */
public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private long x;
    private int y;
    private int z;
    private String name;

    /**
     * Constructs a new {@code Location} object with default values.
     */
    public Location() {
    }

    /**
     * Returns the x-coordinate as a {@code String}.
     *
     * @return The x-coordinate as a {@code String}.
     */
    public String getX() {
        return String.valueOf(x);
    }

    /**
     * Returns the y-coordinate as a {@code String}.
     *
     * @return The y-coordinate as a {@code String}.
     */
    public String getY() {
        return String.valueOf(y);
    }

    /**
     * Returns the z-coordinate as a {@code String}.
     *
     * @return The z-coordinate as a {@code String}.
     */
    public String getZ() {
        return String.valueOf(z);
    }

    /**
     * Returns the name of the location. If the name is {@code null}, it returns "null".
     *
     * @return The name of the location.
     */
    public String getName() {
        if (name == null) return "null";
        return name;
    }

    /**
     * Sets the x-coordinate of the location.
     *
     * @param x The x-coordinate to set.
     */
    public void setX(long x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the location.
     *
     * @param y The y-coordinate to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the z-coordinate of the location.
     *
     * @param z The z-coordinate to set.
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Sets the name of the location.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the {@code Location} object.
     * If the name is {@code null}, it returns the coordinates in the format "(x, y, z)".
     * Otherwise, it returns the coordinates and the name in the format "(x, y, z) at name".
     *
     * @return A string representation of the {@code Location} object.
     */
    @Override
    public String toString() {
        if (name == null) {
            return "(" + x + ", " + y + ", " + z + ")";
        }

        return "(" + x + ", " + y + ", " + z + ")" + " at " + name;
    }

    /**
     * Compares this {@code Location} object to another object for equality.
     *
     * @param o The object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return x == location.x && y == location.y && z == location.z && Objects.equals(name, location.name);
    }

    /**
     * Returns a hash code value for this {@code Location} object.
     *
     * @return A hash code value for this {@code Location} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
