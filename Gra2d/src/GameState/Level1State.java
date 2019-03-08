package GameState;

import Entity.*;
import Entity.Entity.Enemies.Skeleton;
import Entity.Entity.Enemies.SuperSkeleton;
import Main.GamePanel;
import TileMap.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Plansza do gry ktora tworzy gracza i reszte obiektow
 */
public class Level1State extends GameState {

    /** Obiekt mapy {@link TileMap}*/
    private TileMap tileMap;
    /** Obiket tla {@link Background}*/
    private Background bg;
    /** Obiekt gracz {@link Player}*/
    private Player player;
    /** Obiekt teleport {@link Teleport}*/
    private Teleport tp;
    /** Lista preciwnikow {@link Enemy}*/
    private ArrayList<Enemy> enemies;


    /**Lista eksplozji przeciwnikow {@link Explosion}*/
    private ArrayList<Explosion> explosions;
    /** Obiekt Infromacji {@link HUD} */
    private HUD hud;
/** Stan gry */
    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }


    public void init() {

        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/tile.gif");
        tileMap.loadMap("/Maps/mapa1.map");
        tileMap.setPosition(0, 0);


        bg = new Background("/Backgrounds/Background.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(100,160);

        populateEnemies();


         tp = new Teleport(tileMap);

        tp.setPosition(2600,200);
        System.out.println(tp.getx());


        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

    }
    /** Liczba przeciwnikow */
    private void populateEnemies() {
        enemies = new ArrayList<Enemy>();
        Skeleton s;
        SuperSkeleton sket;
        Point[] points = new Point[]{
                new Point(1200,100),
            new Point(200, 100),

        };
     for ( int i =0; i <points.length; i++) {
         s = new Skeleton(tileMap);
         s.setPosition(points[i].x, points[i].y);
        // System.out.println(points[i].y);
         enemies.add(s);

     }
     sket = new SuperSkeleton(tileMap);
     sket.setPosition(220,100);
     enemies.add(sket);
    }


    public void update() {
        player.update();
        tp.update();
        tileMap.setPosition(
                GamePanel.WIDTH / 2 -player.getx(),
                GamePanel.HEIGHT / 2 -player.gety()
        );

        //set background
        bg.setPosition(tileMap.getx(),tileMap.gety());

        //attack enemies
        player.checkAttack(enemies);


       // System.out.println(player.getx() + " " + player.gety() + " " + player.getHeight() + " " + player.getdy() + " " + player.getWidth());

        //update all enemies
        for (int i = 0; i<enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();

            if(e.isDead()) {
                if(e.removehp == true) {
                    player.healthup();
                }
                enemies.remove(i);
                i--;
               // System.out.println(e.gety());
                explosions.add(new Explosion(e.getx(),e.gety()));

            }
        }
      //  System.out.println(player.gety());
        if (player.gety() > 240) {
            player.setPosition(100, 100);
            player.hit(1);
        }
        if(player.getHealth() == 0)
        {
            gsm.setState(GameStateManager.MENUSTATE);
        }

        if (player.getx() <2800 && player.getx() > 2600)
            gsm.setState(GameStateManager.MENUSTATE);



        //update explosion
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
           // System.out.println(explosions.get(i).x);

            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
            }
        }





    }

    public void draw(Graphics2D g) {

        // draw bg
        bg.draw(g);



        // draw tilemap
        tileMap.draw(g);
        //draw player
        player.draw(g);

        tp.draw(g);
        //draw enemies;
        for (int i =0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        //draw explosions
        for ( int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());

            explosions.get(i).draw(g);
        }
        //draw hud
        hud.draw(g);

    }

    public void keyPressed(int k) {
        if( k == KeyEvent.VK_LEFT) player.setLeft(true);
        if( k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFiring();

    }

    public void keyReleased(int k) {
        if( k == KeyEvent.VK_LEFT) player.setLeft(false);
        if( k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }

}


