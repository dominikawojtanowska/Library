package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        LibraryDatabaseService lb = new LibraryDatabaseService();
        GUI gui = new GUI(primaryStage, lb);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
