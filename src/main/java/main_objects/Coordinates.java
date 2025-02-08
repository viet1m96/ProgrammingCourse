package main_objects;

public class Coordinates {
    private Integer x;
    private Integer y;

    Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
    public String getX() {
        return x.toString();
    }
    public String getY() {
        return y.toString();
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
