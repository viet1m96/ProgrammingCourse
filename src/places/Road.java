package places;

import utilities.TypeCons;

import java.util.Objects;

public class Road implements IPlaceActions {
    private int width;
    private int height;
    private TypeCons type;

    public Road() {}
    public Road(int width, int height, TypeCons type) {
        this.width = width;
        this.height = height;
        this.type = type;
    }
    @Override
    public String toString() {
        return "Road [width=" + width + ", height=" + height + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Road)) return false;
        return this.width == ((Road) obj).width && this.height == ((Road) obj).height && this.type == ((Road) obj).type;
    }


    @Override
    public void getDescribe() {
        System.out.println("The road wound between the hills, sometimes through a forest, sometimes through a field");
    }
}
