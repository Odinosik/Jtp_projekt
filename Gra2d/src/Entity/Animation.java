package Entity;

import java.awt.image.BufferedImage;

/**
 * Klasa zarzadzajaca ramkami animacji
 */
public class Animation {
    /** Tablica obrazow */
    private BufferedImage[] frames;
    /** Aktualny obrazek */
    private int currentFrame;
    /** rozpoczecie czasu pomiedzy obrazkami*/
    private long startTime;
    /** opoznienie pomiedzy obrazkami */

    private long delay;
    /** informuje czy animacja juz zostala wgrana */
    private boolean playedOnce;

    /** Konstruktor domyslny */

    public void Animation() {
        playedOnce = false;
    }

    /** Ustawia obrazek
     *
     * @param frames
     */
    public void setFrames(BufferedImage[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }
    /** Ustawia opoznienie */
    public void setDelay(long d) { delay = d;}
    /** Aktualna ramka z orbazkiem */
    public void setFrame(int i) {currentFrame = i; }

    public void update() {

        if(delay == -1) return;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }

    }
    /**
     * Zwraca indeks ramki
     * @return int currentFrame
     * */
    public int getFrame() { return currentFrame;}

    /**
     * zwraca aktualna ramke z listy
     * @return BufferedImage frames[currentFrame]
     */
    public BufferedImage getImage() { return frames[currentFrame];}

    /**
     * sprawdza stan czy animacja zostala juz wgrana
     * @return playedOnce true/false
     */
    public boolean hasPlayedOnce() { return playedOnce;}





}
