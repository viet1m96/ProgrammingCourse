package pokemons;

import attacks.AquaJet;
import attacks.CalmMind;
import attacks.TailWhip;
import attacks.WaterFall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

import javax.sound.sampled.AudioFileFormat;

public class GoldDuck extends PsyDuck {
    public GoldDuck(String name, int level) {
        super(name, level);
        this.setType(Type.WATER);
        this.setStats(80, 82, 78, 95, 80, 85);
        this.setMove(new WaterFall(), new CalmMind(), new TailWhip(), new AquaJet());
    }
}
