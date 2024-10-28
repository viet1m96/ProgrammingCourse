import people.MainCharacter;
import people.TourGuide;
import people.Traveller;
import places.Bridge;
import places.River;
import places.Road;
import transports.Steamer;
import utilities.Occupation;
import utilities.TypeCons;
import utilities.TypeTrans;

import java.io.PrintStream;


public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println(x);
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        TourGuide tourGuide = new TourGuide("John", 20, Occupation.TOURGUIDE, 0, -10);
        Traveller traveller = new Traveller(0, -10);
        MainCharacter mainCharacter = new MainCharacter("Button", 18, Occupation.STUDENT, 0, -10);
        Steamer steamer = new Steamer(TypeTrans.WATERFACE, 50, 0, 10, 20);
        Bridge bridge = new Bridge(0, 0, TypeCons.ARTIFICIAL);
        River river = new River("Red", 20, 100, TypeCons.NATURAL);
        Road road = new Road(20, 20, TypeCons.NATURAL);

        Travel travel = new Travel(tourGuide, traveller, mainCharacter, steamer, bridge, river, road);
        travel.letsgo();
    }
}