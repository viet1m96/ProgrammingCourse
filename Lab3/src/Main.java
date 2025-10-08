import people.MainCharacter;
import people.ITourGuide;
import people.Traveller;
import places.Bridge;
import places.River;
import places.Road;
import transports.Steamer;
import utilities.Occupation;
import utilities.TypeCons;
import utilities.TypeTrans;


public class Main {
    public static void main(String[] args) {
        ITourGuide tourGuide = new ITourGuide("John", 20, Occupation.TOURGUIDE, 0, 0);
        Traveller traveller = new Traveller(0, 0);
        MainCharacter mainCharacter = new MainCharacter("Button", 18, Occupation.STUDENT, 0, 0);
        Steamer steamer = new Steamer(TypeTrans.WATERFACE, 21, 10);
        Bridge bridge = new Bridge(21, 0, TypeCons.ARTIFICIAL);
        River river = new River(21, 0, TypeCons.NATURAL);
        Road road = new Road(20, 20, TypeCons.NATURAL);

        TheTravel theTravel = new TheTravel(tourGuide, traveller, mainCharacter, steamer, bridge, river, road);
        try {
            theTravel.letsgo();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}