package asurza_maarse.bestfriend;

/*
 * Author: Zachary Maarse
 * Date: Dec 12, 2018
 * Purpose: One of the rooms the player can encounter
 */
import static asurza_maarse.bestfriend.MainApp.*;
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
import java.util.Optional;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
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
////   Key(s) x 3   ✓                                               ////
////   Health packs/First aid ✓                                     ////
////   Lore                                                         ////
////       - Notes ✓                                                ////
////       - Flashbacks                                             ////
////                                                                ////
////////////////////////////////////////////////////////////////////////
////                                                                ////
////////////////////////////////////////////////////////////////////////
 */
public class FXMLEscapeRoomTwoController implements Initializable {

    // area for the player to collide with to go to the next room
    @FXML
    private Circle cDoor;
     
    @FXML 
    private Ellipse cPlayer;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label lblRoomNum, lblHealth;

    @FXML
    private Button btnKey1, btnKey2, btnKey3;

    @FXML
    private Polygon wall, wall2, polyExperiment, polyRock, polySkeleton;

    @FXML
    private GridPane gpUser, gpMenuBar, gpInventory;
    
    @FXML
    private ImageView imgUp, imgDown, imgLeft, imgRight, imgHealth;
    
    // Array list of enemies created
    ArrayList<Enemy> enemies = new ArrayList();
    
    Polygon walls[];

   // ArrayList<ImageView> item = new ArrayList();

    int xMove, yMove = 0; // Directional variables

    int enemiesDefeated = 0; // Necessary to check if all enemies have been defeated, which opens up the door

    Random rand = new Random();

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));
    Timeline eMove = new Timeline(new KeyFrame(Duration.millis(150), ae -> enemyMovement()));
    Timeline spawn = new Timeline(new KeyFrame(Duration.seconds(1), ae -> enemyCreation()));
    Timeline itemGen = new Timeline(new KeyFrame(Duration.seconds(1), ae -> rdmItemGen()));
    Timeline enemyAtk = new Timeline(new KeyFrame(Duration.seconds(1), ae -> collisionEnemy()));

    Enemy enemy = new Enemy(); // Handles the Enemy.java class
    Player player = new Player(); // Handles the Player.java class

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
        if (!enemies.isEmpty()) {
            for (Enemy e : enemies) {
                if (collision(e, cPlayer)) {
                    player.setHealth(player.getHealth() - e.getDamage());
                    lblHealth.setText("" + player.getHealth());
                }
            }
        }else {
            return;
        }
    }
    
    private boolean wallCollisionPlayer(Object obj1){
        for (Polygon p: walls){
            if (collision(obj1, p)){
                return true;
            }
        }
        return false;
            
    }
    
    private boolean wallCollisionEnemy(Object obj1){
        for (Polygon p: walls){
            if (collision(obj1, p)){
                return true;
            }
        }
        return false;
            
    }
    
    public boolean itemCollision(Ellipse block1, ImageView block2) {
        //returns true if the areas intersect, false if they dont
        return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
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

    public boolean collision(Object ob1, ArrayList array) {
        for (Object ob2 : array) { // Runs through the specified array
            if (collision(ob2, ob1)) { // Checks for collision between any of the objects in the array list
                return true;
            }
        }
        return false;
    }

    private void items(int i){
        switch (i){
            case 1:
                imgHealth.setFitWidth(42);
                imgHealth.setFitHeight(31);
                
                int rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgHealth.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgHealth.setLayoutY(rand); // Y-coordinate
                
                imgHealth.setVisible(true);
                break;
            default: 
                break;
        }
    }

    private void rdmItemGen() {
        int rand = ThreadLocalRandom.current().nextInt(1, 40 + 1);

        if (rand >= 1 && rand <= 10) {
            items(1);
        } else if (rand >= 11 && rand <= 20) {
            items(1);
        } else if (rand >= 21 && rand <= 30) {
            items(2);
        } else if (rand >= 31 && rand <= 40) {
            items(3);
        }

    }

    private void enemyCreation() {
        int rand = ThreadLocalRandom.current().nextInt(1, 4 + 1); // Determines the type of enemy to spawn
        enemy = new Enemy(rand); // Obtains the characteristics of the random enemy            
        anchorPane.getChildren().add(enemy); // Places the enemy

        // Moves the enemy to a random spot on the screen        
        rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
        enemy.setLayoutX(0); // X-coordinate
        enemy.setTranslateX(rand);
        System.out.println("x: " + rand);

        rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
        enemy.setLayoutY(0); // Y-coordinate
        enemy.setTranslateY(rand);
        System.out.println("y: " + rand + "\n");

        // If the enemy was placed out of bounds, run the function to relocate the enemy until they are no longer out of bounds
        while ((collision(enemy, cPlayer)) || (collision(enemy, enemies)) || wallCollisionEnemy(enemy)) {                                               // (collision(enemy, enemies)) is to prevent them from spawning on top of each other
            setNewEnemyPosition(enemy); // Will continuously run this function until the enemy is in a spot that doesn't trigger any of the conditions
        }
        enemies.add(enemy); // Adds the enemy to the ArrayList after completion
    }

    private void enemyMovement() {
        if (!enemies.isEmpty()) { // While there are enemies in the ArrayList
            for (Enemy e : enemies) { // Loop through each enemy
                if (!wallCollisionEnemy(enemy) || !(collision(e, enemies))) { // Make sure they aren't colliding with any walls or with any other enemies
                    if (e.getTranslateX() < (gpUser.getTranslateX())) { // If the x-val of the enemy is less than that of the player, increase it
                        e.setTranslateX(e.getTranslateX() + 5);
                        directions[0] = false;
                        directions[1] = false;
                        directions[2] = true;
                        directions[3] = false;
                    }
                    if (e.getTranslateX() > (gpUser.getTranslateX())) { // If the x-val of the enemy is greater than that of the player, decrease it
                        e.setTranslateX(e.getTranslateX() - 5);
                        directions[0] = false;
                        directions[1] = true;
                        directions[2] = false;
                        directions[3] = false;
                    }
                    if (e.getTranslateY() < (gpUser.getTranslateY())) { // If the y-val of the enemy is less than that of the player, increase it
                        e.setTranslateY(e.getTranslateY() + 5);
                        directions[0] = false;
                        directions[1] = false;
                        directions[2] = false;
                        directions[3] = true;
                    }
                    if (e.getTranslateY() > (gpUser.getTranslateY())) { // If the y-val of the enemy is greater than that of the player, decrease it
                        e.setTranslateY(e.getTranslateY() - 5);
                        directions[0] = true;
                        directions[1] = false;
                        directions[2] = false;
                        directions[3] = false;
                    }
                } else {
                    rdmEnemyMovement(e);
                    System.out.println("Colliding");
                }
            }
            
        }
    }
                                              //Up(0), Left(1), Right(2), Down(3) // 
    private boolean[] directions = new boolean[]{false, false, false, false};

    private void rdmEnemyMovement(Enemy e) {
        if (wallCollisionEnemy(e)) {
            if (directions[1]) {
                directions[1] = false;
                e.setTranslateX(e.getTranslateX() - 1);

            } else if (directions[2]) {
                directions[2] = false;
                e.setTranslateX(e.getTranslateX() + 1);

            } else if (directions[0]) {
                directions[0] = false;
                e.setTranslateY(e.getTranslateY() - 1);

            } else if (directions[3]) {
                directions[3] = false;
                e.setTranslateY(e.getTranslateY() + 1);

            }
            //Generates a random number, deciding which direction the enemy will move in.
            //This is done upon colliding with a wall,
            int choice = rand.nextInt(4);
            directions[choice] = true;
        } 
        if (directions[1]) {
            e.setTranslateX(e.getTranslateX() + 1);
            return;
        } else if (directions[2]) {
            e.setTranslateX(e.getTranslateX() - 1);
            return;
        } else if (directions[0]) {
            e.setTranslateY(e.getTranslateY() + 1);
            return;
        } else if (directions[3]) {
            e.setTranslateY(e.getTranslateY() - 1);
            return;
        }
    }

    private void setNewEnemyPosition(Enemy enemy) {
        // Places the enemy somewhere else on the screen

        System.out.println("Yes" + "\n");

        int rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
        enemy.setLayoutX(0); // X-coordinate
        enemy.setTranslateX(rand);
        System.out.println("x: " + rand);

        rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
        enemy.setLayoutY(0); // Y-coordinate
        enemy.setTranslateY(rand);
        System.out.println("y: " + rand + "\n");

    }

    private void setImgVisibleFalse() {
        imgUp.setVisible(false);
        imgDown.setVisible(false);
        imgLeft.setVisible(false);
        imgRight.setVisible(false);
    }
    
    @FXML
    private void moveKeyPressed(KeyEvent e) {
        if (null != e.getCode()) {
            switch (e.getCode()) {
                case W:
                    setImgVisibleFalse();
                    imgUp.setVisible(true);
                    yMove = -5;
                    xMove = 0;
                    break;
                case S:
                    setImgVisibleFalse();
                    imgDown.setVisible(true);
                    yMove = 5;
                    xMove = 0;
                    break;
                case A:
                    setImgVisibleFalse();
                    imgLeft.setVisible(true);
                    xMove = -5;
                    yMove = 0;
                    break;
                case D:
                    setImgVisibleFalse();
                    imgRight.setVisible(true);
                    xMove = 5;
                    yMove = 0;
                    break;
                case ENTER:
                    itemPickup();
                default:
                    break;
            }
        }
    }
    
    private void itemPickup() {
        if (itemCollision(cPlayer, imgHealth)) {
            if (player.getHealth() <= 75) {
                player.setHealth(player.getHealth() + 25);
                anchorPane.getChildren().remove(imgHealth);
            } else if (player.getHealth() > 75) {
                player.setHealth(100);
                anchorPane.getChildren().remove(imgHealth);
            }
        } else {
            return;
        }
    }
   
    private Ellipse copy(Ellipse e) { // Handles the creation of the temporary player, used to check for collision before moving actual player
        Ellipse temp = new Ellipse();
        temp.setLayoutX(gpUser.getLayoutX() + gpUser.getTranslateX());
        temp.setLayoutY(gpUser.getLayoutY() + gpUser.getTranslateY());
        temp.setRadiusX(21);
        temp.setRadiusY(24);
        return temp;
    }

    private void move() {
        Ellipse temp = copy(cPlayer); // A temporary copy of the player is made
        anchorPane.getChildren().add(temp); // The copy is placed on the anchorpane

        temp.setTranslateX(temp.getTranslateX() + xMove); // Temporary player is moved before the original
        temp.setTranslateY(temp.getTranslateY() + yMove);

        if (!wallCollisionPlayer(temp)) { // If the temporary player hasn't collided with a wall, move the original
            gpUser.setTranslateX(gpUser.getTranslateX() + xMove);
            gpUser.setTranslateY(gpUser.getTranslateY() + yMove);
        } else {
            return;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        walls = new Polygon[]{wall, wall2, polyExperiment, polyRock, polySkeleton};
        
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
        eMove.setCycleCount(Timeline.INDEFINITE);
        eMove.play();
        enemyAtk.setCycleCount(Timeline.INDEFINITE);
        enemyAtk.play();
        spawn.setCycleCount(rand.nextInt(6));
        spawn.play();
        itemGen.setCycleCount(1);
        itemGen.play();
        player.setHealth(player.getHealth());
        lblHealth.setText("" + player.getHealth());
        
        
        
//        item.add(0,blank);
//        item.add(1,blank);
//        item.add(2,blank);
//        item.add(3,blank);
//        item.add(4,blank);
//        item.add(5,blank);
//        item.add(6,blank);
//        item.add(7,blank);
        
        //bounds = new Rectangle[]{bounds1, bounds2, bounds3, bounds4, bounds5, bounds6, bounds7, bounds8, bounds9, bounds10, bounds11, bounds12, bounds13, bounds14, bounds15};
    }

}
