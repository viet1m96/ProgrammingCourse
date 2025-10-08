import animals.Animal;
import exceptions.OverSpeed;
import people.MainCharacter;
import people.TourGuide;
import people.Traveller;
import places.Bridge;
import places.River;
import places.Road;
import transports.Steamer;
import utilities.Location;
import utilities.level;

import java.util.ArrayList;
import java.util.Objects;

public class Travel {
    private final TourGuide tourGuide;
    private final Traveller travellers;
    private final MainCharacter Button;
    private final Steamer steamer;
    private final Bridge bridge;
    private final River river;
    private final Road road;
    private final ArrayList<Animal> animals;
    @Override
    public String toString() {
        return "The Travel was started!";
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourGuide, travellers, steamer, bridge, road, river);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Travel)) return false;
        return this.tourGuide == ((Travel) obj).tourGuide && this.travellers == ((Travel) obj).travellers;
    }

    public Travel(TourGuide tourGuide, Traveller travellers, MainCharacter Button, Steamer steamer, Bridge bridge, River river, Road road) {
        animals = new ArrayList<>();
        animals.add(new Animal("Birds", " are flying around."));
        animals.add(new Animal("Fishes", " are swimming around."));
        animals.add(new Animal("Rabbits", " are running around"));
        this.tourGuide = tourGuide;
        this.travellers = travellers;
        this.Button = Button;
        this.steamer = steamer;
        this.bridge = bridge;
        this.river = river;
        this.road = road;
    }

    public void letsgo() {
        travellers.clapp();
        System.out.println(this);
        road.getDescribe();
        animals.forEach(Animal::move);
        tourGuide.speak("Along this road, we will see many beautiful scenes.");
        tourGuide.speak("At the end of this road is a river");
        travellers.move(" the river");
        travellers.takeAPhoto();
        while(!travellers.getLocation().equals(bridge.getLocation()) && !Button.getLocation().equals(bridge.getLocation())   && !tourGuide.getLocation().equals(bridge.getLocation())) {
            travellers.changeLocation(0, 1);
            Button.changeLocation(0, 1);
            tourGuide.changeLocation(0, 1);
        }
        travellers.stop();
        river.getDescribe();
        bridge.getDescribe();
        steamer.ringTheBell("Clang clang clang...!!!");
        steamer.emitTheSmoke();
        steamer.move("the bridge");
        double t = 0;
        while(!steamer.getLocation().equals(bridge.getLocation())) {
            t += 0.1;
            steamer.changeLocation(-1, 0);
            steamer.changeSpeed(t);
            if(steamer.getLocation().equals(new Location(25, 0))) {
                try {
                    if(steamer.getSpeed() > 20) throw new OverSpeed("THE SPEED IS OVER THE LIMITED ONE!");
                } catch (OverSpeed e) {
                    System.out.println();
                    System.out.println(e.getMessage());
                    steamer.resetAccel(20, 25);
                }
            }
            if(steamer.getLocation().equals(bridge.getLocation()) && steamer.getAcceleration() < 0) {
                steamer.resetAccel(50, 50);
                System.out.println();
            }
        }
        river.wave(level.LIGHTLY);
        Button.scream("excited");
        Button.speak("This is my first time to see a steamer!");
        
        steamer.getDescribe();
        Button.recall("Steamboats did not sail along the Cucumber River. \nShe had only often seen it in pictures in books.");
    }

}
