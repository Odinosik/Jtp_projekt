package TileMap;

import java.awt.image.BufferedImage;

/**
 * Towrzenie za pomoca kafelkow Tla ( Background)
 */

public class Tile {

    private BufferedImage image;
    private int type;

    // tile types
    /**
     * Wartosc kafelka normalnego
     */
    public static final int NORMAL = 0;
    /**
     * Wartosc kafelka zablokowanego

      */
    public static final int BLOCKED = 1;

    /**
     * Konstruktor domyslny
     * @param image zmienna typu BufferedImage
     * @param type zmienna typu int
     */

    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    /**
     * Metoda zwracajaca kafelek(obrazek)
     * @return image
     */
    public BufferedImage getImage() { return image; }

    /**
     * Metoda zwracajaca typ kafelka
     * @return type
     */
    public int getType() { return type; }

}
