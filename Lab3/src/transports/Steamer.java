package transports;

import utilities.Location;
import utilities.TypeTrans;

import java.util.Objects;

public class Steamer implements ITransActions {
    private TypeTrans type;
    private Location location;

    public Steamer() {}
    public Steamer(TypeTrans type, int coorX, int coorY) {
        this.type = type;
        this.location = new Location(coorX, coorY);
    }

    @Override
    public String toString() {
        return "The steamer set sail!";
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Steamer)) return false;
        return this.type.equals(((Steamer) obj).type) && this.location.equals(((Steamer) obj).location);
    }
    @Override
    public void getDescribe() {
        System.out.println("It has a big pipe");
    }

    @Override
    public void ringTheBell(String notice) {
        System.out.println("The steamer is clanging");
    }

    @Override
    public void emitTheSmoke() {
        System.out.println("The steamer is emitting smoke");
    }

    @Override
    public void move(String destination) {
        System.out.println("The steamer is moving to " + destination);
    }
    public Location getLocation() {
        return location;
    }

    public void changeLocation(int x, int y) {
        location.modify(x, y);
    }

}
