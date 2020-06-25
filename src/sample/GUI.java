package sample;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.FileInputStream;

public class GUI {
    Pane mainPane =null;
    Pane addBookPane=null;
    Pane deleteBookPane=null;
    Pane addClientPane=null;
    Pane deleteClientPane=null;
    Pane rentBookPane=null;
    Pane showRecommended=null;
    private Scene scene;


    public GUI(Stage stage, LibraryDatabaseService lbs) throws Exception {
        mainPane=crateMainPane();
        scene = new Scene (mainPane);
        stage.setScene(scene);
        stage.show();

    }

    private Pane crateMainPane() {
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
        buttons[1].setOnAction(e->{
            if(deleteClientPane==null){
                deleteClientPane=createDeleteClientPane();
            }
            scene.setRoot(deleteClientPane);
        });
        buttons[2].setOnAction(e->{
            if (addBookPane==null){
               addBookPane=createAddBookPane();
            }
            scene.setRoot(addBookPane);
        });
        buttons[3].setOnAction(e->{
            if (deleteBookPane==null){
                deleteBookPane=createDeleteBookPane();
            }
            scene.setRoot(deleteBookPane);
        });
        buttons[4].setOnAction(e->{
            if (rentBookPane==null){
                rentBookPane=createRentBookPane();
            }
            scene.setRoot(rentBookPane);
        });
        return pane;

    }

    private Pane createRentBookPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400);
        Text enterRentalData = new Text("Please enter rental data below:"); enterRentalData.setY(25); enterRentalData.setX(10);
        Text isbn = new Text ("ISBN: "); isbn.setY(70); isbn.setX(10);
        Text ccn = new Text ("Client's card's number: "); ccn.setY(120); ccn.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 70.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        TextField ccnTF = new TextField(); ccnTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(ccnTF, 150.);
        AnchorPane.setRightAnchor(ccnTF, 20.);
        MyButton ok = new MyButton("OK", 270);
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterRentalData, isbn, ccn, isbnTF, ccnTF, ok,backButton);
        return pane;
    }

    private Pane createDeleteClientPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400);
        Text enterBookData = new Text("Please enter client;s card's number:"); enterBookData.setY(25); enterBookData.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 20.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        MyButton ok = new MyButton("OK", 270);
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterBookData,  isbnTF, ok,backButton);
        return pane;
    }

    private Pane createAddBookPane()  {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400);
        Text enterBookData = new Text("Please enter book data below:"); enterBookData.setY(25); enterBookData.setX(10);
        Text isbn = new Text ("ISBN: "); isbn.setY(70); isbn.setX(10);
        Text title = new Text ("Title: "); title.setY(120); title.setX(10);
        Text author = new Text ("Author: "); author.setY(170); author.setX(10);
        Text category = new Text ("Category: "); category.setY(220); category.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 70.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        TextField titleTF = new TextField(); titleTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(titleTF, 70.);
        AnchorPane.setRightAnchor(titleTF, 20.);
        TextField authorTF = new TextField(); authorTF.setLayoutY(150);
        AnchorPane.setLeftAnchor(authorTF, 70.);
        AnchorPane.setRightAnchor(authorTF, 20.);
        TextField categoryTF = new TextField(); categoryTF.setLayoutY(200);
        AnchorPane.setLeftAnchor(categoryTF, 70.);
        AnchorPane.setRightAnchor(categoryTF, 20.);
        MyButton ok = new MyButton("OK", 270);
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterBookData, isbn, title, author, category, isbnTF, titleTF, authorTF, categoryTF, ok,backButton);
        return pane;
    }

    private Pane creteAddClientPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400);
        Text enterClientData = new Text("Please enter Client data below:"); enterClientData.setY(25); enterClientData.setX(10);
        Text name = new Text ("Name: "); name.setY(70); name.setX(10);
        Text surname = new Text ("Surname: "); surname.setY(120); surname.setX(10);
        Text age = new Text ("Age (optionally): "); age.setY(170); age.setX(10);
        Text sex= new Text ("Sex: "); sex.setY(220); sex.setX(10);
        TextField nameTF = new TextField(); nameTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(nameTF, 70.);
        AnchorPane.setRightAnchor(nameTF, 20.);
        TextField surnameTF = new TextField(); surnameTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(surnameTF, 70.);
        AnchorPane.setRightAnchor(surnameTF, 20.);
        TextField ageTF = new TextField(); ageTF.setLayoutY(150);
        AnchorPane.setLeftAnchor(ageTF, 110.);
        AnchorPane.setRightAnchor(ageTF, 20.);
        CheckBox cbm = new CheckBox("male"); cbm.setLayoutY(205);
        AnchorPane.setLeftAnchor(cbm,70.);
        CheckBox cbf = new CheckBox("female"); cbf.setLayoutY(205);
        cbf.setOnAction(e->{
            if(cbm.isSelected()) cbm.setSelected(false);
            cbf.setSelected(true);
        });
        cbm.setOnAction(e->{
            if(cbf.isSelected()) cbf.setSelected(false);
            cbm.setSelected(true);
        });
        AnchorPane.setLeftAnchor(cbf, 130.);
        MyButton ok = new MyButton("OK", 250);
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterClientData,name,surname,age,sex,nameTF,surnameTF,ageTF,cbm, cbf, ok,backButton);
        return pane;
    }

    private Pane createDeleteBookPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0))));
        pane.setPrefSize(300,400);
        Text enterBookData = new Text("Please enter book data below:"); enterBookData.setY(25); enterBookData.setX(10);
        Text isbn = new Text ("ISBN: "); isbn.setY(70); isbn.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 70.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        MyButton ok = new MyButton("OK", 270);
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterBookData, isbn,  isbnTF, ok,backButton);
        return pane;
    }

    private ImageView createBackIcon(){
        ImageView backIcon = new ImageView(new Image("file:src/files/backIcon.png"));
        backIcon.setFitHeight(40); backIcon.setFitWidth(40);
        AnchorPane.setRightAnchor(backIcon,20.);
        AnchorPane.setBottomAnchor(backIcon, 20.);
        backIcon.setOnMouseClicked(e->{
            scene.setRoot(mainPane);
        });
        backIcon.setOnMouseEntered(e->{
            ScaleTransition st = new ScaleTransition(Duration.millis(500), backIcon);
            st.setToX(1.20); st.setToY(1.20);
            st.play();
        });
        backIcon.addEventHandler(MouseEvent.MOUSE_EXITED, e ->{
            ScaleTransition st = new ScaleTransition(Duration.millis(500), backIcon);
            st.setToX(1);  st.setToY(1);
            st.play();
        } );
        return backIcon;
    }


}
