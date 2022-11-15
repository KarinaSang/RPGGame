package main.rpggame.characters;

public class Player extends Character{ // player is-a character
    public Player(int hp, int attackPower, int chargePower, int blockPower, String name) {
        super(hp, attackPower, chargePower, blockPower, name);
    }

    @Override
    public String castAbility() {
        return "Firing an arrow\n";
    }
}
