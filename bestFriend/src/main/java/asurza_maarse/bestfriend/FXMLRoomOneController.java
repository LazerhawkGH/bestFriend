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
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author zacharym44
 */
public class FXMLRoomOneController implements Initializable {

    @FXML
    Pane pnParallax;
    
    @FXML
    ImageView imgUser;
    
    @FXML
    Rectangle recTop, recBottom, recLeft, recRight;
    
    @FXML
    private void btnMove(KeyEvent evt){
        if(evt.getCode() == KeyCode.ENTER){
            pnParallax.setTranslateY(pnParallax.getTranslateY() + 20);
        }      
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
