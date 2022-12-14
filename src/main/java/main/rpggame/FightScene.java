package main.rpggame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import main.rpggame.characters.Character;
import main.rpggame.characters.Goblin;
import main.rpggame.characters.Player;
import main.rpggame.characters.Vampire;
import main.rpggame.model2d.Character2D;

import java.util.ArrayList;
import java.util.Objects;

public class FightScene {
    public final static int NUM_OF_MONSTERS = 5;
    private int curNumOfMonsters;
    private Character2D player;
    private Character2D selectedMonster;
    private ArrayList<Character2D> monsters;
    private Image arrowImage;

    private Canvas canvas;
    private GraphicsContext gc;

    private int curLevel;

    public FightScene(Canvas canvas, GraphicsContext gc) {
        this.canvas = canvas;
        this.gc = gc;
        init();
    }

    private void init() {
        curLevel = 1;
        monsters = new ArrayList<Character2D>();
        Image playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/link_character.png")));
        Character playerInfo = new Player(100, 15, 20, 5, "Link");
        player = new Character2D(playerImage, playerImage.getWidth()*0.2,
                playerImage.getHeight()*0.2, 20, 100, 0, playerInfo);

        Image goblinImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/goblin_character.png")));

        for (int i = 0; i < NUM_OF_MONSTERS; i++) {
            int startX = (int) (Math.random() * 750);
            int startY = (int) (Math.random() * 250);
            Character goblinInfo = new Goblin(100, 5, 5, 5, "Goblin"+ (i+1));
            Character2D goblin = new Character2D(goblinImage, goblinImage.getWidth()*0.2,
                    goblinImage.getHeight()*0.2, startX, startY, 1, goblinInfo);
            monsters.add(goblin);
        }

        arrowImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/arrow.png")));

        curNumOfMonsters = NUM_OF_MONSTERS;
    }

    private void level2() {
        monsters = new ArrayList<Character2D>();
        Image playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/link_character.png")));
        Character playerInfo = new Player(100, 15, 20, 5, "Link");
        player = new Character2D(playerImage, playerImage.getWidth()*0.2,
                playerImage.getHeight()*0.2, 20, 100, 0, playerInfo);

        Image vampireImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/vampire.png")));

        for (int i = 0; i < NUM_OF_MONSTERS; i++) {
            int startX = (int) (Math.random() * 750);
            int startY = (int) (Math.random() * 250);
            Character vampireInfo = new Vampire(100, 5, 5, 5, "Vampire"+ (i+1));
            Character2D goblin = new Character2D(vampireImage, vampireImage.getWidth()*0.2,
                    vampireImage.getHeight()*0.2, startX, startY, 1, vampireInfo);
            monsters.add(goblin);
        }

        curNumOfMonsters = NUM_OF_MONSTERS;
    }

    public void render(TextArea output) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // end the current game if player loses all health
        if (player.getInfo().getHp() <= 0) {
            return;
        }

        Character2D monsterRemoved = removeCollider(player.getX(), player.getY());

        if (monsterRemoved != null) {
            output.appendText(monsterRemoved + " is killed!\n");
        }

        if (((Player)(player.getInfo())).getArrowOn()) {
            drawArrow(output);
        }


        drawPlayer();
        drawMonsters();

        if (curNumOfMonsters == 0) {
            output.appendText("You have killed all monsters! Good job.\n");
            curLevel += 1;

            if (curLevel == 2) {
                level2();
            }
        }
    }

    private void drawPlayer() {
        gc.drawImage(player.getCharacterImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    private void drawMonsters() {
        for (int i = 0; i < curNumOfMonsters; i++) {
            Character2D monster = monsters.get(i);

            switch(monster.getDirection()) {
                case LEFT:
                    monster.moveLeft();
                    break;
                case RIGHT:
                    monster.moveRight();
                    break;
                case UP:
                    monster.moveUp();
                    break;
                case DOWN:
                    monster.moveDown();
                    break;
            }

            if (monster.getY() < 0) {
                monster.setDirection(Direction.DOWN);
            }
            else if (monster.getX() < 0) {
                monster.setDirection(Direction.RIGHT);
            }
            if (monster.getY() > canvas.getHeight()-50) {
                monster.setDirection(Direction.UP);
            }
            if (monster.getX() > canvas.getWidth()-50) {
                monster.setDirection(Direction.LEFT);
            }

            gc.drawImage(monster.getCharacterImage(), monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
        }
    }

    private void drawArrow(TextArea output) {
        Player playerInfo = (Player) player.getInfo();
        int offset = 8 * playerInfo.getArrowTimer();
        playerInfo.increaseArrowTimer();

        int monsterIndex = getCollider(player.getX()+offset, player.getY());

        if (monsterIndex != -1) {
            playerInfo.resetArrow();
            Character monsterInfo = monsters.get(monsterIndex).getInfo();
            monsterInfo.setHp(monsterInfo.getHp()-40);
            output.appendText(monsterInfo.getName() + " got hit by an arrow and lost 40 HP!\n");

            if (monsterInfo.getHp() <= 0) {
                curNumOfMonsters--;
                output.appendText("Good job, you killed " + monsterInfo.getName() + " with an arrow!\n");
                monsters.remove(monsterIndex);
            }
        }
        else if (player.getX()+offset >= canvas.getWidth()) { //out of bounds
            output.appendText("Oops, you missed the arrow!\n");
            playerInfo.resetArrow();
        }
        else {
            gc.drawImage(arrowImage, player.getX()+offset, player.getY()+player.getHeight()/2, arrowImage.getWidth(), arrowImage.getHeight());
        }

    }

    public Character2D removeCollider(double x1, double y1) {
        int result = getCollider(x1, y1);

        if (result != -1) {
            selectedMonster = monsters.get(result);

            if (selectedMonster.getInfo().getHp() <= 0) {
                curNumOfMonsters--;
                return monsters.remove(result);
            }

        }

        return null;
    }

    public int getCollider(double x1, double y1) {
        for (int i = 0; i < curNumOfMonsters; i++) {
            Character2D monster = monsters.get(i);
            boolean checkX = x1 >= monster.getX()-20 && x1 <= monster.getX() + monster.getWidth()+20;
            boolean checkY = y1 <= monster.getY()+10 && y1 >= monster.getY() - monster.getHeight()-10;

            if (checkX && checkY) {
                return i;
            }
        }

        return -1;
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
    public Character2D getSelectedMonster() {
        return selectedMonster;
    }

}
