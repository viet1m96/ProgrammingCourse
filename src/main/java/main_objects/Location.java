package main_objects;

/**
 * The {@code Location} class represents a three-dimensional location with a name.
 * It encapsulates the x, y, and z coordinates, as well as a name for the location.
 */
public class Location {
    private long x;
    private int y;
    private int z;
    private String name;

    /**
     * Constructs a new {@code Location} object with the specified coordinates and name.
     *
     * @param x    The x-coordinate of the location.
     * @param y    The y-coordinate of the location.
     * @param z    The z-coordinate of the location.
     * @param name The name of the location.
     */
    public Location(long x, int y, int z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    /**
     * Returns the x-coordinate of the location as a String.
     *
     * @return The x-coordinate as a String.
     */
    public String getX() {
        return String.valueOf(x);
    }

    /**
     * Returns the y-coordinate of the location as a String.
     *
     * @return The y-coordinate as a String.
     */
    public String getY() {
        return String.valueOf(y);
    }

    /**
     * Returns the z-coordinate of the location as a String.
     *
     * @return The z-coordinate as a String.
     */
    public String getZ() {
        return String.valueOf(z);
    }

    /**
     * Returns the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the {@code Location} object in the format "(x, y, z) at name".
     *
     * @return A string representation of the location.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")" + " at " + name;
    }
}