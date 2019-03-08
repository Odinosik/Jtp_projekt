package Entity.Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
/** Przeciwnik Szkielet */
public class Skeleton extends Enemy {
/** tablica obrazkow do animacji */
    private BufferedImage[] sprites;

    /** Konstrutkor ktory uzupelnia podstawowe parametry przeciwnika
     * Odpowiada za zaladowanie obrazkow
     * @param tm {@link TileMap}
     */
    public Skeleton(TileMap tm) {

        super(tm);
        removehp = false;
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.1;
        maxFallSpeed = 10.0;

        width = 32;
        height = 46;
        cwidth = 25;
        cheight = 35;


        health = maxHealth = 2;
        damage = 1;

        //loadsprites

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                     "/Sprites/Enemies/enemy.gif"
                    )
            );

            sprites = new BufferedImage[8];
            for ( int i = 0; i <sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }



        }
        catch(Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;

    }

    /**
     * Obliczanie nastepnej pozycji obiektu
     */

    private void getNextPosiotion() {

        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }


        if (falling) {

            dy += fallSpeed;

        }
    }

    public void update() {
        //update position
        getNextPosiotion();     //+
        checkTileMapCollision();  //+
       // System.out.println( ytemp);
        setPosition(xtemp,ytemp);

        //check flinching
        if (flinching) {

            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

        // if hits a wall
        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;

        }
        else if (left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        animation.update();

    }

    public void draw(Graphics2D g) {
       // if (notOnScreen()) return;
        setMapPosition();

        super.draw(g);

    }


}
