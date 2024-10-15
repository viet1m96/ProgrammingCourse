package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {
    private int x;
    private int y;

    @Override
    public String toString() {
        return "Location [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Location)) return false;
        return this.x == ((Location) obj).x && this.y == ((Location) obj).y;
    }

    public Location() {}
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void modify(int bx, int by) {
        this.x += bx;
        this.y += by;
    }


}
