package Entity;


import TileMap.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Glowny obiekt MapObject kontrolowany przez uzytkownika
 */
public class Player extends MapObject {

    //player stuff
    /** Podstawowe parametry zycia */
    private int health,maxHealth;

    /** Stan uzywania ognia */
    private int fire,maxFire;

    /** Sprawdzenie zgonu */
    private boolean dead;
    /** Sprawdzenie migania */
    private boolean flinching;
    /** Czas migania */
    private long flinchTimer;



    //fireball
    /** Stan poszczenia ognia */
    private boolean firing;
    /**Koszt ognia */
    private int fireCost;
    /** Ilosc zadanych obrazen przez ogien */
    private int fireBallDamage;
    /**Lista obiektow kul ognia */
    private ArrayList<FireBall> fireBalls;
    //screatch

    /** Stan ataku */
    private boolean scratching;
    /** Ilosc zadanych obrazen przez atak */
    private int scratchDamage;
    /** Zasieg ataku */
    private int scratchRange;

    //gliding
    /** Stan szybowania */
    private boolean gliding;

    //animation
    /** Lista Klatek animacji */
    private ArrayList<BufferedImage[]> sprites;
    /** Lista ilosci animacji na dany stan */
    private final int[] numFrames = {4,6,4,2,3,3,5};
    //animation actions
    /** Stan akcji dla danej animacji */
    private static final int IDLE = 0;
    /** Stan akcji dla danej animacji */
    private static final int WALKING = 1;
    /** Stan akcji dla danej animacji */
    private static final int JUMPING = 2;
    /** Stan akcji dla danej animacji */
    private static final int FALLING = 3;
    /** Stan akcji dla danej animacji */
    private static final int GLIDING = 4;
    /** Stan akcji dla danej animacji */
    private static final int FIREBALL = 5;
    /** Stan akcji dla danej animacji */
    private static final int SCRATCHING = 6;

    /** Konstruktor uzupelniajacy podstawowe parametry oraz ladujacy animacje
     *
     *
     * @param tm {@link TileMap}
     */
    public Player(TileMap tm) {
        super(tm);
        width = 53;
        height = 45;
        cwidth = 30;
        cheight = 35;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpsSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;
        fire = maxFire = 2500;

        fireCost = 200;
        fireBallDamage = 5;
        fireBalls = new ArrayList<FireBall>();
        //fireBalls

        scratchDamage = 8;
        scratchRange = 40;

        //load sprites

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                     "/Sprites/Player/herofinal1.gif"
                    )
            );

            sprites = new ArrayList<BufferedImage[]>();

            for (int i = 0; i<7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for ( int j=0; j < numFrames[i]; j++) {
                    if (i ==  SCRATCHING) {
                        int pom = 71;
                        bi[j] = spritesheet.getSubimage(
                                j * pom,
                                i * height - 23,
                                pom ,
                                height
                        );
                    }
                    else if (i == FIREBALL) {
                        int pom = 71;
                        bi[j] = spritesheet.getSubimage(
                                j * pom,
                                i * height + 67 ,
                                pom,
                                height
                        );
                    }

                    else if (i == WALKING) {

                        bi[j] = spritesheet.getSubimage(
                                j * (width+2),
                                i * height ,
                                (width+2) ,
                                height-5
                        );
                    }
                    else if (i == JUMPING) {

                        bi[j] = spritesheet.getSubimage(
                                j * (width + 2),
                                i * height -5 ,
                                (width + 2),
                                height +5
                        );

                    }
                    else if (i == FALLING) {
                        bi[j] = spritesheet.getSubimage(
                                j * (width + 2),
                                i * height ,
                                width + 2 ,
                                height+11
                        );
                    }
                    else if (i == GLIDING) {
                        bi[j] = spritesheet.getSubimage(
                                j * (width+2),
                                i * height + 11 ,
                                width +2,
                                height+11
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width ,
                                height
                        );
                    }
                }

                sprites.add(bi);


            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);



    }
    /** Pobiera ilosc aktualnego zycia
     * @return int health */
    public int getHealth() { return health;}
    /** Pobiera ilosc maxymalnego zycia
     * @return int maxHealth*/
    public int getMaxHealth() { return maxHealth;}

    /**
     * Pobiera aktualna ilosc energi
     * @return int fire
     */
    public int getFire() { return fire;}

    /**
     * Pobiera maxymalny stan energi
     * @return int maxFire
     */
    public int getMaxFire() { return maxFire;}

    /** Uruchamia stan ataku ogniem */
    public void setFiring() {
        firing = true;
    }
    /** Uruchamia stan ataku  */
    public void setScratching() {
        scratching = true;
    }
    /** Uruchamia stan opadania */

    public void setGliding ( boolean b) {
        gliding = b;
    }

    /**
     * sprawdza ataki zadane przeciwnikom
     * @param enemies lista wrogów
     */
    public void checkAttack(ArrayList<Enemy> enemies) {

        for ( int i = 0; i <enemies.size(); i++) {

            Enemy e = enemies.get(i);
            //scratch attack
            if(scratching) {
                if(facingRight) {
                    if(e.getx() > x &&
                            e.getx() < x + scratchRange &&
                            e.gety() > y - height / 2 &&
                            e.gety() < y + height / 2) {

                        e.hit(scratchDamage);
                    }

                }
                else {
                    if(e.getx() < x && e.getx() > x - scratchRange && e.gety() > y -height / 2 && e.gety() < y + height /2 ) {
                        e.hit(scratchDamage);
                    }



                }
            }
            //fireballs
            for (int j =0; j< fireBalls.size(); j++) {
                if(fireBalls.get(j).intersects(e)) {
                    e.hit(fireBallDamage);
                    fireBalls.get(j).setHit();
                    break;
                }


            }

            //check enemy collision
            if(intersects(e)) {
                hit(e.getDamage());
            }
        }

    }
    /** odnowienie zycia */
    public void healthup (){
        health = maxHealth;

    }

    /**
     * Otrzymywanie obrazen i uruchomienie migotania
     * @param damage ilosc zadanych obrazen
     */

    public void hit ( int damage) {
        if(flinching) return;
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    /** Ustawia nastepna pozycje postaci sterujacej przez gracza */
    private void getNextPosition() {

        //movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if (right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if(dx > 0 ) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx =0;
                }
            }
            else if(dx < 0){
                dx += stopSpeed;
                if (dx >0 ) {
                    dx = 0;
                }
            }
        }
        //cannot attack while moving

        if ((currentAction ==SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)){
            dx = 0;
        }

        //jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        if(falling) {
            if(dy > 0 && gliding) dy += fallSpeed * 0.1;
            else dy += fallSpeed;

            if(dy >0 ) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpsSpeed;

            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    /** Aktualizuje animacje dla danego stanu  i pozycje na mapie gracza */
    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);



        //check attack has stopped
        if (currentAction == SCRATCHING) {
            if ( animation.hasPlayedOnce()) scratching = false;
        }
        if (currentAction == FIREBALL) {
            if ( animation.hasPlayedOnce()) firing = false;
        }
        //fireball attack

        fire += 1;
        if (fire > maxFire) fire = maxFire;
        if(firing && currentAction != FIREBALL) {
            if(fire > fireCost) {
                fire -= fireCost;
                FireBall fb = new FireBall ( tileMap, facingRight);
                fb.setPosition(x,y);
                fireBalls.add(fb);
            }
        }

        //update fireballs
        for(int i = 0; i < fireBalls.size(); i++ ) {
            fireBalls.get(i).update();
           // System.out.println(fireBalls.get(i).gety());
            if (fireBalls.get(i).shouldRemove()) {
                fireBalls.remove(i);
                i--;
            }
        }

        //check done flinching
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }



        if(scratching) {
            if(currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 71;
                height = 45;


            }
        }
        else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 71;
                height = 45;


            }
        } else if (dy > 0 ) {
            if(gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 55;
                    height = 56;
                }
            }
            else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 55;
                height = 56;

            }

        }
        else if (dy < 0){
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(300);
                width = 55;
                height = 45;

            }

        }
        else if(left || right){
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(100);
                width = 55;
                height = 45;
            }
        }
        else  {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 53;
                height = 45;
            }

        }
        animation.update();

        //set diraction
        if (currentAction != SCRATCHING && currentAction != FIREBALL) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }



    }

    public void draw(Graphics2D g ) {
        setMapPosition();

        //draw fireballs
        for ( int i= 0; i <fireBalls.size(); i++) {
            fireBalls.get(i).draw(g);
        }

        //draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) /1000000;
            if (elapsed / 100 % 2 ==0) {
                return;
            }
        }
       super.draw(g);

    }




}
