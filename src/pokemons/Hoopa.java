package pokemons;

import attacks.NastyPlot;
import attacks.PsyBeam;
import attacks.ThunderWave;
import attacks.ZenHeadbutt;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Hoopa extends Pokemon {
    public Hoopa(String name, int level) {
        super(name, level);
        this.setStats(80, 110, 60, 150, 130, 70);
        this.setType(Type.PSYCHIC, Type.GHOST);
        this.setMove(new ZenHeadbutt(), new PsyBeam(), new ThunderWave(), new NastyPlot());
    }
}