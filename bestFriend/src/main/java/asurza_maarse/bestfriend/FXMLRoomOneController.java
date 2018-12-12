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
    private Rectangle wall1;

    @FXML
    private Rectangle recTop, recBottom, recLeft, recRight;

    private Boolean up = false, down = false, left = false, right = false;

    @FXML
    private void btnMove(KeyEvent evt) {
        if (collision(cPlayer, wall1)) {
            if (up) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() - 5);
                cPlayer.setTranslateY(cPlayer.getTranslateY() + 5);
            } else if (down) {
                pnParallax.setTranslateY(pnParallax.getTranslateY() + 5);
                cPlayer.setTranslateY(cPlayer.getTranslateY() - 5);
            } else if (left) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() - 5);
                cPlayer.setTranslateX(cPlayer.getTranslateX() + 5);
            } else if (right) {
                pnParallax.setTranslateX(pnParallax.getTranslateX() + 5);
                cPlayer.setTranslateX(cPlayer.getTranslateX() - 5);
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

    private void setdirFalse() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    private void direction() {
        if (up) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() + 5);
            cPlayer.setTranslateY(cPlayer.getTranslateY() - 5);
        } else if (down) {
            pnParallax.setTranslateY(pnParallax.getTranslateY() - 5);
            cPlayer.setTranslateY(cPlayer.getTranslateY() + 5);
        } else if (left) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() + 5);
            cPlayer.setTranslateX(cPlayer.getTranslateX() - 5);
        } else if (right) {
            pnParallax.setTranslateX(pnParallax.getTranslateX() - 5);
            cPlayer.setTranslateX(cPlayer.getTranslateX() + 5);
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
        // TODO
    }

}
