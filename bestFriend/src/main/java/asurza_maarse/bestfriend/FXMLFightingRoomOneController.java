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
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;

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

    // Declaration of all of the boundss, so they may be used in the collision loop
    //@FXML Rectangle bounds1, bounds2, bounds3, bounds4, bounds5, bounds6, bounds7, bounds8, bounds9, bounds10, bounds11, bounds12, bounds13, bounds14, bounds15;

// Temporary player & area for the player to collide with to go to the next room
    @FXML
    private Circle cPlayer, cDoor;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblRoomNum, lblWeaponEquipped;

    @FXML
    private Button btnKey1, btnKey2, btnKey3, btnOtherItem;

    @FXML
    private Polygon wall;

    @FXML
    private GridPane gpUser, gpMenuBar, gpInventory;

    @FXML
    private ImageView imgAtkUp, imgAtkDown, imgAtkLeft, imgAtkRight;

    // Array of all the Rectangles, to simplify collision
    //Rectangle bounds[];
    
    // Array list of enemies created
    ArrayList<Enemy> enemies = new ArrayList();

    // The Booleans responsible for both moving the user, and for making collision work nicely
    private Boolean up = false, down = false, left = false, right = false, userMoving = false;

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));
    //Timeline spawn = new Timeline(new KeyFrame(Duration.seconds(1), ae -> enemyCreation()));

    Enemy enemy = new Enemy();
    Player player = new Player();

    @FXML
    private void showInventory(ActionEvent evt) {
        gpMenuBar.setVisible(false);
        gpInventory.setVisible(true);
    }

    @FXML
    private void back(ActionEvent evt) {
        gpInventory.setVisible(false);
        gpMenuBar.setVisible(true);
    }

    private void collisionEnemy() {
        if (enemies.size() != 0) {
            for (Enemy e : enemies) {
                if (collision(e, cPlayer)) {
                    player.setHealth(player.getHealth() - e.getDamage());
                } else if (e.getHealth() == 0) {
                    enemies.remove(e); // Don't know if this works yet
                } else if ((collision(e, imgAtkUp)) || (collision(e, imgAtkDown)) || (collision(e, imgAtkLeft)) || (collision(e, imgAtkRight))) {
                    e.setHealth(e.getHealth() - player.getAtk());
                }
            }
        } else {
            return;
        }
    }

//    private boolean collisionLoop() {
//        for (Rectangle i : bounds) {      // Loops through the bounds of the play area, sets each rectangle to 'i' as it goes through
//            if (collision(cPlayer, i)) { // Checks for collision between the user and any of the bounds
//                return true;
//            }
//        }
//        return false;
//    }
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

    private void enemyCreation() {
        int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1); // Determines the type of enemy to spawn
        enemy = new Enemy(rand); // Obtains the characteristics of the random enemy
        enemies.add(enemy);
    }

//        anchorPane.getChildren().add(enemy); // Places the enemy
//        setNewEnemyPosition(enemy);
//    }
//    private void setNewEnemyPosition(Enemy enemy) {        
//        // Places the enemy somewhere on the screen 
//        int rand = ThreadLocalRandom.current().nextInt(89, (89 + 700) + 1); // (Min x-val, (min x-val + width) + 1) 
//        enemy.setLayoutX(rand);
//        rand = ThreadLocalRandom.current().nextInt(241, (241 + 350) + 1); // (Min y-val, (min y-val + height) + 1) 
//        enemy.setLayoutY(rand);
//        // If the enemy lands out of bounds, place them somewhere else until they aren't
//        while ((collision(enemy, cPlayer)) || collision(enemy, wall)) {
//            rand = ThreadLocalRandom.current().nextInt(89, (89 + 700) + 1);
//            enemy.setLayoutX(rand);
//            rand = ThreadLocalRandom.current().nextInt(241, (241 + 350) + 1);
//            enemy.setLayoutY(rand);
//        }       
//    }
    
    @FXML
    private void moveKeyPressed(KeyEvent e) {
        if (null != e.getCode()) {
            switch (e.getCode()) {
                case W:
                    setDirFalse();
                    up = true;
                    break;
                case S:
                    setDirFalse();
                    down = true;
                    break;
                case A:
                    setDirFalse();
                    left = true;
                    break;
                case D:
                    setDirFalse();
                    right = true;
                    break;
                default:
                    break;
            }
        }
        if (!userMoving) {
            userMoving = true;
            tMove.setCycleCount(Timeline.INDEFINITE);
            tMove.play();
        }
    }

    private Circle copy(Circle c) {
        Circle temp = new Circle();
        temp.setLayoutX(c.getLayoutX());
        temp.setLayoutY(c.getLayoutY());
        temp.setRadius(21);
        return temp;
    }

    private void move() {
        Circle temp = copy(cPlayer);
        if (up) {
            temp.setTranslateY(temp.getTranslateY() - 5);
            if (collision(temp, wall)) {
                temp.setTranslateY(temp.getTranslateY() + 5);
                anchorPane.getChildren().remove(temp);
                tMove.stop();
                setDirFalse();
                userMoving = false;
            }
        } else if (down) {
            temp.setTranslateY(temp.getTranslateY() + 5);
            if (collision(temp, wall)) {
                temp.setTranslateY(temp.getTranslateY() - 5);
                anchorPane.getChildren().remove(temp);
                tMove.stop();
                setDirFalse();
                userMoving = false;
            }
        } else if (left) {
            temp.setTranslateX(temp.getTranslateX() - 5);
            if (collision(temp, wall)) {
                temp.setTranslateX(temp.getTranslateX() + 5);
                anchorPane.getChildren().remove(temp);
                tMove.stop();
                setDirFalse();
                userMoving = false;
            }
        } else if (right) {
            temp.setTranslateX(temp.getTranslateX() + 5);
            if (collision(temp, wall)) {
                temp.setTranslateX(temp.getTranslateX() - 5);
                anchorPane.getChildren().remove(temp);
                tMove.stop();
                setDirFalse();
                userMoving = false;
            }
        }

        if (up && !collision(temp, wall)) {
            gpUser.setTranslateY(gpUser.getTranslateY() - 5);
        } else if (down && !collision(temp, wall)) {
            gpUser.setTranslateY(gpUser.getTranslateY() + 5);
        } else if (left && !collision(temp, wall)) {
            gpUser.setTranslateX(gpUser.getTranslateX() - 5);
        } else if (right && !collision(temp, wall)) {
            gpUser.setTranslateX(gpUser.getTranslateX() + 5);
        }

        // If they are colliding with a wall, move them in the opposite direction and stop the movement
    }

//   //<editor-fold defaultstate="collapsed" desc="comment">
//    private void move() {
//        Circle tempPlayer = cPlayer;
//        tempPlayer.setLayoutX(gpUser.getLayoutX() + gpUser.getTranslateX() + 42);
//        tempPlayer.setLayoutY(gpUser.getLayoutY() + gpUser.getTranslateY() + 42);
//        anchorPane.getChildren().add(tempPlayer);
//            if (up) {
//                cPlayer.setTranslateY(cPlayer.getTranslateY() + 7);
//                gpUser.setTranslateY(gpUser.getTranslateY() + 7);
//                setDirFalse();
//            } else if (down) {
//                cPlayer.setTranslateY(cPlayer.getTranslateY() - 7);
//                gpUser.setTranslateY(gpUser.getTranslateY() - 7);
//                setDirFalse();
//            } else if (left) {
//                cPlayer.setTranslateX(cPlayer.getTranslateX() + 7);
//                gpUser.setTranslateX(gpUser.getTranslateX() + 7);
//                setDirFalse();
//            } else if (right) {
//                cPlayer.setTranslateX(cPlayer.getTranslateX() - 7);
//                gpUser.setTranslateX(gpUser.getTranslateX() - 7);
//                setDirFalse();
//            }
//        if (!collision(cPlayer, wall)) {
//            direction();
//        }
//    }
//    private void direction() {
//        if (up) {
//            gpUser.setTranslateY(gpUser.getTranslateY() - 6);
//        } else if (down) {
//            gpUser.setTranslateY(gpUser.getTranslateY() + 6);
//        } else if (left) {
//            gpUser.setTranslateX(gpUser.getTranslateX() - 6);
//        } else if (right) {
//            gpUser.setTranslateX(gpUser.getTranslateX() + 6);
//        } else {
//            setDirFalse();
//        }
//    }
//</editor-fold>
    
    @FXML
    private void moveKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.W
                || e.getCode() == KeyCode.A
                || e.getCode() == KeyCode.S
                || e.getCode() == KeyCode.D) {

            setDirFalse();
            tMove.stop();
            userMoving=false;
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
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
        //spawn.setCycleCount(6);
        //spawn.play();
        //bounds = new Rectangle[]{bounds1, bounds2, bounds3, bounds4, bounds5, bounds6, bounds7, bounds8, bounds9, bounds10, bounds11, bounds12, bounds13, bounds14, bounds15};
    }

}
