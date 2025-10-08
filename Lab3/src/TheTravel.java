import people.MainCharacter;
import places.level;
import people.ITourGuide;
import people.Traveller;
import places.Bridge;
import places.River;
import places.Road;
import transports.Steamer;

import java.util.Objects;

public class TheTravel {
    private ITourGuide tourGuide;
    private Traveller travellers;
    private MainCharacter Button;
    private Steamer steamer;
    private Bridge bridge;
    private River river;
    private Road road;

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
        if(!(obj instanceof TheTravel)) return false;
        return this.tourGuide == ((TheTravel) obj).tourGuide && this.travellers == ((TheTravel) obj).travellers;
    }

    public TheTravel(ITourGuide tourGuide, Traveller travellers, MainCharacter Button, Steamer steamer, Bridge bridge, River river, Road road) {
        this.tourGuide = tourGuide;
        this.travellers = travellers;
        this.Button = Button;
        this.steamer = steamer;
        this.bridge = bridge;
        this.river = river;
        this.road = road;
    }

    public void letsgo() throws InterruptedException {
        travellers.clapp();
        System.out.println(this);
        road.getDescribe();
        Thread.sleep(1000);
        tourGuide.speak("Along this road, we will see many beautiful scenes.");
        tourGuide.speak("At the end of this road is a river");
        travellers.move(" the river");
        Thread.sleep(1000);
        travellers.takeAPhoto();
        Thread.sleep(1000);
        while(!travellers.getLocation().equals(river.getLocation()) && !Button.getLocation().equals(river.getLocation())   && !tourGuide.getLocation().equals(river.getLocation())) {
            travellers.changeLocation(1, 0);
            Button.changeLocation(1, 0);
            tourGuide.changeLocation(1, 0);
        }
        travellers.stop();
        river.getDescribe();
        bridge.getDescribe();
        steamer.ringTheBell("Clang clang clang...!!!");
        steamer.emitTheSmoke();
        steamer.move("the bridge");
        Thread.sleep(1000);
        while(!steamer.getLocation().equals(river.getLocation())) {
            steamer.changeLocation(0, -1);
        }
        river.wave(level.LIGHTLY);
        Button.scream("excited");
        Button.speak("This is my first time to see a steamer!");
        Thread.sleep(1000);
        steamer.getDescribe();
        Button.recall("Steamboats did not sail along the Cucumber River. \nShe had only often seen it in pictures in books.");
    }

}
