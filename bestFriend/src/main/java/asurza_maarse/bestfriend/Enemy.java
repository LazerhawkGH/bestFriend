/*
 * Author: Zachary Maarse
 * Date: Dec 19, 2018
 * Purpose: Handles the creation of enemies
 */
package asurza_maarse.bestfriend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author zacharym44
 *
 *
 * Type:   | Deformed | Weak | Strong | Boss |
 * Damage: | 10       | 20   | 50     | 100  |
 * Health: | 150      | 200  | 250    | 350  |
 * Speed:  | 2        | 5    | 7      | 20   |
 *
 */
public class Enemy extends ImageView {

    public String type;
    public int damage, health, speed;

    public Enemy() {
        super();
        type = "Basic";
        damage = 10;
        health = 150;

    }
    // int type, Image img

    public Enemy(int type) {
        super();
        switch (type) {
            case 1:
                setType("Deformed");
                setDamage(10);
                setHealth(150);
                setSpeed(2);
                this.setImage(new Image(getClass().getResource("enemy.png").toString()));
                break;
            case 2:
                setType("Weak");
                setDamage(20);
                setHealth(200);
                setSpeed(5);
                this.setImage(new Image(getClass().getResource("enemy.png").toString()));
                break;
            case 3: 
                setType("Strong-");
                setDamage(50);
                setHealth(250);
                setSpeed(7);
                this.setImage(new Image(getClass().getResource("enemy.png").toString()));
                break;
            case 4:
                setType("Boss");
                setDamage(100);
                setHealth(350);
                setSpeed(20);
                this.setImage(new Image(getClass().getResource("enemy.png").toString()));
                break;
        }

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int s) {
        this.speed = s;
    }
    
    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        this.health = h;
    }

}