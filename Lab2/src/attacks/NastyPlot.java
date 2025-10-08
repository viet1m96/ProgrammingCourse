package attacks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class NastyPlot extends StatusMove {
    public NastyPlot() {
        super(Type.DARK, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, 2);
    }

    @Override
    protected String describe() {
        return "Uses NastyPlot";
    }

}
