package main.rpggame.characters;

public class Werewolf extends Character {
    public Werewolf(int hp, int attackPower, int chargePower, int blockPower, String name) {
        super(hp, attackPower, chargePower, blockPower, name);
    }

    // werewolves will have empowered attacks
    @Override
    public String castAbility() {
        this.setAttackPower(this.getAttackPower() + 10);
        return this.getName() + " received an attack buff\n";
    }
}
