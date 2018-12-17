package asurza_maarse.bestfriend;

/*
 * Author: Zachary Maarse
 * Date: Dec 12, 2018
 * Purpose: One of the rooms the player can encounter
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
    
    
    
    private boolean collisionLoop() {
        for (Rectangle i : walls) {      // Loops through the bounds of the play area, sets each rectangle to 'i' as it goes through
            if (collision(cPlayer, i)) { // Checks for collision between the user and any of the walls
                return true;               
            }
        }
        return false;
    }
    
    //Handles collision between a Circle and a rectangle
    public boolean collision(Circle block1, Rectangle block2) {
        //returns true if the areas intersect, false if they dont
        return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
    }
    
    @FXML
    private void move(KeyEvent evt) {
        if (collisionLoop()) {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        walls = new Rectangle[]{wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8, wall9, wall10, wall11, wall12, wall13, wall14, wall15};
    }    
    
}
