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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author user
 */
public class SavingSlotsController implements Initializable {

    /**
     * Initializes the controller class.
     *
     */
    
    
    @FXML
    private Rectangle rSaveW, rChoose;
    @FXML
    private Label lblSave, lblChoose;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnYes, btnNo, btnContinue;
    
    PlayerSaving pSaving = new PlayerSaving();

    @FXML
    public void btnContinue(ActionEvent evt) throws IOException {
        String n = txtName.getText();
        MainApp.setPlayerName(n);
        pSaving.setPlayerName(n);
        pSaving.save("file.txt", 0);
        
        
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/fxml/FXMLRoomOne.fxml")); //where FXMLPage2 is the name of the scene

        Scene home_page_scene = new Scene(home_page_parent);
////get reference to the stage 
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
//
        stage.hide(); //optional
        stage.setScene(home_page_scene); //puts the new scence in the stage
//
        stage.setTitle("BestFriend"); //changes the title
        stage.show(); //shows the new page
        home_page_scene.getRoot().requestFocus();

    }
    
    
    @FXML
    public void btnSYes(ActionEvent evt){
        btnYes.setVisible(false);
        btnNo.setVisible(false);
        txtName.setVisible(true);
        lblSave.setText("Name Your Character");
        btnContinue.setVisible(true);
    }
    

    @FXML
    private Button btn1, btn2, btn3;

    private boolean slot1 = false, slot2 = false, slot3 = false;

    public void btnSlots(ActionEvent event){
        Button btn = (Button) event.getSource();
        if (btn == btn1) {
            slot1 = true;
        } else if (btn == btn2) {
            slot2 = true;
        } else if (btn == btn3) {
            slot3 = true;
        }
        btn1.setVisible(false);
        btn2.setVisible(false);
        btn3.setVisible(false);
        rChoose.setVisible(false);
        lblChoose.setVisible(false);
        
        rSaveW.setVisible(true);
        btnYes.setVisible(true);
        btnNo.setVisible(true);
        lblSave.setVisible(true);
        lblSave.setText("Choose Slot 1?");

//        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pSaving.open("file.txt", 0);
        MainApp.setPlayerName(pSaving.getPlayerName());
        if(!MainApp.getPlayerName().equals("")){
        btn1.setText(MainApp.getPlayerName());
        }
    }

}
