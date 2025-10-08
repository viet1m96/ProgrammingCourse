package pokemons;

import attacks.CalmMind;
import attacks.TailWhip;
import attacks.WaterFall;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class PsyDuck extends Pokemon {
    public PsyDuck(String name, int level) {
        super(name, level);
        this.setStats(50, 52, 48, 65, 50, 55);
        this.setType(Type.WATER);
        this.setMove(new WaterFall(), new TailWhip(), new CalmMind());
    }
}
