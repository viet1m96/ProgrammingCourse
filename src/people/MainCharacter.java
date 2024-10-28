package people;

import utilities.Location;
import utilities.Occupation;

import java.util.Objects;

public class MainCharacter extends Human{
    private String name;
    private int age;
    private Occupation state;
    private Location location;

    public MainCharacter(){}
    public MainCharacter(String name, int age, Occupation state, int coorX, int coorY) {
        this.name = name;
        this.age = age;
        this.state = state;
        this.location = new Location(coorX, coorY);
    }

    @Override
    public String toString() {
        return name + ": ";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, state, location);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Human)) return false;
        return this.name.equals(((MainCharacter) obj).name) && this.age == ((MainCharacter) obj).age && this.location.equals(((MainCharacter) obj).location);
    }

    public void recall(String memorize) {
        System.out.print(name + " remembers that ");
        System.out.println(memorize);
    }
    public void scream(String emotion) {
        System.out.println(name + " is " + emotion);
    }

    public void changeLocation(int x, int y) {
        location.modify(x, y);
    }

    public Location getLocation() {
        return location;
    }
}
