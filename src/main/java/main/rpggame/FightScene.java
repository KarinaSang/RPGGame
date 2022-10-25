package main.rpggame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import main.rpggame.model2d.Character2D;

import java.util.ArrayList;
import java.util.Objects;

public class FightScene {
    public final static int NUM_OF_MONSTERS = 5;
    private int curNumOfMonsters;
    private Character2D player;
    private ArrayList<Character2D> monsters;

    private GraphicsContext gc;

    public FightScene(GraphicsContext gc) {
        this.gc = gc;
        init();
    }

    private void init() {
        monsters = new ArrayList<Character2D>();
        Image playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/link_character.png")));
        player = new Character2D(playerImage, "Link", playerImage.getWidth()*0.2, playerImage.getHeight()*0.2, 20, 100, 0);

        Image goblinImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/goblin_character.png")));

        for (int i = 0; i < NUM_OF_MONSTERS; i++) {
            int startX = (int) Math.random() * 780;
            int startY = (int) Math.random() * 280;
            Character2D goblin = new Character2D(goblinImage, "Goblin"+ (i+1), goblinImage.getWidth()*0.2, goblinImage.getHeight()*0.2, startX, startY, 0);
            monsters.add(goblin);
        }
    }

    public void render(TextArea output) {
        drawPlayer();
       // drawMonsters();
    }

    private void drawPlayer() {
        gc.drawImage(player.getCharacterImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    public Character2D getPlayer() {
        return player;
    }

    public void setPlayer(Character2D player) {
        this.player = player;
    }

    public ArrayList<Character2D> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Character2D> monsters) {
        this.monsters = monsters;
    }

}
