package main_objects;

public class Location {
    private long x;
    private int y;
    private int z;
    private String name;

    public Location(long x, int y, int z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public String getX() {
        return String.valueOf(x);
    }
    public String getY() {
        return String.valueOf(y);
    }
    public String getZ() {
        return String.valueOf(z);
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")" + " at " + name;
    }
}
