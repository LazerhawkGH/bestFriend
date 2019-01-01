package asurza_maarse.bestfriend;

/*
 * Author: Zachary Maarse
 * Date: Dec 12, 2018
 * Purpose: One of the rooms the player can encounter
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author zacharym44
 */

                        /*
                       ////////////////////////////////////////////////////////////////////////
                      ////                      Interaction Plan                          ////
                     ////////////////////////////////////////////////////////////////////////
                    ////                                                                ////
                   ////   Key(s)                                                       ////
                  ////   Weapons                                                      ////
                 ////       - Wooden Knife                                           ////
                ////       - Plastic Knife                                          ////
               ////       - Kitchen Knife                                          ////
              ////       - Dagger                                                 ////
             ////       - Poison Dagger                                          ////
            ////   Health packs/First aid                                       ////
           ////   Lore                                                         ////
          ////       - Notes                                                  ////
         ////       - Flashbacks                                             ////
        ////   Health debuffs                                               ////
       ////       - Rotten food                                            ////
      ////       - Poison                                                 ////
     ////                                                                ////
    ////////////////////////////////////////////////////////////////////////
   ////                                                                ////
  ////////////////////////////////////////////////////////////////////////
*/

public class FXMLFightingRoomOneController implements Initializable {

    // Declaration of all of the walls, so they may be used in the collision loop
    @FXML Rectangle wall1, wall2, wall3, wall4, wall5,
                    wall6, wall7, wall8, wall9, wall10,
                    wall11, wall12, wall13, wall14, wall15;
    
    // Temporary player
    @FXML Circle cPlayer, cDoor; 
    
    
    // The pane that will be moved in the opposite direction of the player to simulate parallax
    @FXML Pane pnParallax;
    
    // Array of all the Rectangles, to simplify collision
    Rectangle walls []; 
    
    // The Booleans responsible for both moving the user, and for making collision work nicely
    private Boolean up = false, down = false, left = false, right = false;
    
    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));
    
    private boolean collisionLoop() {
        for (Rectangle i : walls) {      // Loops through the bounds of the play area, sets each rectangle to 'i' as it goes through
            if (collision(cPlayer, i)) { // Checks for collision between the user and any of the walls
                return true;               
            }
        }
        return false;
    }
    
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
    
     

    @FXML
    private void moveKeyPressed(KeyEvent e) {
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
        if (collisionLoop()) {

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
    private void moveKeyReleased(KeyEvent e){
        if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT){
            setDirFalse();
        }
    }
    
    private void setDirFalse() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        walls = new Rectangle[]{wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8, wall9, wall10, wall11, wall12, wall13, wall14, wall15};
    }    
    
}
