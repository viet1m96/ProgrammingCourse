package attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class AquaJet extends PhysicalMove {
    public AquaJet() {
        super(Type.WATER, 40, 100, 1, 1);
    }

    @Override
    public String describe() {
        return "Uses Aqua Jet";
    }
}
