package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class PsyBeam extends SpecialMove {
    public PsyBeam() {
        super(Type.PSYCHIC, 65, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() <= 0.1) Effect.confuse(p);
    }

    @Override
    protected String describe() {
        return "Uses Psy Beam";
    }
}
