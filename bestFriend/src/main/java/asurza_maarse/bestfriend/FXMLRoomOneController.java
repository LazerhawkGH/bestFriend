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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

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

    @FXML
    private void btnMove(KeyEvent evt) {
        if (collisionT()) {
            if (up) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() - 3);
                cPlayer.setTranslateY(cPlayer.getTranslateY() + 3);
            } else if (down) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() + 3);
                cPlayer.setTranslateY(cPlayer.getTranslateY() - 3);
            } else if (left) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() - 3);
                cPlayer.setTranslateX(cPlayer.getTranslateX() + 3);
            } else if (right) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() + 3);
                cPlayer.setTranslateX(cPlayer.getTranslateX() - 3);
            }
        } else {
            if (evt.getCode() == KeyCode.UP) {
                setdirFalse();
                up = true;
            } else if (evt.getCode() == KeyCode.DOWN) {
                setdirFalse();
                down = true;
            } else if (evt.getCode() == KeyCode.LEFT) {
                setdirFalse();
                left = true;
            } else if (evt.getCode() == KeyCode.RIGHT) {
                setdirFalse();
                right = true;
            }
            direction();
        }

    }

    private boolean collisionT() {
        for (int i = 0; i < walls.size(); i++) {
            if(collision(cPlayer, walls.get(i))){
                return true;                
            }
        }
        return false;
    }

    private void setdirFalse() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    private void direction() {
        if (up) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() + 6);
            cPlayer.setTranslateY(cPlayer.getTranslateY() - 6);
        } else if (down) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() - 6);
            cPlayer.setTranslateY(cPlayer.getTranslateY() + 6);
        } else if (left) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() + 6);
            cPlayer.setTranslateX(cPlayer.getTranslateX() - 6);
        } else if (right) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() - 6);
            cPlayer.setTranslateX(cPlayer.getTranslateX() + 6);
        } else {
            setdirFalse();
        }

    }

    public boolean collision(Circle block1, Rectangle block2) {
//returns true if the areas intersect, false if they dont
        return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
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
    }

}
