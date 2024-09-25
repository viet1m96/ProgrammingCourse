package pokemons;

import attacks.Confide;
import attacks.MegaDrain;
import attacks.RazorLeaf;
import attacks.SludgeBomb;
import ru.ifmo.se.pokemon.Type;

public class Roserade extends Roselia {
    public Roserade(String name, int level) {
        super(name, level);
        this.setType(Type.GRASS, Type.POISON);
        this.setStats(60, 70, 65, 125, 105, 90);
        this.setMove(new MegaDrain(), new Confide(), new RazorLeaf(), new SludgeBomb());
    }
}
