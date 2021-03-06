package asurza_maarse.bestfriend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class FXMLRoomTwoController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Rectangle rSave, rPiano, rBook, rBasket, rEntrance1, rEntrance2, rEntrance3, rEntrance4;

    @FXML
    private Circle cPlayer;

    @FXML
    private Rectangle recTop, recBottom, recLeft, recRight;

    @FXML
    private Polygon wall;

    private boolean r1 = false, r2 = false, r3 = false;

    //Interaction
    @FXML
    private Rectangle rInteract;
    @FXML
    private Label lblInteract;

    //Dialog
    @FXML
    private Rectangle rDialog;
    @FXML
    private Label lblDialog;
    @FXML
    private GridPane gpFace;
    //MC expressions
    @FXML
    private ImageView iMAngry, iMNeutralC, iMNeutral, iMCrying, iMCryingC, iMSmiling, iMSmilingC, iMSurprised;

    private boolean diary = false, piano = false;
    private int i = 0;

    //Saving Window
    @FXML
    private Rectangle rSaveW;
    @FXML
    private Label lblSave;
    @FXML
    private Button btnYes, btnNo;

    MediaPlayer player = new MediaPlayer((new Media(getClass().getResource("/Inside Your Head.mp3").toString())));

    ArrayList<Shape> walls = new ArrayList();
    ArrayList<Rectangle> entrances = new ArrayList();

    private Boolean up = false, down = false, left = false, right = false;

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

    @FXML
    private void btnSYes(ActionEvent evt) {
        saveWVisibleFalse();
    }

    @FXML
    private void btnSNo(ActionEvent evt) {
        saveWVisibleFalse();
    }

    @FXML
    private void btnMov(KeyEvent e) throws IOException {
        if (!rDialog.isVisible() || !rInteract.isVisible()) {

            if (r1 || r2 || r3) {
                player.stop();

                Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/FXMLRoomThree.fxml")); 

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
        if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
            //if(){
            //interactVisibleFalse();
            //dialogVisibleFalse();
            //}
            if (diary) {
                diary();
            } else if (piano){
                piano();
            } else {
                interactVisibleFalse();
                dialogVisibleFalse();
            }

        }
    }

    private void piano() {
        switch (i) {
            case 1:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\nI remember BF used to play piano all the time...");
                i = 2;
                break;
                
            case 2:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\nThe music sheets are all on the floor.");
                i = 0;
                piano = false;
                break;
        }
    }

    private void diary() {
        switch (i) {
            case 1:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\nA Book?\nWhy is there a book down here?");
                i = 2;
                break;

            case 2:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\nOh, it seems to be a diary...\n*Flips to last entry*");
                i = 3;
                break;
            case 3:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\n*Reads diary*\n\"I've hid in this cell, hoping he wouldn't notice I was\ngone. I think he knows now...\"");
                i = 4;
                break;

            case 4:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\n\"For anyone who might read this.\nSTAY AWAY FROM---\"");
                i = 5;
                break;
            case 5:
                interactVisibleFalse();
                dialogVisibleTrue();
                setExpressionFalse();
                iMNeutral.setVisible(true);
                lblDialog.setText("["+MainApp.getPlayerName()+"]\n(The rest is illegible)\nI wonder who HE is...");
                i = 0;
                diary = false;
                break;
            default:
                break;

        }

    }

    private void move() {
        if (collisionT()) {
            if (collision(cPlayer, rSave)) {
                saveWVisibleTrue();
            } else if (collision(cPlayer, rBook)) {
                interactVisibleTrue();
                lblInteract.setText("A Book");
                diary = true;
                i = 1;
                //lblInteract.setText("A Teddy Bear");

            } else if (collision(cPlayer, rPiano)) {
                interactVisibleTrue();
                lblInteract.setText("A Piano");
                piano = true;
                i = 1;
                //lblInteract.setText("A Teddy Bear");

            } else if (collision(cPlayer, rBasket)) {
                interactVisibleTrue();
                lblInteract.setText("A Basket");
                //lblInteract.setText("A Teddy Bear");

            } else if (collisonE()) {
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
        } else {
            direction();

        }
    }

    private void saveWVisibleTrue() {
        rSaveW.setVisible(true);
        lblSave.setVisible(true);
        btnYes.setVisible(true);
        btnNo.setVisible(true);
    }

    private void saveWVisibleFalse() {
        rSaveW.setVisible(false);
        lblSave.setVisible(false);
        btnYes.setVisible(false);
        btnNo.setVisible(false);
    }

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

    private void interactVisibleTrue() {
        rInteract.setVisible(true);
        lblInteract.setVisible(true);
        //lblInteract.setText("");
    }

    private void interactVisibleFalse() {
        rInteract.setVisible(false);
        lblInteract.setVisible(false);
    }

    private void dialogVisibleTrue() {
        rDialog.setVisible(true);
        lblDialog.setVisible(true);
        gpFace.setVisible(true);
    }

    private void dialogVisibleFalse() {
        rDialog.setVisible(false);
        lblDialog.setVisible(false);
        gpFace.setVisible(false);
    }

    private void setExpressionFalse() {
        iMAngry.setVisible(false);
        iMNeutralC.setVisible(false);
        iMNeutral.setVisible(false);
        iMCrying.setVisible(false);
        iMCryingC.setVisible(false);
        iMSmiling.setVisible(false);
        iMSmilingC.setVisible(false);
        iMSurprised.setVisible(false);
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
        walls.add(rSave);
        walls.add(rPiano);
        walls.add(rBasket);
        walls.add(rBook);
        entrances.add(rEntrance1);
        entrances.add(rEntrance2);
        entrances.add(rEntrance3);
        entrances.add(rEntrance4);
        dialogVisibleTrue();
        setExpressionFalse();
        saveWVisibleFalse();
        iMNeutral.setVisible(true);
        lblDialog.setText("["+MainApp.getPlayerName()+"]\n...");
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();

        player.play();
        player.setVolume(0.3);
    }

}
