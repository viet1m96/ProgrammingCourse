package places;

import utilities.Location;
import utilities.TypeCons;

import java.util.Objects;

public class Bridge implements IPlaceActions {
    private Location location;
    private TypeCons type;

    public Bridge(){}
    public Bridge(int coorX, int coorY, TypeCons type){
        location = new Location(coorX, coorY);
        this.type = type;
    }

    @Override
    public void getDescribe() {
        System.out.println("The bridge was thrown over the water from one bank to the other");
    }

    @Override
    public String toString() {
        return "Bridge [location=" + location + ", type=" + type + "]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(location, type);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Bridge)) return false;
        return this.location.equals(((Bridge)obj).location) && this.type.equals(((Bridge)obj).type);
    }

    public Location getLocation() {
        return location;
    }

}
