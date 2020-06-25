package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI {
    Pane mainPane =null;
    Pane addBookPane=null;
    Pane deleteBookPane=null;
    Pane addClientPane=null;
    Pane deleteClientPane=null;
    Pane addRental=null;
    Pane showRecommended=null;
    private Scene scene;


    public GUI(Stage stage, LibraryDatabaseService lbs) {
        mainPane=crateManePane();
        scene = new Scene (mainPane);
        stage.setScene(scene);
        stage.show();

    }

    private Pane crateManePane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400); pane.setMaxSize(600, 1500);
        MyButton[] buttons = new MyButton[6];
        buttons[0] = new MyButton("Add Client",  50);
        buttons[1] = new MyButton("Delete Client",  100);
        buttons[2] = new MyButton("Add Book",  150);
        buttons[3] = new MyButton("Delete Book",  200);
        buttons[4] = new MyButton("Rent Book",  250);
        buttons[5] = new MyButton("Recomend Book",  300);

        pane.getChildren().addAll(buttons);

        buttons[0].setOnAction(e ->{
            if(addClientPane==null){
                addClientPane=creteAddClientPane();
            }
            scene.setRoot(addClientPane);
        });
        return pane;

    }

    private Pane creteAddClientPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400); mainPane.setMaxSize(600, 1500);
        Text enterClientData = new Text("Please enter Client data below:"); enterClientData.setY(25); enterClientData.setX(10);
        Text name = new Text ("Name: "); name.setY(70); name.setX(10);
        Text surname = new Text ("Surname: "); surname.setY(120); surname.setX(10);
        Text age = new Text ("Age (optionally): "); age.setY(170); age.setX(10);
        TextField nameTF = new TextField(); nameTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(nameTF, 70.);
        AnchorPane.setRightAnchor(nameTF, 20.);
        TextField surnameTF = new TextField(); surnameTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(surnameTF, 70.);
        AnchorPane.setRightAnchor(surnameTF, 20.);
        TextField ageTF = new TextField(); ageTF.setLayoutY(150);
        AnchorPane.setLeftAnchor(ageTF, 110.);
        AnchorPane.setRightAnchor(ageTF, 20.);
        MyButton ok = new MyButton("OK", 250);
        pane.getChildren().addAll(enterClientData,name,surname,age,nameTF,surnameTF,ageTF, ok);
        return pane;
    }


}
