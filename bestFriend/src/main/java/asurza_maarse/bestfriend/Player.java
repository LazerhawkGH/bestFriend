/*
 * Author: Zachary Maarse
 * Date: Dec 13, 2018
 * Purpose: Handles the players properties
 */
package asurza_maarse.bestfriend;

import javafx.scene.image.ImageView;

/**
 *
 * @author zacharym44
 */
public class Player extends ImageView{
    
    public String name;
    public int health, atk;    
    
    public Player(){
        name = "Elliot";
        health = 100;
        atk = 50;
    }
    
    public Player(String n, Boolean hk, int h, int a){
        this.name = n;
        this.health = h;
        this.atk = a;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String n){
        this.name = n;
    }
       
    public int getAtk(){
        return atk;
    }
    public void setAtk(int a){
        this.atk = a;
    }
    
    public int getHealth(){
        return health;
    }
    public void setHealth(int h){
        this.health = h;
    }
}
