package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
    Pane activePane ;
    StartPane mainPane ;
    Pane[] panes = new Pane[6];

    public GUI(Stage stage, LibraryDatabaseService lbs) {
        mainPane = new StartPane();
        Scene scene = new Scene (mainPane, 600, 300);
        stage.setScene(scene);
        stage.show();
    }
}
