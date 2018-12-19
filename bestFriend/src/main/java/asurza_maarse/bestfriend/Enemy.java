/*
 * Author: Zachary Maarse
 * Date: Dec 19, 2018
 * Purpose: 
 */
package asurza_maarse.bestfriend;

/**
 *
 * @author zacharym44
 */
public class Enemy {

    public String type;
    public int damage, health;

    public Enemy() {
        type = "Basic";
        damage = 10;
        health = 150;

    }

    public Enemy(String type, int damage, int health) {
        this.type = type;
        this.damage = damage;
        this.health = health;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        this.type = t;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int d) {
        this.damage = d;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        this.health = h;
    }

}
