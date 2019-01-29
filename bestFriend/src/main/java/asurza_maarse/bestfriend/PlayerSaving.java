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
    
}
