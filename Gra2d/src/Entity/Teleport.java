package Entity;
import TileMap.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Obiekt tworzacy teleport
 * Teleport odpowiada za koniec planszy */
public class Teleport extends MapObject {

    /** Tablica przechowujaca animacje */
    private BufferedImage[] sprites;

    /**
     * Konstruktor Tworzacy obiekt, odpowiada za zaladowanie obrazkow
     * @param tm {@link TileMap}
     */
    public Teleport(TileMap tm) {
        super(tm);
        facingRight = true;
        width = height = 40;
        cwidth = 20;
        cheight = 40;

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/Teleport.gif"
                    )
            );
            sprites = new BufferedImage[9];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(1);
        right = true;
        facingRight = true;


    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);}

        /** Aktualizowanie obiektu */
    public void update(){

        animation.update();
    }
}
