package Entity;

import TileMap.TileMap;

/**
 * Podstawowa klasa do tworzenia przeciwnikow
 */
public class Enemy extends MapObject {
    /** Podstawe paramatry zycia */
    protected int health,maxHealth;
    /**wartosc smierci przeciwniki true/false */
    protected boolean dead;
    /**wartosc odnowy zycia */
    public boolean removehp;
    /**Wartosc obrazen */
    protected int damage;
    /**wartosc migania true/false */
    protected boolean flinching;
    /** Dlugosc migania */
    protected long flinchTimer;


    /**Tworzenie na mapie
     *
     * @param tm
     */
    public Enemy (TileMap tm) {
        super(tm);
    }

    /**
     * Sprawdzenie zgona true/false
     * @return dead
     */
    public boolean isDead() { return dead; }

    /**
     * ilosc obrazen
     * @return int damage
     */
    public int getDamage() { return damage; }

/** Przyjecie obrazen przez przeciwnika */
    public void hit ( int damage) {
        if (dead || flinching) return;
        health -= damage;
        if (health < 0) health = 0;
        if (health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    /** Aktualizowanie Przeciwnika animacji, pozycji */
    public void update() {

    }
}
