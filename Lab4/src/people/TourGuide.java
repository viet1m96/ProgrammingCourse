package people;

import utilities.Location;
import utilities.Occupation;

import java.util.Objects;

public class TourGuide extends Human implements TourGuideActions {
    private String name;
    private int age;
    private Location location;
    private Occupation occupation;
    public TourGuide() {}
    public TourGuide(String name, int age, Occupation occu, int coorX, int coorY) {
        this.name = name;
        this.age = age;
        this.location = new Location(coorX, coorY);
        occupation = occu;
        System.out.print(this);
        System.out.print("Hello everyone! My name is " + name + " I am " + age + " yrs old.");
        System.out.println("Today I am your tour guide.");
    }
    @Override
    public String toString() {
        return "Tour Guide: ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, occupation, location);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Human)) return false;
        return this.name.equals(((TourGuide) obj).name) && this.age == ((TourGuide) obj).age && this.location.equals(((TourGuide) obj).location) && this.occupation.equals(((TourGuide) obj).occupation);
    }

    @Override
    public void point(String destination) {
        System.out.print(this);
        System.out.println("That is " + destination);
    }

    @Override
    public void explain(String script) {
        System.out.print(this);
        System.out.println(script);
    }

    public Location getLocation() {
        return location;
    }

    public void changeLocation(int x, int y) {
        location.modify(x, y);
    }
}