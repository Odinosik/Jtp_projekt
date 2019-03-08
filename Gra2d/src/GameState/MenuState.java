package GameState;

import TileMap.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Menu Gry
 */
public class MenuState extends GameState {
    /**
     * tlo
     */
    private Background bg;
    private BufferedImage tit;
    private BufferedImage titimg;
    /**
     * aktualny wybor
     */
    private int currentChoice = 0;
    /**
     * tablica opcji
     */
    private String[] options = {
            "Start",
            "Quit"
    };
    /**
     * Kolor
     */
    private Color titleColor;
    /**
     * czcionka tytulu
     */
    private Font titleFont;
    /**
     * czcionka opcji
     */
    private Font font;
/**
 * Domyslny konstruktor inicjalizujacy stan menu
 * @param  gsm
 * @see GameStateManager
 */
    public MenuState(GameStateManager gsm) {

        this.gsm = gsm;

        try {

            bg = new Background("/Backgrounds/Back3.gif", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(0, 0, 0);
            titleFont = new Font(
                    "Verdana",
                    Font.PLAIN,
                    28);



        } catch (Exception e) {
            e.printStackTrace();

        }


        try {

            BufferedImage titimg = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Backgrounds/title.gif"
                    )
            );
            tit = titimg.getSubimage(0,0,195,65);



        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    /**
     * @see GameState
     */
    public void init() {}

    /**
     * Aktualizacja tla
     */

    public void update() {
        bg.update();
    }

    /**
     * Metoda rysujaca Menu
     * @param g
     */

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);
        g.drawImage(tit,60,40,null);





        // draw menu options
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.BLACK);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    /**
     * Wybor dokonywany w menu
     */

    private void select() {
        if(currentChoice == 0) {
            // start
            gsm.setState(GameStateManager.LEVELSTATE);
        }
        if(currentChoice == 1) {
            System.exit(0);
        }
    }

    /**
     * obsluga wybranych klawiszy
     * @param k
     */

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int k) {}

}











