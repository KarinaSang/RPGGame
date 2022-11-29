package main.rpggame.characters;

public class Player extends Character{ // player is-a character
    private boolean arrowOn;
    private int arrowTimer;

    public Player(int hp, int attackPower, int chargePower, int blockPower, String name) {
        super(hp, attackPower, chargePower, blockPower, name);
        arrowOn = false;
        arrowTimer = 0;
    }

    public boolean getArrowOn() {
        return arrowOn;
    }

    public int getArrowTimer() {
        return arrowTimer;
    }

    // when arrow goes off screen or hits an enemy, we want to reset the arrow
    public void resetArrow() {
        arrowOn = false;
        arrowTimer = 0;
    }

    public void increaseArrowTimer() {
        arrowTimer++;
    }

    @Override
    public String castAbility() {
        arrowOn = true;
        return "Firing an arrow\n";
    }
}
