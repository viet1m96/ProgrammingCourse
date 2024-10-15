package places;

import utilities.Location;
import utilities.TypeCons;

public class River implements IPlaceActions {
    private Location location;
    private TypeCons type;

    public River() {}
    public River(int coorX, int coorY, TypeCons type) {
        this.location = new Location(coorX, coorY);
        this.type = type;
    }
    @Override
    public String toString() {
        return "River [location=" + location + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof River)) return false;
        return this.location.equals(((River)obj).location) && this.type.equals(((River)obj).type);
    }


    @Override
    public void getDescribe() {
        System.out.println("The long river, its surface sparkling, lies under the bridge.");
    }

    public void wave(level lv) {
        System.out.println("The waves lap the shoreline " + lv.toString().toLowerCase());
    }

    public Location getLocation() {
        return location;
    }


}

