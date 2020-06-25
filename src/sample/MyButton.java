package sample;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;


public class MyButton extends Button {


    MyButton (String name, double y){
        super(name);
        setBackground(new Background(new BackgroundFill(Color.SALMON, CornerRadii.EMPTY , new Insets(0,0,0,0))));
        setLayoutY(y);
        setPrefSize(150,50);
        setStyle("-fx-border-color:black");
        setOpacity(0.70);
        AnchorPane.setLeftAnchor(this, 70.);
        AnchorPane.setRightAnchor(this, 70.);


        this.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->{
            this.setOpacity(1);
            this.toFront();
            ScaleTransition st = new ScaleTransition(Duration.millis(500), this);
            st.setToX(1.20); st.setToY(1.10);
            st.play();
        });
        this.addEventHandler(MouseEvent.MOUSE_EXITED,  e ->{
            setOpacity(0.70);
            ScaleTransition st = new ScaleTransition(Duration.millis(500), this);
            st.setToX(1);  st.setToY(1);
            st.play();
        } );



    }


}
