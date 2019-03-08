package Entity;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.Expression;

/** Klasa odpowiadajaca za eksplozje gdy zginie przeciwnik */
public class Explosion {
 /** parametry x,y na osi */
    private int x,y;
   /**Parametry na mapie x,y */
    private int xmap,ymap;

/** Parametry serokosci i wysokosci */
    private int width,height;
    /** Obiek Animation {@link Animation}
     */
    private Animation animation;
    /** Tablica obrazkow */
    private BufferedImage[] sprites;
    /** Stan znikniecia animacji */
    private boolean remove;

    /**
     * Konstruktor przyjmujacy parametry x,y zgonu przeciwnika do pojawienia sie animacji
     * @param x
     * @param y
     */
    public Explosion(int x, int y) {

        this.x = x;
        System.out.println(this.x);
        this.y = y;
        System.out.println(this.y);
        width = 30;
        height = 30;

        try{
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/explosion.gif"
                    )
            );

            sprites = new BufferedImage[6];

            for (int i = 0; i <sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i*width,
                        0,
                        width,
                        height
                );
            }


        }
        catch ( Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);

    }
    /** Aktualiowanie obrazkow */
    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    /**
     * Sprawdzenie stanu usuniecia obiektu
     * @return boolean remove
     */

    public boolean shouldRemove() { return remove; }

    /**
     * Ustawienei pozycji na mapie x,y
     * @param x
     * @param y
     */
    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap = y;
    }
    /** Metoda rysujaca obiekt */
    public void draw(Graphics2D g) {
        g.drawImage(
                animation.getImage(),
                x + xmap - width /2,
                y + ymap - height / 2,
                null
        );
    }

}
