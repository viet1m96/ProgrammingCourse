package pokemons;

import attacks.Confide;
import attacks.MegaDrain;
import attacks.RazorLeaf;
import ru.ifmo.se.pokemon.Type;

public class Roselia extends Budew{
    public Roselia(String name, int level) {
        super(name, level);
        this.setType(Type.GRASS, Type.POISON);
        this.setStats(50, 60, 45, 100, 80, 65);
        this.setMove(new MegaDrain(), new Confide(), new RazorLeaf());
    }
}
