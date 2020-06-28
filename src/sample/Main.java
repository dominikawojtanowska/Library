package sample;

import javafx.application.Application;
import javafx.stage.Stage;

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
