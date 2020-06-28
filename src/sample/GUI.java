package sample;

import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI {
    Pane mainPane;
    Pane addBookPane=null;
    Pane deleteBookPane=null;
    Pane addClientPane=null;
    Pane deleteClientPane=null;
    Pane rentBookPane=null;
    Pane showMostPopular=null;
    private Scene scene;
    LibraryDatabaseService lbs;
    Stage popupStage = new Stage();
    Pattern pattern = Pattern.compile("[A-Z][a-z]+");
    Pattern patternYear = Pattern.compile("[1-2][0-9]{3}");
    Pattern patternEmpty = Pattern.compile("\\s*");
    Background background=new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, new Insets(0,0,0, 0)));

    public GUI(Stage stage, LibraryDatabaseService lbs) throws Exception {
        this.lbs=lbs;
        stage.setTitle("Library");
        mainPane=crateMainPane();
        scene = new Scene (mainPane);
        stage.setScene(scene);
        stage.show();
    }

    private Pane crateMainPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400); pane.setMaxSize(600, 1500);
        MyButton[] buttons = new MyButton[6];
        buttons[0] = new MyButton("Add client",  50);
        buttons[1] = new MyButton("Delete client",  100);
        buttons[2] = new MyButton("Add book",  150);
        buttons[3] = new MyButton("Delete book",  200);
        buttons[4] = new MyButton("Rent/Return book",  250);
        buttons[5] = new MyButton("Show most popular",  300);
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
        buttons[5].setOnAction(e->{
            if(showMostPopular==null) showMostPopular=createShowRecommendedPane();
            scene.setRoot(showMostPopular);
        });
        return pane;
    }

    private Pane createShowRecommendedPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400);
        Text text = new Text("Pick by witch category: "); text.setX(10); text.setY(20);
        CheckBox cba = new CheckBox("by reader's age"); cba.setLayoutY(35); cba.setLayoutX(10);
        TextField ageTF = new TextField(); ageTF.setLayoutY(30);
        AnchorPane.setLeftAnchor(ageTF, 130.);
        AnchorPane.setRightAnchor(ageTF, 20.);
        ageTF.setDisable(true);
        CheckBox cbc = new CheckBox("by category"); cbc.setLayoutY(75); cbc.setLayoutX(10);
        TextField categoryTF = new TextField(); categoryTF.setLayoutY(70);
        AnchorPane.setLeftAnchor(categoryTF, 130.);
        AnchorPane.setRightAnchor(categoryTF, 20.);
        categoryTF.setDisable(true);
        cba.setOnAction(e->{
            if(cba.isSelected()){
                ageTF.setDisable(false);
            }
            else{
                ageTF.setDisable(true);
            }
        });
        cbc.setOnAction(e->{
            if(cbc.isSelected()){
                categoryTF.setDisable(false);
            }
            else{
                categoryTF.setDisable(true);
            }
        });
        TableView bookTable = new TableView();
        AnchorPane.setTopAnchor(bookTable, 145.);
        AnchorPane.setBottomAnchor(bookTable, 70.);
        AnchorPane.setLeftAnchor(bookTable, 20.);
        AnchorPane.setRightAnchor(bookTable, 20.);
        TableColumn<String, Book> column1 = new TableColumn<>("isbn");
        column1.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        TableColumn<String, Book> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<String, Book> column3 = new TableColumn<>("Author");
        column3.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<String, Book> column4 = new TableColumn<>("Category");
        column4.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookTable.getColumns().addAll(column1, column2,column3,column4);
        MyButton goButton = new MyButton("Show", 110);
        goButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setBackground(background);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            boolean success = false;
            boolean goodAgeFormat = false;
            ObservableList<Book> popularBookList = null;
            if(cba.isSelected() || cbc.isSelected()){
                int yearOfBirth = 0;
                Matcher categoryMatcher = patternEmpty.matcher(categoryTF.getText());
                if(cba.isSelected()){
                    try {
                        Date now = new Date();
                        String[] dateParts = now.toString().split(" ");
                        yearOfBirth = Integer.parseInt(dateParts[dateParts.length-1]) - Integer.parseInt(ageTF.getText());
                        goodAgeFormat=true;
                    }catch(Exception exc){
                        popText.setText("Bad age format");
                        popupStage.show();

                    }
                    if(cbc.isSelected()){
                        if(!categoryMatcher.matches() || !goodAgeFormat){
                            popularBookList= lbs.getPopularBookList(yearOfBirth, categoryTF.getText());
                            success=true;
                        }
                        else{
                            popText.setText("Lack of data");
                            popupStage.show();
                        }
                    }else{
                        if(goodAgeFormat) {
                            popularBookList = lbs.getPopularBookList(yearOfBirth);
                            success = true;
                        }
                    }
                }
                else{
                    if(!categoryMatcher.matches()){
                        popularBookList= lbs.getPopularBookList(categoryTF.getText());
                        success = true;
                    }
                }
            }else{
                popularBookList=lbs.getPopularBookList();
                success=true;
            }
            if(success){
                bookTable.setItems(popularBookList);
            }
        });
        ImageView backButton = createBackIcon();
        goButton.setPrefHeight(25);
        pane.getChildren().addAll(text, cba,ageTF, cbc, categoryTF, goButton, backButton, bookTable);
        return pane;
    }

    private Pane createRentBookPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400);
        Text enterRentalData = new Text("Please enter rental data below:"); enterRentalData.setY(25); enterRentalData.setX(10);
        Text isbn = new Text ("ISBN: "); isbn.setY(70); isbn.setX(10);
        Text cardNumber = new Text ("Client's card's number: "); cardNumber.setY(120); cardNumber.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 70.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        TextField cardNumberTF = new TextField(); cardNumberTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(cardNumberTF, 150.);
        AnchorPane.setRightAnchor(cardNumberTF, 20.);
        MyButton okButton = new MyButton("OK", 270);
        okButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setBackground(background);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            Matcher isbnMatcher = patternEmpty.matcher(cardNumberTF.getText());
            Matcher cardNumberMatcher = patternEmpty.matcher(cardNumberTF.getText());
            if(!isbnMatcher.matches() && !cardNumberMatcher.matches()){
                popText.setText(lbs.addUpdateRental(isbnTF.getText() , Integer.parseInt(cardNumberTF.getText())));
            }
            else{
                popText.setText("Lack of data, or bad provided data");
            }
            popupStage.show();
        });
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterRentalData, isbn, cardNumber, isbnTF, cardNumberTF, okButton ,backButton);
        return pane;
    }

    private Pane createDeleteClientPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400);
        Text enterClientData = new Text("Please enter client's card's number:"); enterClientData.setY(25); enterClientData.setX(10);
        TextField cardNumberTF = new TextField(); cardNumberTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(cardNumberTF, 20.);
        AnchorPane.setRightAnchor(cardNumberTF, 20.);
        MyButton okButton = new MyButton("OK", 270);
        okButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setBackground(background);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            Matcher nameMatcher = patternEmpty.matcher(cardNumberTF.getText());
            if(!nameMatcher.matches()){
                popText.setText(lbs.deleteClient(Integer.parseInt(cardNumberTF.getText())));
            }
            else{
                popText.setText("Lack of data, or bad provided data");
            }
            popupStage.show();;
        });
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterClientData, cardNumberTF, okButton ,backButton);
        return pane;
    }

    private Pane createAddBookPane()  {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
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
        MyButton okButton = new MyButton("OK", 250);
        okButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setBackground(background);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            Matcher isbnMatcher = patternEmpty.matcher(isbnTF.getText());
            Matcher titleMatcher = patternEmpty.matcher(titleTF.getText());
            Matcher authorMatcher = patternEmpty.matcher(authorTF.getText());
            Matcher categoryMatcher = patternEmpty.matcher(categoryTF.getText());
            if(!isbnMatcher.matches() && !titleMatcher.matches() && !authorMatcher.matches() && !categoryMatcher.matches()){
                popText.setText(lbs.addBook(isbnTF.getText(), titleTF.getText(), authorTF.getText(), categoryTF.getText()));
            }
            else{
                popText.setText("Lack of data, or bad provided data");
            }
            popupStage.show();;
        });
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterBookData, isbn, title, author, category, isbnTF, titleTF, authorTF, categoryTF, okButton,backButton);
        return pane;
    }

    private Pane creteAddClientPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400);
        Text enterClientData = new Text("Please enter Client data below:"); enterClientData.setY(25); enterClientData.setX(10);
        Text name = new Text ("Name: "); name.setY(70); name.setX(10);
        Text surname = new Text ("Surname: "); surname.setY(120); surname.setX(10);
        Text year = new Text ("Year of birth (optionally): "); year.setY(170); year.setX(10);
        Text sex= new Text ("Sex: "); sex.setY(220); sex.setX(10);
        TextField nameTF = new TextField(); nameTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(nameTF, 70.);
        AnchorPane.setRightAnchor(nameTF, 20.);
        TextField surnameTF = new TextField(); surnameTF.setLayoutY(100);
        AnchorPane.setLeftAnchor(surnameTF, 70.);
        AnchorPane.setRightAnchor(surnameTF, 20.);
        TextField yearTF = new TextField(); yearTF.setLayoutY(150);
        AnchorPane.setLeftAnchor(yearTF, 150.);
        AnchorPane.setRightAnchor(yearTF, 20.);
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
        MyButton okButton = new MyButton("OK", 250);
        okButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setBackground(background);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            Matcher nameMatcher = pattern.matcher(nameTF.getText());
            Matcher surnameMatcher = pattern.matcher(surnameTF.getText());
            Matcher yearMatcher = patternYear.matcher(yearTF.getText());
            Matcher emptyYearMatcher = patternEmpty.matcher(yearTF.getText());
            if((cbm.isSelected() || cbf.isSelected()) && nameMatcher.matches() && surnameMatcher.matches() && (yearMatcher.matches()||emptyYearMatcher.matches())){
                char sexChar;
                if(cbm.isSelected()){
                    sexChar= 'm';
                }else{
                    sexChar='f';
                }
                if(emptyYearMatcher.matches()){
                    popText.setText(lbs.addClient(nameTF.getText(), surnameTF.getText(), sexChar));
                }
                else{
                    popText.setText(lbs.addClient(nameTF.getText(), surnameTF.getText(),Integer.parseInt(yearTF.getText()), sexChar));
                }
            }
            else{
                popText.setText("Lack of data, or bad provided data");
            }
            popupStage.show();;
        });
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterClientData,name,surname,year,sex,nameTF,surnameTF,yearTF,cbm, cbf, okButton,backButton);
        return pane;
    }

    private Pane createDeleteBookPane() {
        Pane pane =new AnchorPane();
        pane.setBackground(background);
        pane.setPrefSize(300,400);
        Text enterBookData = new Text("Please enter book data below:"); enterBookData.setY(25); enterBookData.setX(10);
        Text isbn = new Text ("ISBN: "); isbn.setY(70); isbn.setX(10);
        TextField isbnTF = new TextField(); isbnTF.setLayoutY(50);
        AnchorPane.setLeftAnchor(isbnTF, 70.);
        AnchorPane.setRightAnchor(isbnTF, 20.);
        MyButton okButton = new MyButton("OK", 270);
        okButton.setOnAction(e->{
            Pane popupPane = new StackPane();
            popupPane.setBackground(background);
            Scene popupScene= new Scene(popupPane);
            popupStage.setScene(popupScene);
            popupPane.setPrefSize(200,50);
            Text popText = new Text();
            popupPane.getChildren().add(popText);
            Matcher isbnMatcher = patternEmpty.matcher(isbnTF.getText());
            if(!isbnMatcher.matches()){
                popText.setText(lbs.deleteBook(isbnTF.getText()));
            }
            else{
                popText.setText("Lack of data, or bad provided data");
            }
            popupStage.show();;
        });
        ImageView backButton = createBackIcon();
        pane.getChildren().addAll(enterBookData, isbn,  isbnTF, okButton ,backButton);
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
