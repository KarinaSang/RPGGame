package main.rpggame;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RPGGame extends Application {
    private Button attackButton;
    private Button chargeButton;
    private Button blockButton;
    private Button restartButton;
    private TextArea output;
    private Character player;
    private Character ai;
    private Canvas canvas;
    private GraphicsContext gc;
    private FightScene fightScene;

    private Parent createContent() {
        player = new Character(100, 15, 20, 5);
        ai = new Character(100, 5, 5, 5);
        VBox root = new VBox(10);
        root.setPrefSize(800, 600);

        output = new TextArea();
        attackButton = new Button ("ATTACK");
        chargeButton = new Button ("CHARGE");
        blockButton = new Button ("BLOCK");
        restartButton = new Button("RESTART");

        GridPane actionPane = new GridPane();
        actionPane.add(attackButton, 0, 0);
        actionPane.add(chargeButton, 1, 0);
        actionPane.add(blockButton, 2, 0);
        actionPane.add(restartButton, 3, 0);
        actionPane.setHgap(30);

        attackButton.setOnAction(e -> makeMove(Action.ATTACK));
        chargeButton.setOnAction(e -> makeMove(Action.CHARGE));
        blockButton.setOnAction(e -> makeMove(Action.BLOCK));
        restartButton.setOnAction(e -> restart());

        output.setPrefHeight(450);

        attackButton.setFont(Font.font("Arial", 26));
        chargeButton.setFont(Font.font("Arial", 26));
        blockButton.setFont(Font.font("Arial", 26));
        restartButton.setFont(Font.font("Arial", 26));

        output.setFont(Font.font("Arial", 26));
        updateInfo();

        canvas = new Canvas(800, 300);
        gc = canvas.getGraphicsContext2D();

        fightScene = new FightScene(gc);
        fightScene.render(output);

        root.getChildren().addAll(actionPane, canvas, output);
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
        stage.setTitle("RPG GAME");
    }

    private void disableButtons() {
        attackButton.setDisable(true);
        chargeButton.setDisable(true);
        blockButton.setDisable(true);
    }

    private void enableButtons() {
        attackButton.setDisable(false);
        chargeButton.setDisable(false);
        blockButton.setDisable(false);
    }

    private void restart() {
        enableButtons();
        output.clear();
        player.setHp(100);
        ai.setHp(100);
        updateInfo();
    }

    private void makeMove(Action userAction) {
        Action aiAction = makeAIMove();
        ActionResult result = userAction.checkAgainst(aiAction);

        if (result == ActionResult.DRAW) {
            output.appendText("DRAW \n");
        }
        else if (result == ActionResult.WIN) {
            int dmg = player.calcDamage(userAction);
            ai.setHp(ai.getHp() - dmg);
            output.appendText("Player deals " + dmg + " to AI \n");
            updateInfo();

            // if ai loses all hp
            if (ai.getHp() < 0) {
                output.appendText("Game over. Player wins!");
                disableButtons();
            }
        }
        else {
            int dmg = ai.calcDamage(userAction);
            player.setHp(player.getHp() - dmg);
            output.appendText("AI deals " + dmg + " to player \n");
            updateInfo();

            // if player loses all hp
            if (player.getHp() < 0) {
                output.appendText("Game over. AI wins!");
                disableButtons();
            }
        }
    }

    private Action makeAIMove() {
        int aiMoveIndex = (int) (Math.random() * Action.values().length);

        return Action.values()[aiMoveIndex];
    }

    private void updateInfo() {
        output.appendText("Player: " + player + ", AI: " + ai + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
