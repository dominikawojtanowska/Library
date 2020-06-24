package sample;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class StartPane extends Pane {
    StartPane(){
        this.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        MyButton b1 = new MyButton("Dodaj Klienta", this, 100, 100);
    }
}
