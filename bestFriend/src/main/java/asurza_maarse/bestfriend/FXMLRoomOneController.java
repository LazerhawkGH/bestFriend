package asurza_maarse.bestfriend;

/*
 * Author: Zachary Maarse and Celine Asurza
 * Date: Dec 7, 2018
 * Purpose: Initial starting room
 */
import java.io.IOException;
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

/**
 * FXML Controller class
 *
 * @author zacharym44
 */
public class FXMLRoomOneController implements Initializable {

    @FXML
    private Pane pan;

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
    private Rectangle rSave, rBear, rEntrance1, rEntrance2, rEntrance3, rEntrance4;

    @FXML
    private ImageView imgUser;

    @FXML
    private Rectangle rSwitch;

    @FXML
    private Circle cPlayer;

    @FXML
    private Rectangle recTop, recBottom, recLeft, recRight;

    @FXML
    private Polygon wall;

    private boolean r1, r2, r3;

    ArrayList<Shape> walls = new ArrayList();
    ArrayList<Rectangle> entrances = new ArrayList();

    private Boolean up = false, down = false, left = false, right = false;

    Timeline tMove = new Timeline(new KeyFrame(Duration.millis(20), ae -> move()));

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
    private void btnMove(KeyEvent e) {
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

    private void move() {
        if (collisionT()) {
            if (collision(cPlayer, rSave)) {

            } else if (collision(cPlayer, rBear)) {

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
                cPlayer.setTranslateY(cPlayer.getTranslateY() + 3);
                setDirFalse();
            } else if (down) {
                cPlayer.setTranslateY(cPlayer.getTranslateY() - 3);
                setDirFalse();
            } else if (left) {
                cPlayer.setTranslateX(cPlayer.getTranslateX() + 3);
                setDirFalse();
            } else if (right) {
                cPlayer.setTranslateX(cPlayer.getTranslateX() - 3);
                setDirFalse();
            }
        } else if (collision(cPlayer, rSwitch)) {
            if (up) {
                pan.setTranslateY(412);
                cPlayer.setTranslateY(150);
                System.out.println(cPlayer.getTranslateY());

            } else if (down) {
                pan.setTranslateY(0);
                cPlayer.setTranslateY(-193);
                System.out.println(cPlayer.getTranslateY());
            }

        } else {
            direction();

        }
    }

    private void changeRoom(KeyEvent evt) throws IOException {
        if (r1 || r2 || r3) {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/FXMLFightingRoomOne.fxml")); //where FXMLPage2 is the name of the scene

            Scene home_page_scene = new Scene(home_page_parent);
//get reference to the stage 
            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();

            stage.hide(); //optional
            stage.setScene(home_page_scene); //puts the new scence in the stage

            stage.setTitle("BestFriend"); //changes the title
            stage.show(); //shows the new page
        }
    }

    private void direction() {
        if (up) {
            cPlayer.setTranslateY(cPlayer.getTranslateY() - 2);

        } else if (down) {
            cPlayer.setTranslateY(cPlayer.getTranslateY() + 2);

        } else if (left) {
            cPlayer.setTranslateX(cPlayer.getTranslateX() - 2);

        } else if (right) {
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

    private void setDirFalse() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    private boolean collisionT() {
        for (int i = 0; i < walls.size(); i++) {
            if (collision(cPlayer, walls.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean collisonE() {
        for (int i = 0; i < entrances.size(); i++) {
            if (collision(cPlayer, entrances.get(i))) {
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
        walls.add(rBear);
        entrances.add(rEntrance1);
        entrances.add(rEntrance2);
        entrances.add(rEntrance3);
        entrances.add(rEntrance3);
        entrances.add(rEntrance4);
        tMove.setCycleCount(Timeline.INDEFINITE);
        tMove.play();
    }

}
