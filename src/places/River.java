package places;

import utilities.Location;
import utilities.TypeCons;
import utilities.level;

import java.util.Objects;

public class River implements PlaceActions {
    private TypeCons type;
    private int width;
    private int length;
    private String name;

    public River() {}
    public River(String name, int width, int length, TypeCons type) {
        this.width = width;
        this.length = length;
        this.type = type;
    }
    @Override
    public String toString() {
        return "Bridge [name= " + name + ", width= " + width + ", length= " + length + ", type= " + type + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, width, length, name);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof River)) return false;
        return this.name.equals(((River)obj).name) && this.width == ((River)obj).width && this.length == ((River)obj).length;
    }


    @Override
    public void getDescribe() {
        System.out.println("The long river, its surface sparkling, lies under the bridge.");
    }

    public void wave(level lv) {
        System.out.println("The waves lap the shoreline " + lv.toString().toLowerCase());
    }


}

