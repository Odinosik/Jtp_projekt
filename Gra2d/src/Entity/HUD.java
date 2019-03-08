package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/** Klasa informujaca gracza o aktualnej ilosc zycia i energii */
public class HUD {
    /** Obiekt typu gracz(player) */
    private Player player;
    /** Zmienna obraek */
    private BufferedImage image;
    /** czcionnka */
    private Font font;
    private BufferedImage heart;

    /** Konstruktor ladujacy obrazek
     *
     * @param p {@link Player}
     */
    public HUD(Player p) {

        player = p;
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/HUD/hud1.gif"
                    )
            );
            font = new Font("Arial", Font.PLAIN, 14);
            heart = image.getSubimage(0,0,13,12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Rysowanie obrazka inormujacaego
     * @param g
     */



    public void draw(Graphics2D g) {

        for (int i = 0; i < player.getHealth(); i++) {
            g.drawImage(heart, 10 + i * 15, 10, null);
        }


    }
//        g.drawImage( image, 0, 20, null);
//        g.setFont(font);
//        g.setColor(Color.WHITE);
//        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30,30);
//        g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100,30,50);
    }




