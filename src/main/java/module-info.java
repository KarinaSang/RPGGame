module main.rpggame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens main.rpggame to javafx.fxml;
    exports main.rpggame;
    exports main.rpggame.characters;
    opens main.rpggame.characters to javafx.fxml;
}