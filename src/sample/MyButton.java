package sample;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;


public class MyButton extends Circle {
    private String name;

    MyButton (String name, Pane pane, double x, double y){
        super(x,y,50);
        this.name= name;
        this.setFill(Color.ORANGE);
        this.setStroke(Color.DARKBLUE); this.setStrokeWidth(5); this.setFill(Color.LIGHTCYAN);
        Text text = new Text(name); text.setTextOrigin(VPos.CENTER); text.setX(x); text.setY(y);
        text.setDisable(true);
        

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->{
            FillTransition ft = new FillTransition(Duration.millis(500), this , (Color) this.getFill(), Color.ORANGE);
            ScaleTransition st = new ScaleTransition(Duration.millis(500), this);
            st.setToX(1.20); st.setToY(1.20);
            ParallelTransition pt = new ParallelTransition(ft, st);
            pt.play();
        });
        this.addEventHandler(MouseEvent.MOUSE_EXITED,  e ->{
            FillTransition ft = new FillTransition(Duration.millis(500), this , (Color) this.getFill(), Color.LIGHTCYAN);
            ScaleTransition st = new ScaleTransition(Duration.millis(500), this);
            st.setToX(1);  st.setToY(1);
            ParallelTransition pt = new ParallelTransition(ft, st);
            pt.play();
        } );

        pane.getChildren().addAll(this, text);


    }

}
