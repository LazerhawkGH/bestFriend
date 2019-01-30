package asurza_maarse.bestfriend;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("FieldMayBeFinal")
public class MainApp extends Application {
   
    private static Boolean hasKey1 = false;
    private static Boolean hasKey2 = false;
    private static Boolean hasKey3 = false;
    private static Boolean hasWoodKnife = false;
    private static Boolean hasPlasticKnife = false;
    private static Boolean hasKitchenKnife = false;
    private static Boolean hasDagger = false;
    private static Boolean hasPoisonDagger = false;
    
    private static Boolean inPuzzleOne = false;
    private static Boolean inPuzzleTwo = false;
    private static Boolean inPuzzleThree = false;
    private static Boolean inSaveRoom = false;
    
    private static String playerName = "";
    
    private static int roomsCleared = 0;
    
    public static void setPlayerName(String pName){
        MainApp.playerName = pName;
    }
    
    public static String getPlayerName(){
        return playerName;
    }
    
    
        
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        
        Scene scene = new Scene(root);
        stage.setTitle("BestFriend");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.show();
        scene.getRoot().requestFocus();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Global Variables">
    public static Boolean getKey1(){
        return hasKey1;
    }
    public static void setKey1(boolean hk1){
        MainApp.hasKey1 = hk1;
    }
    
    public static Boolean getKey2(){
        return hasKey2;
    }
    public static void setKey2(boolean hk2){
        MainApp.hasKey2 = hk2;
    }
    
    public static Boolean getKey3(){
        return hasKey3;
    }
    public static void setKey3(boolean hk3){
        MainApp.hasKey3 = hk3;
    }
    
    public static Boolean getWoodKnife(){
        return hasWoodKnife;
    }
    public static void setWoodKnife(boolean wk){
        MainApp.hasWoodKnife = wk;
    }
    
    public static Boolean getPlasticKnife(){
        return hasPlasticKnife;
    }
    public static void setPlasticKnife(boolean pk){
        MainApp.hasPlasticKnife = pk;
    }
    
    public static Boolean getKitchenKnife(){
        return hasKitchenKnife;
    }
    public static void setKitchenKnife(boolean kk){
        MainApp.hasKitchenKnife = kk;
    }
    
    public static Boolean getDagger(){
        return hasDagger;
    }
    public static void setDagger(boolean d){
        MainApp.hasDagger = d;
    }
    
    public static Boolean getPoisonDagger(){
        return hasPoisonDagger;
    }
    public static void setPoisonDagger(boolean pd){
        MainApp.hasPoisonDagger = pd;
    }
    
    public static Boolean getPuzzleOne(){
        return inPuzzleOne;
    }
    public static void setPuzzleOne(boolean pz1){
        MainApp.inPuzzleOne = pz1;
    }
    
    public static Boolean getPuzzleTwo(){
        return inPuzzleTwo;
    }
    public static void setPuzzleTwo(boolean pz2){
        MainApp.inPuzzleTwo = pz2;
    }
    
    public static Boolean getPuzzleThree(){
        return inPuzzleThree;
    }
    public static void setPuzzleThree(boolean pz3){
        MainApp.inPuzzleThree = pz3;
    }
    
    public static Boolean getSaveRoom(){
        return inSaveRoom;
    }
    public static void setSaveRoom(boolean sr){
        MainApp.inSaveRoom = sr;
    }
    
    public static int getRoomsCleared(){
        return roomsCleared;
    }
    public static void setRoomsCleared(int rc){
        MainApp.roomsCleared = rc;
    }
//</editor-fold> // Huge chunk of code containing all of the global variables, click [+] to view them all
    
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }

}
