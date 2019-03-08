package GameState;


import java.util.ArrayList;

/**
 * Klasa tworzaca Menadzer stanu gry
 * Wykorzystuje klase abstrakcyjna GameState
 * @see GameState
 */

public class GameStateManager {
    /**
     * Lista stanow gry
     */
    private ArrayList <GameState> gameStates;
    /**
     * Indeks aktywowanego stanu gry
     */
    private int currentState;
    /**
     * Wartosc Menu
     */
    public static final int MENUSTATE = 0;
    /**
     * Wartosc poziomu
     */
    public static final int LEVELSTATE = 1;

    /** Domyslny konstruktor
     *
     */
    public GameStateManager() {

        gameStates =  new ArrayList<GameState>();
        currentState =MENUSTATE ;
        gameStates.add(new MenuState(this));

        gameStates.add(new Level1State(this));
    }

    /**
     *  Metoda ustawiajaca stan gry
     * @param state stan gry
     */

    public void setState ( int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    /**
     * Metoda Aktualizujaca stan gry
     */
    public void update(){
        gameStates.get(currentState).update();

    }

    /**
     * Metoda rysujaca
     * @param g
     */
    public void draw(java.awt.Graphics2D g){
        gameStates.get(currentState).draw(g);
    }

    /**
     *
     * @param k
     */

    public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}





}
