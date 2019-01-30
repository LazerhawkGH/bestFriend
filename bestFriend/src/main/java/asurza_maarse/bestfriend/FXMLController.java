/*
 * Author: Celine Asurza and Zachary Maarse
 * Date: Dec 05, 2018
 * Purpose: (Opening Screen)
 */
package asurza_maarse.bestfriend;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private void btnPlay(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/savingSlots.fxml")); 

        Scene home_page_scene = new Scene(home_page_parent);
        //get reference to the stage 
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.hide(); //optional
        stage.setScene(home_page_scene); //puts the new scence in the stage
        
        stage.setTitle("BestFriend"); //changes the title
        stage.show(); //shows the new page
        home_page_scene.getRoot().requestFocus();
    }

    @FXML
    private void exit(ActionEvent e){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
