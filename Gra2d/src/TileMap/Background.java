package TileMap;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 * Obiekt Tworzacy Tlo
 */
public class Background {

    private BufferedImage image;
/** pozycja */
    private double x,y;
    /** szybkosc */
    private double dx,dy;
    /** poruszanie mapy */
    private double moveScale;
    /**
     *  konstruktor ladujacy mape
     * @param s sciezka do pliku z tlem
     * @param ms szybkosc poruszania sie tla
     */


    public Background(String s, double ms) {

        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            moveScale = ms;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Ustawia obrazek tla na odpowiedniej pozycji (x,y)
     * @param x pozycja x na osi
     * @param y pozycja y na osi
     */

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    /**
     * Ustawia szybkosc poruszania sie tla (dx,dy)
     * @param dx szybkosc x na osi
     * @param dy szybkosc y na osi
     */

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Metoda aktualizujaca
     *
     */

    public void update() {
        x += dx;
        y += dy;
    }

    /**
     * Metoda rysujaca
     * @param g
     * @see java.awt.Graphics2D
     */
    public void draw(Graphics2D g) {

        g.drawImage(image, (int)x, (int)y, null);

        if(x < 0) {
            g.drawImage(
                    image,
                    (int)x + GamePanel.WIDTH,
                    (int)y,
                    null
            );
        }
        if(x > 0) {
            g.drawImage(
                    image,
                    (int)x - GamePanel.WIDTH,
                    (int)y,
                    null
            );
        }
    }

}



