package Entity;


import java.awt.*;
import java.awt.image.BufferedImage;
import TileMap.TileMap;

import javax.imageio.ImageIO;
/** Klasa tworzaca obiekty typu kula ognia wystrzeliwane przez gracza */

public class FireBall extends MapObject {

    private boolean hit;
    private boolean remove;
    /** Tablica obrazkow dla kuli ognia */
    private BufferedImage[] sprites;
    /**Tablica obrazkow dla kolizji z kula ognia */
    private BufferedImage[] hitSprites;

    /** Konstruktor tworzacy kule ognia,uzupelnia podstawoe informacje o niej
     *
     * @param tm {@link TileMap}
     * @param right stan obrocenia gracza
     */
    public FireBall(TileMap tm, boolean right) {

        super(tm);

        facingRight = true;

        moveSpeed = 3.8;

        if(right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 30;
        height = 30;
        cwidth = 14;
        cheight = 14;

        //load sprites

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/fireball.gif"
                    )
            );

            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            hitSprites = new BufferedImage[3];
            for (int i = 0; i <hitSprites.length; i++) {
                hitSprites[i] = spritesheet.getSubimage(
                        i*width,
                        height,
                        width,
                        height
                );
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);


        }
        catch(Exception e) {
            e.printStackTrace();
        }


    }
    /** Funkcja odpalajaca animacje udezenia */
    public void setHit() {
        if(hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }
    /** Aktualizowanie kuli ognia i udezenia */
    public void update() {

        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if(hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    /**
     * Stan usuniecia kuli ognia
     * @return boolean remove true/false
     */
    public boolean shouldRemove() { return remove; }

    public void draw(Graphics2D g) {

        setMapPosition();

      super.draw(g);



    }
}

