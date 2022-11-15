package main.rpggame.characters;

public class Vampire extends Character {
    public Vampire(int hp, int attackPower, int chargePower, int blockPower, String name) {
        super(hp, attackPower, chargePower, blockPower, name);
    }

    // vampire can restore health and possibly gain a charge power buff
    @Override
    public String castAbility() {
        String abilityString = this.getName() + " restored some health";

        int newHp = this.getHp() + 20;

        if (newHp > 100) {
            this.setHp(100);
            this.setChargePower(this.getChargePower() + (newHp-100));
            abilityString += " and gained a charge power buff";
        } else {
            this.setHp(newHp);
        }

        return abilityString + "\n";
    }
}
