package asurza_maarse.bestfriend;

/*
 * Author: Zachary Maarse
 * Date: Dec 7, 2018
 * Purpose: Initial starting room
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import javafx.scene.shape.Shape;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

/**
 * FXML Controller class
 *
 * @author zacharym44
 */
public class FXMLRoomOneController implements Initializable {

    @FXML
    private Pane pnParallax;

    @FXML
    private ImageView imgUser;

    @FXML
    private Circle cPlayer;

    @FXML
    private Rectangle recTop, recBottom, recLeft, recRight;

    @FXML
    private Rectangle wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8, wall9, wall10, wall11, wall12, wall13, wall14, wall15, wall16, wall17, wall18;

    ArrayList<Rectangle> walls = new ArrayList();

    private Boolean up = false, down = false, left = false, right = false;

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));

    @FXML
    private void btnMove(KeyEvent e) {
        if (e.getCode() == KeyCode.UP) {
            setDirFalse();
            up = true;
        } else if (e.getCode() == KeyCode.DOWN) {
            setDirFalse();
            down = true;
        } else if (e.getCode() == KeyCode.LEFT) {
            setDirFalse();
            left = true;
        } else if (e.getCode() == KeyCode.RIGHT) {
            setDirFalse();
            right = true;
        }
 

    }

    private void move() {
        if (collisionT()) {

            if (up) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() - 3);

            } else if (down) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() + 3);

            } else if (left) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() - 3);

            } else if (right) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() + 3);

            }
        } else {
            direction();
        }
    }
    
    private void direction() {
        if (up) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() + 6);

        } else if (down) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() - 6);

        } else if (left) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() + 6);

        } else if (right) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() - 6);

        } else {
            setDirFalse();
        }

    }
    
    @FXML
    private void onRelease(KeyEvent e){
        if(e.getCode() == KeyCode.UP||e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT){
            setDirFalse();
        }
    }
    
    private void setDirFalse() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    private boolean collisionT() {
        for (int i = 0; i < walls.size(); i++) {
            if (collision(cPlayer, walls.get(i))) {
                return true;
            }
        }
        return false;
    }


    

    //public boolean coll(Circle block1, Rectangle block2) {
//returns true if the areas intersect, false if they dont
    //return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
    //}
    public boolean collision(Object block1, Object block2) {
        try {
            //If the objects can be changed to shapes just see if they intersect
            Shape s1 = (Shape) block1;
            Shape s2 = (Shape) block2;
            Shape a = Shape.intersect(s1, s2);
            return a.getBoundsInLocal().getWidth() != -1;
        } catch (Exception e) {
            //If the objects can't be changed to shapes, make a shape with there size and location
            //Then rotate them

            //Gets the real location and size of the first object
            double rectX = ((Node) block1).getLayoutX() + ((Node) block1).getTranslateX();
            double rectY = ((Node) block1).getLayoutY() + ((Node) block1).getTranslateY();
            double rectWidth = ((Node) block1).getBoundsInLocal().getWidth();
            double rectHeight = ((Node) block1).getBoundsInLocal().getHeight();

            //Gets the real location and sizr of the second object
            double rectX2 = ((Node) block2).getLayoutX() + ((Node) block2).getTranslateX();
            double rectY2 = ((Node) block2).getLayoutY() + ((Node) block2).getTranslateY();
            double rectWidth2 = ((Node) block2).getBoundsInLocal().getWidth();
            double rectHeight2 = ((Node) block2).getBoundsInLocal().getHeight();

            //makes two new shapes and rotates them
            Shape rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
            Shape rect2 = new Rectangle(rectX2, rectY2, rectWidth2, rectHeight2);
            rect.setRotate(((Node) ((Node) block1)).getRotate());
            rect2.setRotate(((Node) block2).getRotate());
            //Makes a new shapes of where they touch
            Shape a = Shape.intersect(rect, rect2);

            //returns if they touch
            return a.getBoundsInLocal().getWidth() != -1;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        walls.add(wall5);
        walls.add(wall6);
        walls.add(wall7);
        walls.add(wall8);
        walls.add(wall9);
        walls.add(wall10);
        walls.add(wall11);
        walls.add(wall12);
        walls.add(wall13);
        walls.add(wall14);
        walls.add(wall15);
        walls.add(wall16);
        walls.add(wall17);
        walls.add(wall18);
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
    }

}
