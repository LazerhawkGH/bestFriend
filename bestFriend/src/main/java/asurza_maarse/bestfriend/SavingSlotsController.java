/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 * FXML Controller class
 *
 * @author user
 */
public class SavingSlotsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public void btnSlots(ActionEvent event) throws IOException{
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/FXMLRoomOne.fxml")); //where FXMLPage2 is the name of the scene

        Scene home_page_scene = new Scene(home_page_parent);
//get reference to the stage 
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.hide(); //optional
        stage.setScene(home_page_scene); //puts the new scence in the stage

        stage.setTitle("BestFriend"); //changes the title
        stage.show(); //shows the new page
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
