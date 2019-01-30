/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asurza_maarse.bestfriend;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.shape.Shape;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author user
 */
public class PuzzleRoom1Controller implements Initializable {

    @FXML
    private ImageView imgWon;
    
    @FXML
    private Button btnContinue, btnExit;
    @FXML
    private Label lblYouDied;

    @FXML
    private GridPane gp1;
    @FXML
    private Label lblRoomNum;
    @FXML
    private Button btnInventory;
    @FXML
    private Label lblHealth;

    @FXML
    private GridPane gp2;
    @FXML
    private Label lblWeaponEquiped;
    @FXML
    private Button btnKey1;
    @FXML
    private Button btnKey2;
    @FXML
    private Button btnKey3;
    @FXML
    private Button btnOtherItem;
    @FXML
    private Button btnBack;

    @FXML
    private GridPane gpPlayer;
    @FXML
    private ImageView imgUp, imgDown, imgLeft, imgRight;

    @FXML
    private Rectangle rSave, rVase, rFall, rOof, rDeath, rEntrance;

    @FXML
    private ImageView imgBox;

    @FXML
    private Circle cPlayer;

    @FXML
    private Rectangle recTop, recBottom, recLeft, recRight;

    @FXML
    private Polygon wall;

    private boolean r1 = false, r2 = false, r3 = false;
    private int i = 0;

    private boolean fall = false, won = false, loss = false;

    //Saving Window
//    @FXML
//    private Rectangle rSaveW;
//    @FXML
//    private Label lblSave;
//    @FXML
//    private Button btnYes, btnNo;
    MediaPlayer player = new MediaPlayer((new Media(getClass().getResource("/Caspro - Dark Digital.mp3").toString())));
    MediaPlayer scream = new MediaPlayer((new Media(getClass().getResource("/Scream.mp3").toString())));

    ArrayList<Shape> walls = new ArrayList();
    ArrayList<Rectangle> entrances = new ArrayList();

    private Boolean up = false, down = false, left = false, right = false;

    @FXML
    private GridPane gpEnemy;

    @FXML
    private ImageView imgELeft, imgERight, imgEDown;

    private int eDir = 0;

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(15), ae -> move()));

    @FXML
    private void btnShowInvent(ActionEvent evt) {
        gp1.setVisible(false);
        gp2.setVisible(true);
    }

    @FXML
    private void btnGoBack(ActionEvent evt) {
        gp2.setVisible(false);
        gp1.setVisible(true);
    }

//    @FXML
//    private void btnSYes(ActionEvent evt) {
//        saveWVisibleFalse();
//    }
//
//    @FXML
//    private void btnSNo(ActionEvent evt) {
//        saveWVisibleFalse();
//    }
    
    @FXML
    private void btnContinue(ActionEvent e) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/PuzzleRoom1.fxml")); //where FXMLPage2 is the name of the scene

            Scene home_page_scene = new Scene(home_page_parent);
//get reference to the stage 
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            stage.hide(); //optional
            stage.setScene(home_page_scene); //puts the new scence in the stage

            stage.setTitle("BestFriend"); //changes the title
            stage.show(); //shows the new page

            home_page_scene.getRoot().requestFocus();
    }
    
    @FXML
    private void btnExit(ActionEvent e){
        System.exit(0);
    }
    
    @FXML
    private void btnMov(KeyEvent e) throws IOException {

        if (r1 || r2 || r3) {
            player.stop();

            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml")); 

            Scene home_page_scene = new Scene(home_page_parent);
//get reference to the stage 
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            stage.hide(); //optional
            stage.setScene(home_page_scene); //puts the new scence in the stage

            stage.setTitle("BestFriend"); //changes the title
            stage.show(); //shows the new page

            home_page_scene.getRoot().requestFocus();

        }
        if (e.getCode() == KeyCode.W) {
            setDirFalse();
            up = true;
        } else if (e.getCode() == KeyCode.S) {
            setDirFalse();
            down = true;
        } else if (e.getCode() == KeyCode.A) {
            setDirFalse();
            left = true;
        } else if (e.getCode() == KeyCode.D) {
            setDirFalse();
            right = true;
        }
    }

  

    private void enemyMove() {

        if (gpEnemy.getTranslateX() <= -50) { //Limit on left
            eDir = 3;
            enemyDirection();
        } else if (gpEnemy.getTranslateX() == 620) {// Limit on Right
            eDir = 2;
            enemyDirection();
        } else if (eDir == 2 && collision(cPlayer, rOof)) {
            // If The Enemy's Direction is Left and the Player on the rOof
            //Ememy Speed Increases
            eDir = 4;
            enemyDirection();
        } else {
            enemyDirection();
        }
    }

    private void enemyDirection() {
        if (eDir == 1) { //Down
            setImgEVisibleFalse();
            imgEDown.setVisible(true);
            gpEnemy.setTranslateY(gpEnemy.getTranslateY() + 2);

        } else if (eDir == 2) { //Left
            setImgEVisibleFalse();
            imgELeft.setVisible(true);
            gpEnemy.setTranslateX(gpEnemy.getTranslateX() - 2);

        } else if (eDir == 3) { //Right
            setImgEVisibleFalse();
            imgERight.setVisible(true);
            gpEnemy.setTranslateX(gpEnemy.getTranslateX() + 2);

        } else if (eDir == 4) { //Left Super Speed
            setImgEVisibleFalse();
            imgELeft.setVisible(true);
            gpEnemy.setTranslateX(gpEnemy.getTranslateX() - 7);

        } else if (eDir == 0) {
            gpEnemy.setTranslateX(gpEnemy.getTranslateX() + 0);
            gpEnemy.setTranslateY(gpEnemy.getTranslateY() + 0);
        }
    }

    private void move() {
        if (!loss) {
            if (collisionT()) {
                if (collisonE()) {
                    int rand = ThreadLocalRandom.current().nextInt(1, (1 + 3) + 1);
                    if (rand == 1) {
                        r1 = true;
                    } else if (rand == 2) {
                        r2 = true;
                    } else if (rand == 3) {
                        r3 = true;
                    }
                }
                if (up) {
                    gpPlayer.setTranslateY(gpPlayer.getTranslateY() + 3);
                    cPlayer.setTranslateY(cPlayer.getTranslateY() + 3);
                    setDirFalse();
                } else if (down) {
                    gpPlayer.setTranslateY(gpPlayer.getTranslateY() - 3);
                    cPlayer.setTranslateY(cPlayer.getTranslateY() - 3);
                    setDirFalse();
                } else if (left) {
                    gpPlayer.setTranslateX(gpPlayer.getTranslateX() + 3);
                    cPlayer.setTranslateX(cPlayer.getTranslateX() + 3);
                    setDirFalse();
                } else if (right) {
                    gpPlayer.setTranslateX(gpPlayer.getTranslateX() - 3);
                    cPlayer.setTranslateX(cPlayer.getTranslateX() - 3);
                    setDirFalse();
                }
            } else if (collision(cPlayer, imgBox)) {
                moveBox();
            } else if (collision(imgBox, rFall)) {
                fall = true;
            } else if(collision(cPlayer, gpEnemy)){
                loss = true;
            } else {
                direction();

            }
            enemyMove();
            boxFall();
            win();
            lose();
        }
    }

    private void moveBox() {
        if (up) {
            imgBox.setTranslateY(imgBox.getTranslateY() - 2);
        } else if (left) {
            imgBox.setTranslateX(imgBox.getTranslateX() - 2);
        } else if (right) {
            imgBox.setTranslateX(imgBox.getTranslateX() + 2);
        } else if (down) {
            imgBox.setTranslateY(imgBox.getTranslateY() + 2);
        }
    }

    private void boxFall() {
        if (!won && !loss) {
            if (fall) {
                imgBox.setTranslateY(imgBox.getTranslateY() + 4);
            }
            if (imgBox.getTranslateY() == 170 && collision(gpEnemy, imgBox)) {
                won = true;
            } else if (imgBox.getTranslateY() == 170 && !collision(gpEnemy, imgBox)) {
                loss = true;
            }
        }
    }

    private void win() {
        if (won) {
            imgWon.setVisible(true);
            gpEnemy.setTranslateY(-470);
            imgBox.setTranslateY(-400);
            imgBox.setVisible(false);
        }
    }

    private void lose() {
        if (loss) {
            rDeath.setVisible(true);
            player.stop();
            scream.play();
            tMove.stop();
            tMove.setDelay(Duration.seconds(5));
            tMove.play();
            lblYouDied.setVisible(true);
            btnContinue.setVisible(true);
            btnExit.setVisible(true);
        }
    }

//    private void saveWVisibleTrue() {
//        rSaveW.setVisible(true);
//        lblSave.setVisible(true);
//        btnYes.setVisible(true);
//        btnNo.setVisible(true);
//    }
//
//    private void saveWVisibleFalse() {
//        rSaveW.setVisible(false);
//        lblSave.setVisible(false);
//        btnYes.setVisible(false);
//        btnNo.setVisible(false);
//    }
    private void direction() {
        if (up) {
            setImgVisibleFalse();
            imgUp.setVisible(true);
            gpPlayer.setTranslateY(gpPlayer.getTranslateY() - 2);
            cPlayer.setTranslateY(cPlayer.getTranslateY() - 2);

        } else if (down) {
            setImgVisibleFalse();
            imgDown.setVisible(true);
            gpPlayer.setTranslateY(gpPlayer.getTranslateY() + 2);
            cPlayer.setTranslateY(cPlayer.getTranslateY() + 2);

        } else if (left) {
            setImgVisibleFalse();
            imgLeft.setVisible(true);
            gpPlayer.setTranslateX(gpPlayer.getTranslateX() - 2);
            cPlayer.setTranslateX(cPlayer.getTranslateX() - 2);

        } else if (right) {
            setImgVisibleFalse();
            imgRight.setVisible(true);
            gpPlayer.setTranslateX(gpPlayer.getTranslateX() + 2);
            cPlayer.setTranslateX(cPlayer.getTranslateX() + 2);

        } else {
            setDirFalse();
        }

    }

    @FXML
    private void onRelease(KeyEvent e) {
        if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.S || e.getCode() == KeyCode.A || e.getCode() == KeyCode.D) {
            setDirFalse();
        }
    }

    private void setImgEVisibleFalse() {
        imgELeft.setVisible(false);
        imgERight.setVisible(false);
        imgEDown.setVisible(false);
    }

    private void setImgVisibleFalse() {
        imgUp.setVisible(false);
        imgDown.setVisible(false);
        imgLeft.setVisible(false);
        imgRight.setVisible(false);
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
                System.out.print("col");
                return true;
            }
        }
        return false;
    }

    private boolean collisonE() {
        for (int i = 0; i < entrances.size(); i++) {
            if (collision(cPlayer, entrances.get(i))) {
                System.out.print("ent col");
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
        walls.add(wall);
        walls.add(rVase);
        entrances.add(rEntrance);

        //saveWVisibleFalse();
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
        eDir = 3;
        player.play();
    }

}
