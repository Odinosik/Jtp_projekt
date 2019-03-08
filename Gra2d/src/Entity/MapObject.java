package Entity;
import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

/**
 * Podstawowa klasa dla wszystkich poruszajacych sie obiektow
 */
public abstract class MapObject {

    //tile stuff
    /** mapa */
    protected TileMap tileMap;

    /** zmienne odpowiadajace za polozenie mapy */
    protected int xmap, ymap, tileSize;


    //position and vector
    /** zmienne pozycyjne */
    protected double x,y;
   /** zmienne odpowiadajace za poruszanie */
    protected double dx,dy;


    //dimension
    /**
     * zmienne wymiarowe
     */
    protected int width,height;


    //colision
    /**
     * zmienne kolizyjne
     */
    protected int cwidth, cheight;


    /**
     * zmienne kolizyjne
     */
    protected int currRow, currCol;

    /**
     * zmienne kolizyjne
     */
    protected double xdest, ydest;
    /**
     * zmienne kolizyjne
     */
    protected double xtemp, ytemp;
    /**
     * zmienne kolizyjne
     */
    protected boolean topLeft,topRight;
    /**
     * zmienne kolizyjne
     */
    protected boolean bottomLeft, bottomRight;



    //animation
    /**Zmienna animacji */
    protected Animation animation;
    /**Zmienna aktualnej animacji */
    protected int currentAction;
    /**Zmienna poprzedniej animacji */
    protected int previousAction;

    // movement
 /**Zmienne ruchu */
    protected boolean left;
    /**Zmienne ruchu */
    protected boolean right;
    /**Zmienne ruchu */
    protected boolean up;
    /**Zmienne ruchu */
    protected boolean down;
    /**Zmienne ruchu */
    protected boolean jumping;
    /**Zmienne ruchu */
    protected boolean falling;
    /**Zmienne ruchu */
    protected boolean facingRight;
    /**Zmienne ruchu */


    //movement attributes
            /**Zmienne atrybutow ruchu */
    protected double moveSpeed;
    /**Zmienne atrybutow ruchu */
    protected double maxSpeed;
    /**Zmienne atrybutow ruchu */
    protected double stopSpeed;
    /**Zmienne atrybutow ruchu */
    protected double fallSpeed;
    /**Zmienne atrybutow ruchu */
    protected double maxFallSpeed;
    /**Zmienne atrybutow ruchu */
    protected double jumpStart;
    /**Zmienne atrybutow ruchu */
    protected double stopJumpsSpeed;


    //constructor

    /**Konstruktor Inicjalizujacy mape {@link TileMap}

     * @param tm {@link MapObject}
     */
    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize();

    }

    /**
     * Zwroc true/false jeśli koliduje z obiektem o
     * @param o {@link MapObject}
     * @return true/false
     */

    public boolean intersects (MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);



    }

    /**
     * Obliczanie prostakatu przeciacia
     * @return true/false
     */

    public Rectangle getRectangle() {
        return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
    }

    /**
     *Obliczanie rogow do kolizji dla {@link MapObject}
     * @param x zmienna wymiaru x
     * @param y zmienna wymiaru y
     */
    public void calculateCorners(double x ,double y) {

        int leftTile = (int) (x - cwidth / 2) /tileSize;
        int rightTile = (int) (x+ cheight /2-1 ) /tileSize;

        int topTile = (int) (y - cheight / 2) / tileSize;
        int bottomTile = (int) (y +cheight /2-1) /tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile,rightTile);
        int bl = tileMap.getType(bottomTile,leftTile);
        int br = tileMap.getType(bottomTile,rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;






    }

    /**
     * Metoda okreslajaca kolizje
     */


    public void checkTileMapCollision () {
        currCol = (int)x / tileSize;
        currRow = (int)y /tileSize;

        xdest = x +dx;
        ydest = y +dy;

        xtemp = x;
        ytemp = y;


        calculateCorners(x,ydest);
        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                ytemp = currRow * tileSize + cheight / 2;

            }
            else {
                ytemp += dy;

            }
        }
        if(dy > 0) {

            if(bottomRight || bottomLeft) {
                dy = 0;
                falling = false;
                ytemp = (currRow + 1) * tileSize - cheight / 2;
                //System.out.println(ytemp);

            }
            else {
                ytemp +=dy;

            }

        }

        calculateCorners(xdest, y);
        if (dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                xtemp = currCol * tileSize + cwidth / 2;
            }
            else {
                xtemp += dx;

            }
        }
        if (dx > 0 ) {
            if (topRight || bottomRight) {
                dx = 0;
                xtemp = (currCol +1 ) *tileSize - cwidth /2 ;


            }
            else {
                xtemp += dx;
            }

        }
        if (!falling) {
            calculateCorners(x, ydest+3);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

    }

    /**
     * Zwraca wartosc x
     * @return x
     */
    public int getx() { return (int)x;}

    /**
     * Zwraca wartosc dy
     * @return x
     */

    public int getdy() { return (int)dy;}
    /**
     * Zwraca wartosc y
     * @return y
     */
    public int gety() { return (int)y;}

    /**
     * Zwraca szerokosc
     * @return width
     */
    public int getWidth() { return width;}
    /**
     * Zwraca wysokosc
     * @return height
     */
    public int getHeight() { return height; }
    /**
     * Zwraca powieszchnie szerokosci
     * @return cwidth
     */
    public int getCWidth() { return cwidth;}
    /**
     * Zwraca powieszchnie wysokosci
     * @return cheight
     */
    public int getCHeight() { return cheight; }

    /**
     * Ustawia aktualna pozycje obiektu (x,y)
     * @param x
     * @param y
     */

    public void setPosition ( double x ,double y) {
        this.x = x;
        this.y = y;
        //System.out.println(y);

    }

    /**
     * Ustawia predkosc obiektu (dx,dy)
     * @param dx
     * @param dy
     */
    public void setVector ( double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Ustawia pozycje na mapie (xmap,ymap)
     */

    public void setMapPosition() {
        xmap = (int)tileMap.getx();
        ymap = (int)tileMap.gety();
    }

    /**
     * Sprawdza stan sterowania
     * @param b
     */

    public void setLeft ( boolean b) { left = b; }
    /**
     * Sprawdza stan sterowania
     * @param b
     */
    public void setRight ( boolean b) { right = b; }
    /**
     * Sprawdza stan sterowania
     * @param b
     */
    public void setUp ( boolean b ) { up = b; }
    /**
     * Sprawdza stan sterowania
     * @param b
     */
    public void setDown ( boolean b) { down = b; }
    /**
     * Sprawdza stan sterowania
     * @param b
     */
    public void setJumping (boolean b) { jumping = b;}

    /**
     * Spradza polozenie obiektu czy nie jest po za mapa
     * @return true/false
     */

    public boolean notOnScreen() {
        return x + xmap + width < 0 ||
                x + xmap - width > GamePanel.WIDTH ||
                y + ymap + height < 0 ||
                y + ymap - height > GamePanel.HEIGHT;
    }

    /**
     * Rysowanei obiektow
     * @param g
     */

    public void draw(Graphics2D g) {
        if (facingRight) {
            g.drawImage ( animation.getImage(),
                    (int)(x + xmap - width /2),
                    (int) ( y + ymap - height /2),
                    null
            );
        }
        else {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xmap - width /2 + width),
                    (int) ( y + ymap - height /2),
                    -width,
                    height,
                    null
            );

        }
    }





}
