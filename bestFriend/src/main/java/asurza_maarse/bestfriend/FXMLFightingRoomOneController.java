
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
                   ////   Key(s) x 3                                                   ////
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
    private Label lblRoomNum, lblWeaponEquipped, lblHealth;

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
    /*private Boolean up = false, down = false, left = false, right = false, userMoving = false;*/
    
    int xMove, yMove = 0; // Directional variables
    
    int enemiesDefeated = 0; // Necessary to check if all enemies have been defeated, which opens up the door
    
    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));
    Timeline eMove = new Timeline(new KeyFrame(Duration.millis(100), ae -> enemyMovement()));
    Timeline spawn = new Timeline(new KeyFrame(Duration.seconds(1), ae -> enemyCreation()));

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
                    lblHealth.setText("" + player.getHealth());
                } else if (e.getHealth() == 0) {
                    
                    enemies.remove(e); // Don't know if this works yet
                    anchorPane.getChildren().remove(e);
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

    public boolean collision(Object ob1, ArrayList array) {
        for (Object ob2 : array) { // Runs through the specified array
            if (collision(ob2, ob1)) { // Checks for collision between any of the objects in the array list
                return true;
            }
        }
        return false;
    }

    private void items(int i){
       switch(i){
           case 1:
               anchorPane.getChildren().add(new ImageView(""));
               break;
           case 2:
               
       }
    }    
    
    private void rdmItemGen(){
        int rand = ThreadLocalRandom.current().nextInt(1, 120 + 1);
        
        if (rand >= 1 && rand < 20){
            items(1);
        }else if (rand >=20 && rand < 30){
            
        }
        
    }
    
    private void enemyCreation() {
        int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1); // Determines the type of enemy to spawn
        enemy = new Enemy(rand); // Obtains the characteristics of the random enemy            
        anchorPane.getChildren().add(enemy); // Places the enemy

        // Moves the enemy to a random spot on the screen        
        rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
        enemy.setLayoutX(rand); // X-coordinate
        System.out.println("x: " + rand);

        rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
        enemy.setLayoutY(rand); // Y-coordinate
        System.out.println("y: " + rand + "\n");

        // If the enemy was placed out of bounds, run the function to relocate the enemy until they are no longer out of bounds
        while ((collision(enemy, cPlayer)) || collision(enemy, wall) || (collision(enemy, enemies))) { // (collision(enemy, enemies)) is to prevent them from spawning on top of each other
            setNewEnemyPosition(enemy); // Will continuously run this function until the enemy is in a spot that doesn't trigger any of the conditions
        }
        enemies.add(enemy); // Adds the enemy to the ArrayList after completion
    }

    private void enemyMovement() {
        if (!enemies.isEmpty()) { // While there are enemies in the ArrayList
            for (Enemy e : enemies) { // Loop through each enemy
                if (!collision(e, wall)) { // Make sure they aren't colliding with any walls
                    if ((e.getLayoutX() + e.getTranslateX()) < ((gpUser.getLayoutX() + gpUser.getTranslateX()) + 42)) { // If the x-val of the enemy is less than that of the player, increase it
                        e.setTranslateX(e.getTranslateX() + 5);
                    }
                    if ((e.getLayoutX() + e.getTranslateX()) > ((gpUser.getLayoutX() + gpUser.getTranslateX()) + 42)) { // If the x-val of the enemy is greater than that of the player, decrease it
                        e.setTranslateX(e.getTranslateX() - 5);
                    }
                    if ((e.getLayoutY() + e.getTranslateY()) < ((gpUser.getLayoutX() + gpUser.getTranslateX()) + 42)) { // If the y-val of the enemy is less than that of the player, increase it
                        e.setTranslateY(e.getTranslateY() + 5);
                    }
                    if ((e.getLayoutY() + e.getTranslateY()) > ((gpUser.getLayoutX() + gpUser.getTranslateX()) + 42)) { // If the y-val of the enemy is greater than that of the player, decrease it
                        e.setTranslateY(e.getTranslateY() - 5);
                    }
                }else{
                    System.out.println("yeet");
                }
            }
        }
    }

    private void setNewEnemyPosition(Enemy enemy) {
        // Places the enemy somewhere else on the screen 
        int rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
        enemy.setLayoutX(rand);
        System.out.println("New x: " + rand);

        rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
        enemy.setLayoutY(rand);
        System.out.println("New y: " + rand + "\n");

        System.out.println("Yes" + "\n");

    }

    @FXML
    private void moveKeyPressed(KeyEvent e) {
        if (null != e.getCode()) {
            switch (e.getCode()) {
                case W:
                    yMove = -5;
                    xMove = 0;
                    break;
                case S:
                    yMove = 5;
                    xMove = 0;
                    break;
                case A:
                    xMove = -5;
                    yMove = 0;
                    break;
                case D:
                    xMove = 5;
                    yMove = 0;
                    break;
                default:
                    break;
            }
        }

    }

    private Circle copy(Circle c) { // Handles the creation of the temporary player, used to check for collision before moving actual player
        Circle temp = new Circle();
        temp.setLayoutX(63 + gpUser.getLayoutX() + gpUser.getTranslateX());
        temp.setLayoutY(63 + gpUser.getLayoutY() + gpUser.getTranslateY());
        temp.setRadius(21);
        return temp;
    }

    private void move() {
        Circle temp = copy(cPlayer); // A temporary copy of the player is made
        anchorPane.getChildren().add(temp); // The copy is placed on the anchorpane

        temp.setTranslateX(temp.getTranslateX() + xMove); // Temporary player is moved before the original
        temp.setTranslateY(temp.getTranslateY() + yMove);

        if (!collision(temp, wall)) { // If the temporary player hasn't collided with a wall, move the original
            gpUser.setTranslateX(gpUser.getTranslateX() + xMove);
            gpUser.setTranslateY(gpUser.getTranslateY() + yMove);

            //System.out.println("Not colliding"); // For debugging purposes
        } else {
            //System.out.println("Colliding"); // For debugging purposes
        }
        anchorPane.getChildren().remove(temp); // After going through once, the temporary player is removed, and garbage collector throws it out
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
//</editor-fold> // Former movement code
    
    @FXML
    private void moveKeyReleased(KeyEvent e) {
        // If the user stops pressing a movement key, stop the movement
        if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.A || e.getCode() == KeyCode.S || e.getCode() == KeyCode.D) {
            xMove = 0;
            yMove = 0;
        }
    }

//    private void setDirFalse() {
//       /* up = false;
//        down = false;
//        left = false;
//        right = false;*/
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
        eMove.setCycleCount(Timeline.INDEFINITE);
        eMove.play();
        spawn.setCycleCount(6);
        spawn.play();
        
        //bounds = new Rectangle[]{bounds1, bounds2, bounds3, bounds4, bounds5, bounds6, bounds7, bounds8, bounds9, bounds10, bounds11, bounds12, bounds13, bounds14, bounds15};
    }

}

