/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asurza_maarse.bestfriend;

/**
 *
 * @author user
 */
import java.io.IOException;  //Used as we are dealing with Files.   
import java.io.RandomAccessFile;  //Added to give support to Random Access Files

public class PlayerSaving {
    private String playerName; // 8 chars, 16 bytes
    private int roomNumber; //4 bytes
    private int health; //4 bytes
    
    private final int RECSIZE = 24;
    
    public PlayerSaving(){
        playerName = "";
        roomNumber = 0;
        health = 100;
    }
    
    public PlayerSaving(String playerName, int roomNumber, int health){
        
    }
    
    public void setPlayerName(String pName){
        StringBuffer temp = new StringBuffer(pName);
        temp.setLength(8);
        playerName = temp.toString();
    }
    
    public String getPlayerName() {
        return playerName.trim();
    }
    
    public void setRoomNumber(int rNum){
        roomNumber = rNum;
    }
    
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public void setHealth(int h){
        health = h;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void save(String file, int record) {
        try {
            RandomAccessFile recordFile = new RandomAccessFile(file, "rw");
            recordFile.seek(record * RECSIZE);
            recordFile.writeChars(playerName);
            recordFile.writeInt(roomNumber);
            recordFile.writeInt(health);
        } catch (IOException ex) {

        }
    }
    
    public void open(String file, int record) {
        try {
            RandomAccessFile recordFile = new RandomAccessFile(file, "r");
            recordFile.seek(record * RECSIZE);

            char userN[] = new char[8];
            for (int i = 0; i < userN.length; i++) {
                userN[i] = recordFile.readChar();
            }
            playerName = new String(userN);

            roomNumber = recordFile.readInt();
            
            health = recordFile.readInt();

        } catch (IOException ex) {

        }

    }
        
    public int numRecord(String file) {
        int numR = 0;
        try {
            RandomAccessFile recordFile = new RandomAccessFile(file, "r");
            numR = (int) (recordFile.length() / RECSIZE);

        } catch (Exception ex) {
        }
        return numR;
    }
    
    public void delete(String file, int recordNumber) {
//move the last record from the file to the top and removes the empty space at the end

        open(file, numRecord(file) - 1);
        save(file, recordNumber);

        try {
            RandomAccessFile recordFile = new RandomAccessFile(file, "rw");
            recordFile.setLength(recordFile.length() - RECSIZE);
            recordFile.close();
        } catch (IOException ex) {
            System.out.println("oof");
        }
    }
}
