package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class WaterFall extends PhysicalMove {
    public WaterFall() {
        super(Type.WATER, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() <= 0.2) Effect.flinch(p);
    }

    @Override
    public String describe() {
        return "Uses Waterfall";
    }
}
