package GameState;

import java.awt.event.KeyEvent;

/**
 * Klasa abstrakcyjna która implemntuje potrzebne rzeczy
 */
public abstract class GameState {
    /**
     * referencja obiektu GameStateManager
     */
    protected GameStateManager gsm;
    /** Inizjalizowanie obiektow */
    public abstract void init();
    /** Aktualizowanie obiektow */
    public abstract void update();
    /** Rysowanie obiektow
     * @param g */
    public abstract void draw(java.awt.Graphics2D g);

    /**
     * Wywolana po nacisnieciu przyciska zmniana stan na true
     *@see java.awt.event.KeyListener#keyPressed(KeyEvent)
     * @param k
     */
    public abstract void keyPressed(int k);

    /**
     * Wywolana po upuszczeniu przyciska zmiena stan na false
     * @see java.awt.event.KeyListener#keyReleased(KeyEvent)
     * @param k
     */
    public abstract void keyReleased(int k);
    }

