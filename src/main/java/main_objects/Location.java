package main_objects;

import java.util.Objects;

public class Location {
    private long x;
    private int y;
    private int z;
    private String name;

    public Location() {
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
        if(name == null) return "null";
        return name;
    }

    public void setX(long x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if(name == null) {
            return "(" + x + ", " + y + ", " + z + ")";
        }

        return "(" + x + ", " + y + ", " + z + ")" + " at " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return x == location.x && y == location.y && z == location.z && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}