import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();
        Budew budew = new Budew("team01", 10);
        Roselia roselia = new Roselia("team02", 9);
        Roserade roserade = new Roserade("team03", 8);
        PsyDuck psyDuck = new PsyDuck("team11", 10);
        GoldDuck goldDuck = new GoldDuck("team12", 10);
        Hoopa hoopa = new Hoopa("team13", 10);
        battle.addAlly(budew);
        battle.addAlly(roselia);
        battle.addAlly(roserade);
        battle.addFoe(psyDuck);
        battle.addFoe(goldDuck);
        battle.addFoe(hoopa);
        battle.go();
    }
}