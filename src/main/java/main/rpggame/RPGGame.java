package main.rpggame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.rpggame.characters.Character;
import main.rpggame.characters.Player;
import main.rpggame.model2d.Character2D;

import java.util.Objects;

public class RPGGame extends Application {
    private Button restartButton;
    private Label playerHPLabel;
    private int numOfPotions;
    private Label potionLabel;
    private TextArea output;
    private Canvas canvas;
    private GraphicsContext gc;
    private FightScene fightScene;

    private Parent createContent() {
        VBox root = new VBox(10);
        root.setPrefSize(800, 600);

        numOfPotions = 5;
        playerHPLabel = new Label("HP: 100");
        potionLabel = new Label(String.valueOf(numOfPotions));

        output = new TextArea();
        restartButton = new Button("RESTART");

        restartButton.setFont(Font.font("Arial", 26));
        output.setFont(Font.font("Arial", 26));
        playerHPLabel.setFont(Font.font("Arial", 26));
        potionLabel.setFont(Font.font("Arial", 20));

        Image potionImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/rpggame/img/potion.png")));
        ImageView potionImageView = new ImageView(potionImage);
        potionImageView.setFitHeight(40);
        potionImageView.setFitWidth(30);
        StackPane potionPane = new StackPane();
        potionPane.getChildren().add(potionImageView);
        potionPane.getChildren().add(potionLabel);

        GridPane gamePane = new GridPane();
        gamePane.add(playerHPLabel, 1, 0);
        gamePane.add(potionPane, 2, 0);
        gamePane.add(restartButton, 3, 0);
        gamePane.setHgap(30);

        restartButton.setOnAction(e -> restart());

        output.setPrefHeight(450);


        canvas = new Canvas(800, 300);
        gc = canvas.getGraphicsContext2D();

        fightScene = new FightScene(canvas, gc);
        fightScene.render(output);


        root.setOnKeyPressed(e -> {
            System.out.println(e.getCode() + " is pressed");
            Character2D currentPlayer = fightScene.getPlayer();

            // recording player movement
            if (e.getCode() == KeyCode.W) {
                currentPlayer.setY(currentPlayer.getY()-10);
            }
            else if (e.getCode() == KeyCode.A) {
                currentPlayer.setX(currentPlayer.getX()-10);
            }
            else if (e.getCode() == KeyCode.S) {
                currentPlayer.setY(currentPlayer.getY()+10);
            }
            else if (e.getCode() == KeyCode.D) {
                currentPlayer.setX(currentPlayer.getX()+10);
            }

            if (e.getCode() == KeyCode.ENTER
                && !((Player)(currentPlayer.getInfo())).getArrowOn()) {
                makeMove(Action.ABILITY);
            }

            // make player attack
            if (fightScene.getCollider(currentPlayer.getX(), currentPlayer.getY()) != -1) {
                if (e.getCode() == KeyCode.J) {
                    makeMove(Action.ATTACK);
                }
                else if (e.getCode() == KeyCode.K) {
                    makeMove(Action.CHARGE);
                }
                else if (e.getCode() == KeyCode.L) {
                    makeMove(Action.BLOCK);
                }
            }

            // consuming potion
            if (e.getCode() == KeyCode.P) {
                int hp = currentPlayer.getInfo().getHp();

                // only allow player to consume a potion when he is not full hp
                if (hp != 100 && numOfPotions > 0) {
                    numOfPotions--;
                    currentPlayer.getInfo().setHp(Math.min(hp+10, 100));
                    int newHp = currentPlayer.getInfo().getHp();
                    playerHPLabel.setText("HP: " + newHp);
                    output.appendText("Player drinks one potion and restores hp to " + newHp + "\n");
                    potionLabel.setText(String.valueOf(numOfPotions));
                }
            }

            fightScene.render(output);
        });

        root.getChildren().addAll(gamePane, canvas, output);
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                fightScene.render(output);
            }
        };
        gameLoop.start();

        stage.show();
        stage.setTitle("RPG GAME");
    }


    private void restart() {
        output.clear();
        fightScene = new FightScene(canvas, gc);
        playerHPLabel.setText("HP: 100");
        numOfPotions = 5;
        potionLabel.setText(String.valueOf(numOfPotions));
    }

    private void makeMove(Action userAction) {
        Action aiAction = makeAIMove();
        Character player = fightScene.getPlayer().getInfo();

        if (userAction == Action.ABILITY) {
            String abilityString = player.castAbility();
            output.appendText(abilityString);

            if (fightScene.getSelectedMonster() == null) {
                return;
            }
        }

        Character ai = fightScene.getSelectedMonster().getInfo();

        ActionResult result = userAction.checkAgainst(aiAction);


        if (result == ActionResult.DRAW) {
            output.appendText("DRAW \n");
        }
        else if (result == ActionResult.WIN) {
            int dmg = player.calcDamage(userAction);
            ai.setHp(ai.getHp() - dmg);
            output.appendText("Player deals " + dmg + " to " + ai.getName() + "\n");
            updateInfo();
        }
        else {
            int dmg = ai.calcDamage(userAction);
            player.setHp(player.getHp() - dmg);
            output.appendText(ai.getName()  + " deals " + dmg + " to player \n");
            updateInfo();

            // if player loses all hp
            if (player.getHp() <= 0) {
                output.appendText("Game over. Monsters win!");
            }
        }

        playerHPLabel.setText("HP: " + player.getHp());
    }

    private Action makeAIMove() {
        int aiMoveIndex = (int) (Math.random() * Action.values().length);

        return Action.values()[aiMoveIndex];
    }

    private void updateInfo() {
        output.appendText("Player " + fightScene.getPlayer().getInfo() + ", "
                + fightScene.getSelectedMonster().getInfo().getName() + " "
                + fightScene.getSelectedMonster().getInfo() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
