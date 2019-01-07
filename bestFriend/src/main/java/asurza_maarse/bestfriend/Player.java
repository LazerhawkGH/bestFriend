/*
 * Author: Zachary Maarse
 * Date: Dec 13, 2018
 * Purpose: Handles the players properties
 */
package asurza_maarse.bestfriend;

/**
 *
 * @author zacharym44
 */
public class Player {
    
    public String name;
    public Boolean hasKnife;
    public int health, atk;    
    
    public Player(){
        name = "Elliot";
        hasKnife= false;
        health = 100;
        atk = 50;
    }
    
    public Player(String n, Boolean hk, int h, int a){
        this.name = n;
        this.hasKnife = hk;
        this.health = h;
        this.atk = a;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String n){
        this.name = n;
    }
    
    public Boolean getHasKnife(){
        return hasKnife;
    }
        public void setHasKnife(Boolean hk){
        this.hasKnife = hk;
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
