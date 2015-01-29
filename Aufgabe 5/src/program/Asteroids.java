package program;

import com.google.common.eventbus.Subscribe;
import helper.Angle;
import org.amcgala.Amcgala;
import org.amcgala.Scene;
import org.amcgala.event.InputHandler;
import org.amcgala.event.KeyPressedEvent;
import org.amcgala.event.KeyReleasedEvent;
import org.amcgala.math.Vertex3f;
import shapes.Asteroid;
import shapes.Ship;
import shapes.Shot;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Die zentrale Spiele-Klasse, die als Controller angesehen werden kann.
 *
 * @author Alex Dobrynin
 */
public class Asteroids extends Amcgala {

    public static Scene scene;
    // Anzahl Asteroiden im Spiel
    public static int asteroiden;
    // GameOver
    public static boolean gameOver;

    Ship starTrek;

    public Asteroids() throws Exception {
        scene = new Scene("one");

        starTrek = Ship.createInstance(new Vertex3f(280, 215, 0), new Vertex3f(240, 200, 0), new Vertex3f(240,230,0));
        scene.addShape(starTrek, starTrek.getNode());

        addAsteroids();

        // Drücken des Pfeiltasten abfragen
        scene.addInputHandler(new InputHandler() {

            @Subscribe
            public void moveShip(KeyPressedEvent event) {
                int keyCode = event.getKeyCode();

                if(!gameOver)
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            starTrek.getShipModel().setKeyEvent(1);
                            System.out.println("Increase speed");
                            break;
                        case KeyEvent.VK_RIGHT:
                            starTrek.getShipModel().setKeyEvent(2);
                            System.out.println("Rotate right");
                            break;
                        case KeyEvent.VK_LEFT:
                            starTrek.getShipModel().setKeyEvent(3);
                            System.out.println("Rotate left");

                    }

            }

        }, "move ship");

        // Loslassen der Pfeiltasten abfragen
        scene.addInputHandler(new InputHandler() {

            @Subscribe
            public void stopShip(KeyReleasedEvent event) {
                int keyCode = event.getKeyCode();
                if(!gameOver)
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            System.out.println("Stop");
                            starTrek.getShipModel().setKeyEvent(0);
                            break;
                        case KeyEvent.VK_RIGHT:
                            System.out.println("Rotate right stop");
                            starTrek.getShipModel().setKeyEvent(4);
                            break;
                        case KeyEvent.VK_LEFT:
                            System.out.println("Rotate left stop");
                            starTrek.getShipModel().setKeyEvent(4);
                    }


            }
        }, "reset moving");

        // Schießen
        scene.addInputHandler(new InputHandler() {

            @Subscribe
            public void ShootWeapon(KeyReleasedEvent event) {
                int keyCode = event.getKeyCode();
                if(!gameOver)
                    switch (keyCode) {
                        case KeyEvent.VK_SPACE:
                            System.out.println("Shoot");
                            Shot shot = starTrek.getShipModel().ShootWeapon();
                            scene.addShape(shot, shot.getNode());
                            break;
                    }


            }
        }, "Shoot");

        // Asteroiden hinzufügen
        scene.addInputHandler(new InputHandler() {

            @Subscribe
            public void add(KeyReleasedEvent event) {
                int keyCode = event.getKeyCode();
                if(!gameOver)
                    switch (keyCode) {
                        case KeyEvent.VK_ENTER:
                            addAsteroids();
                            break;
                    }


            }
        }, "Shoot");


        framework.addScene(scene);
        framework.loadScene(scene);
    }

    public static void increaseAsteroiden(){
        asteroiden++;
    }
    public static void decreaseAsteroiden(){
        asteroiden--;
    }
    public static int countAsteroiden(){
        return asteroiden;
    }
    public static void gameOver() {
        gameOver = true;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void addAsteroids() {
        for(int i=0;i<5;i++){
            Asteroid asteroid = new Asteroid(new Vertex3f((float)Math.random()*400,(float)Math.random()*400,(float)2.0),randInt(1,5)*30, randInt(1,5)*30,new Angle(Math.random()*(2*Math.PI)), false);
            scene.addShape(asteroid, asteroid.getNode());
            asteroiden++;
        }
    }



    public static void main(String[] args) {
        try {
            new Asteroids();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
