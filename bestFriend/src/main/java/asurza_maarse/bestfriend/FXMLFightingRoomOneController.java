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
    private ImageView imgUp, imgDown, imgLeft, imgRight, imgAtkUp, imgAtkDown, imgAtkLeft, imgAtkRight;

    

    // Array list of enemies created
    ArrayList<Enemy> enemies = new ArrayList();

    ArrayList<ImageView> item = new ArrayList();

    int xMove, yMove = 0; // Directional variables

    int enemiesDefeated = 0; // Necessary to check if all enemies have been defeated, which opens up the door

    Random rand = new Random();

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(40), ae -> move()));
    Timeline eMove = new Timeline(new KeyFrame(Duration.millis(150), ae -> enemyMovement()));
    Timeline spawn = new Timeline(new KeyFrame(Duration.seconds(1), ae -> enemyCreation()));
    Timeline itemGen = new Timeline(new KeyFrame(Duration.seconds(1), ae -> rdmItemGen()));

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
        if (enemies.size() != 0) {
            for (Enemy e : enemies) {
                if (collision(e, cPlayer)) {
                    //player.setHealth(player.getHealth() - e.getDamage());
                    //lblHealth.setText("" + player.getHealth());
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

    private void items(int i) {

        switch (i) {
            case 1:
                ImageView imgKey = new ImageView("/key.png");
                item.add(0, imgKey);
                anchorPane.getChildren().add(imgKey);
                imgKey.setFitWidth(58);
                imgKey.setFitHeight(58);
                int rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgKey.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgKey.setLayoutY(rand); // Y-coordinate

                break;
            case 2:
                ImageView imgKey2 = new ImageView("/key2.png");
                item.add(1, imgKey2);
                anchorPane.getChildren().add(imgKey2);
                imgKey2.setFitWidth(58);
                imgKey2.setFitHeight(58);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgKey2.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgKey2.setLayoutY(rand); // Y-coordinate
                break;
            case 3:
                ImageView imgKey3 = new ImageView("/key3.png");
                item.add(2, imgKey3);
                anchorPane.getChildren().add(imgKey3);
                imgKey3.setFitWidth(58);
                imgKey3.setFitHeight(58);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgKey3.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgKey3.setLayoutY(rand); // Y-coordinate
                break;
            case 4:
                ImageView imgWKnife = new ImageView("/wooden knife.png");
                item.add(3, imgWKnife);
                anchorPane.getChildren().add(imgWKnife);
                imgWKnife.setFitWidth(54);
                imgWKnife.setFitHeight(62);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgWKnife.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgWKnife.setLayoutY(rand); // Y-coordinate
                break;
            case 5:
                ImageView imgPKnife = new ImageView("/pKnife.png");
                item.add(4, imgPKnife);
                anchorPane.getChildren().add(imgPKnife);
                imgPKnife.setFitWidth(56);
                imgPKnife.setFitHeight(40);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgPKnife.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgPKnife.setLayoutY(rand); // Y-coordinate
                break;
            case 6:
                ImageView imgKKnife = new ImageView("/kKnife.png");
                item.add(5, imgKKnife);
                anchorPane.getChildren().add(imgKKnife);
                imgKKnife.setFitWidth(74);
                imgKKnife.setFitHeight(35);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgKKnife.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgKKnife.setLayoutY(rand); // Y-coordinate
                break;
            case 7:
                ImageView imgDagger = new ImageView("/dagger.png");
                item.add(6, imgDagger);
                anchorPane.getChildren().add(imgDagger);
                imgDagger.setFitWidth(52);
                imgDagger.setFitHeight(52);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgDagger.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgDagger.setLayoutY(rand); // Y-coordinate
                break;
            case 8:
                ImageView imgPDagger = new ImageView("/pDagger.png");
                item.add(7, imgPDagger);
                anchorPane.getChildren().add(imgPDagger);
                imgPDagger.setFitWidth(52);
                imgPDagger.setFitHeight(52);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgPDagger.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgPDagger.setLayoutY(rand); // Y-coordinate
                break;
            case 9:
                ImageView imgHealth = new ImageView("/health.png");
                item.add(8, imgHealth);
                anchorPane.getChildren().add(imgHealth);
                imgHealth.setFitWidth(51);
                imgHealth.setFitHeight(51);
                rand = ThreadLocalRandom.current().nextInt(182, (182 + 544) + 1); // (Min x-val, (min x-val + width) + 1) 
                imgHealth.setLayoutX(rand); // X-coordinate

                rand = ThreadLocalRandom.current().nextInt(242, (242 + 351) + 1); // (Min y-val, (min y-val + height) + 1) 
                imgHealth.setLayoutY(rand); // Y-coordinate
                break;
        }
    }

    private void rdmItemGen() {
        int rand = ThreadLocalRandom.current().nextInt(1, 90 + 1);

        if (rand >= 1 && rand <= 10) {
            items(1);
        } else if (rand >= 11 && rand <= 20) {
            items(2);
        } else if (rand >= 21 && rand <= 30) {
            items(3);
        } else if (rand >= 31 && rand <= 40) {
            items(4);
        } else if (rand >= 41 && rand <= 50) {
            items(5);
        } else if (rand >= 51 && rand <= 60) {
            items(6);
        } else if (rand >= 61 && rand <= 70) {
            items(7);
        } else if (rand >= 71 && rand <= 80) {
            items(8);
        } else if (rand >= 81 && rand <= 90) {
            items(9);
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
        while ((collision(enemy, cPlayer)) || collision(enemy, wall) || (collision(enemy, enemies))) { // (collision(enemy, enemies)) is to prevent them from spawning on top of each other
            setNewEnemyPosition(enemy); // Will continuously run this function until the enemy is in a spot that doesn't trigger any of the conditions
        }
        enemies.add(enemy); // Adds the enemy to the ArrayList after completion
    }

    private void enemyMovement() {
        if (!enemies.isEmpty()) { // While there are enemies in the ArrayList
            for (Enemy e : enemies) { // Loop through each enemy
                if (!collision(e, wall) || !(collision(e, enemies))) { // Make sure they aren't colliding with any walls or with any other enemies
                    if (e.getTranslateX() < (gpUser.getTranslateX() + 42)) { // If the x-val of the enemy is less than that of the player, increase it
                        e.setTranslateX(e.getTranslateX() + 5);
                        directions[0] = false;
                        directions[1] = false;
                        directions[2] = true;
                        directions[3] = false;
                    }
                    if (e.getTranslateX() > (gpUser.getTranslateX() + 42)) { // If the x-val of the enemy is greater than that of the player, decrease it
                        e.setTranslateX(e.getTranslateX() - 5);
                        directions[0] = false;
                        directions[1] = true;
                        directions[2] = false;
                        directions[3] = false;
                    }
                    if (e.getTranslateY() < (gpUser.getTranslateY() + 42)) { // If the y-val of the enemy is less than that of the player, increase it
                        e.setTranslateY(e.getTranslateY() + 5);
                        directions[0] = false;
                        directions[1] = false;
                        directions[2] = false;
                        directions[3] = true;
                    }
                    if (e.getTranslateY() > (gpUser.getTranslateY() + 42)) { // If the y-val of the enemy is greater than that of the player, decrease it
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
            collisionEnemy();
        }
    }
                                              //Up(0), Left(1), Right(2), Down(3) // 
    private boolean[] directions = new boolean[]{false, false, false, false};

    private void rdmEnemyMovement(Enemy e) {
        if (collision(e, wall)) {
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
            int choice1 = rand.nextInt(4);
            directions[choice1] = true;
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
        imgAtkUp.setVisible(false);
        imgAtkDown.setVisible(false);
        imgAtkLeft.setVisible(false);
        imgAtkRight.setVisible(false);
    }
    
    @FXML
    private void moveKeyPressed(KeyEvent e) {
        if (null != e.getCode()) {
            switch (e.getCode()) {
                case W:
                    setImgVisibleFalse();
                    imgUp.setVisible(true);
                    imgAtkUp.setVisible(true);
                    yMove = -5;
                    xMove = 0;
                    break;
                case S:
                    setImgVisibleFalse();
                    imgDown.setVisible(true);
                    imgAtkDown.setVisible(true);
                    yMove = 5;
                    xMove = 0;
                    break;
                case A:
                    setImgVisibleFalse();
                    imgLeft.setVisible(true);
                    imgAtkLeft.setVisible(true);
                    xMove = -5;
                    yMove = 0;
                    break;
                case D:
                    setImgVisibleFalse();
                    imgRight.setVisible(true);
                    imgAtkRight.setVisible(true);
                    xMove = 5;
                    yMove = 0;
                    break;
                case ENTER:
                    if (!item.isEmpty()) { // While the item array list has something in it
                        for (ImageView i : item) { // Loop through the array list of items
                            if (collision(cPlayer, i)) { // Check if the player collides with any item 
                                if (i.equals(item.get(0))) { // If the player is colliding with a certain item, make the global variable true
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setKey1(true);
                                        item.remove(0);
                                    }

                                } else if (i.equals(item.get(1))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setKey2(true);
                                        item.remove(1);
                                    }
                                    
                                } else if (i.equals(item.get(2))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setKey3(true);
                                        item.remove(2);
                                    }
                                    
                                } else if (i.equals(item.get(3))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setWoodKnife(true);
                                        item.remove(3);
                                    }
                                    
                                } else if (i.equals(item.get(4))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setPlasticKnife(true);
                                        item.remove(4);
                                    }
                                    
                                } else if (i.equals(item.get(5))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setKitchenKnife(true);
                                        item.remove(5);
                                    }
                                    
                                } else if (i.equals(item.get(6))) {
                                    Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
                                    alert.setTitle("Item Confirmation");
                                    alert.setHeaderText("Do you wish to pick up this item?");
                                    ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
                                    ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
                                    alert.getButtonTypes().setAll(yes, no);
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == yes) {
                                        setDagger(true);
                                        item.remove(6);
                                    }
                                    
                                } else if (i.equals(item.get(7))) {
                                    setPoisonDagger(true);
                                    item.remove(7);
                                } else if (i.equals(item.get(8))) {
                                    if (player.getHealth() <= 75) {
                                        player.setHealth(player.getHealth() + 25);
                                    } else if (player.getHealth() > 75) {
                                        player.setHealth(100);
                                    }
                                    item.remove(8);
                                }
                            }
                        }
                    }
                default:
                    break;
            }
        }
    }

    private void itemConfirmation(){
       Alert alert = new Alert(AlertType.CONFIRMATION); // opens up dialog box asking user if they want to exit to the main menu
            alert.setTitle("Item Confirmation");
            alert.setHeaderText("Do you wish to pick up this item?");
            ButtonType yes = new ButtonType("YES"); // pressing yes will put this button into a variable called "result"
            ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE); // if escape is pressed again, or they press this, it will close the alert
            alert.getButtonTypes().setAll(yes, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
        eMove.setCycleCount(Timeline.INDEFINITE);
        eMove.play();
        spawn.setCycleCount(rand.nextInt(6));
        spawn.play();
        itemGen.setCycleCount(1);
        itemGen.play();
        player.setHealth(player.getHealth());
        lblHealth.setText("" + player.getHealth());
        //bounds = new Rectangle[]{bounds1, bounds2, bounds3, bounds4, bounds5, bounds6, bounds7, bounds8, bounds9, bounds10, bounds11, bounds12, bounds13, bounds14, bounds15};
    }

}
