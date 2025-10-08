package transports;

import exceptions.NotEnoughFuel;
import utilities.Location;
import utilities.Parameters;
import utilities.TypeTrans;
import utilities.level;

import java.util.Objects;
import java.util.Random;

public class Steamer extends Transports {
    private TypeTrans type;
    private Location location;
    private Parameters parameters;

    public Steamer() {}
    public Steamer(TypeTrans type, int coorX, int coorY, int acceleration, int oilAssumption) {
        this.type = type;
        this.location = new Location(coorX, coorY);
        this.parameters = new Parameters(acceleration, oilAssumption, 50);
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

    public void changeLocation(int x, int y) {
        location.modify(x, y);
    }

    public void changeSpeed(double t) {
        parameters.modifySpeed(t);
    }

    public void resetAccel(double maxSpeed, double distance) {
       parameters.setAcceleration(maxSpeed, distance);
    }

    public Location getLocation() {
        return location;
    }

    public double getSpeed() {
        return parameters.getSpeed();
    }
    public double getAcceleration() {
        return parameters.getAcceleration();
    }
}

