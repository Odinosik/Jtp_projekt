package Main;


import javax.swing.JFrame;

/**
 * Główna klasa uruchamiajaca Scyther 64 2D
 *
 *@author Hubert Czarnowski
 *@author Grafiki stworzone przez Luis Zuno
 *
 */


public class Game {

    /**
     * Główna metoda uruchamiajaca aplikacje
     * @param args
     */

    public static void main(String[] args) {

        JFrame window = new JFrame("SCYTHER 64 2D");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

}
