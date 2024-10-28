package people;

import utilities.Location;

import java.util.Objects;

public class Traveller extends Human{
    private Location location;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, this.getClass().getSimpleName());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Human)) return false;
        return this.location.equals(((Traveller) obj).getLocation());
    }

    public Traveller() {}
    public Traveller(int coorX, int coorY) {
        this.location = new Location(coorX, coorY);
    }

    public void takeAPhoto() {
        System.out.println("They are taking photos");
    }

    public void clapp() {
        System.out.println("yeahhhh");
        System.out.println("Travellers are clapping");
    }

    public void changeLocation(int x, int y) {
        location.modify(x, y);
    }

    public Location getLocation() {
        return location;
    }

}
