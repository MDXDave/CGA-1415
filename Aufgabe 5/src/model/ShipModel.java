package model;

import org.amcgala.Framework;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.transform.RotationZ;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Shape;
import helper.Angle;
import helper.Velocity;
import org.lwjgl.Sys;
import shapes.Ship;
import shapes.Shot;

import java.security.Key;

/**
 * Das Model eines Raumschiffes, welches jegliche Anwendungslogik zum Steuern des Schiffes enthält. Die Anwendungslogik des
 * Models ist von der View entkoppelt, weswegen theoretisch jedes Shapes das Verhalten eines Raumschiffes simulieren kann.
 *
 * @author Alex Dobrynin
 */
public class ShipModel {

    private Ship ship;
    private Angle angle;
    private Velocity velocity;

    private int KeyEvent = 0;

    public ShipModel(Shape shape) {
        this.ship = (Ship) shape;
        this.angle = new Angle(0);
        this.velocity = new Velocity(0.5);
    }

    /**
     * Diese Methode enthält alle Aktualisierungen des zugehörigen View-Objektes
     */
    public void onViewUpdate() {
        //System.out.println("onViewUpdate()");
        //System.out.println("Velocity: "+velocity.value);

        switch(KeyEvent){
            // Standard
            case 0:
                ship.getShipModel().velocity.resetValue();
                break;
            // Beschleunigen um 0.5 bis max. 10
            case 1:
                if(ship.getShipModel().velocity.value<10)
                    ship.getShipModel().velocity.increaseValueBy(0.5);
                break;
            // Nach rechts rotieren
            case 2:
                turnRight();
                break;
            // Nach links rotieren
            case 3:
                turnLeft();
                break;
            default:
                // Stop
        }

        // Das Raumschiff hat einen natürlichen Drift von 0.5
        ship.getNode().add(new Translation(velocity.value, 0, 0));


        Vertex3f currentPosition = ship.getCurrentPosition();


        /* 1: Wenn x > Scenebreite
           2: Wenn x < 0
           3: Wenn y > Scenehöhe
           4: Wenn y < 0
          */

        if(currentPosition.y > 1000 || currentPosition.y < -1000 || currentPosition.x > 1000 || currentPosition.x < -1000){
            Ship.getInstance().resetPosition();
        }

        if(currentPosition.x >= Framework.getInstance().getWidth()){
            ship.getNode().add(new Translation(-Framework.getInstance().getWidth(),0,0));
            System.out.println("Max: "+Framework.getInstance().getWidth()+","+Framework.getInstance().getHeight()+"\nCurrent position: "+currentPosition.x+","+currentPosition.y);
        } else if(currentPosition.x <= 0){
            System.out.println("Max: "+Framework.getInstance().getWidth()+","+Framework.getInstance().getHeight()+"\nCurrent position: "+currentPosition.x+","+currentPosition.y);
            ship.getNode().add(new Translation(-Framework.getInstance().getWidth(),0,0));
        }

        if(currentPosition.y <= 0){
            System.out.println("Max: " + Framework.getInstance().getWidth() + "," + Framework.getInstance().getHeight() + "\nCurrent position: " + currentPosition.x + "," + currentPosition.y);
            ship.getNode().add(new Translation(-Framework.getInstance().getHeight(), 0, 0));
        }else if(currentPosition.y >= Framework.getInstance().getHeight()){
            System.out.println("Max: "+Framework.getInstance().getWidth()+","+Framework.getInstance().getHeight()+"\nCurrent position: "+currentPosition.x+","+currentPosition.y);
            ship.getNode().add(new Translation(-Framework.getInstance().getHeight(),0,0));
        }

    }

    /**
     * Diese Methode kann zum Rotieren des Ship-Objektes nach Rechts verwendet werden. Die Rotation
     * eines Objektes besteht aus den folgenden drei Schritten:
     * 1. In den Nullpunkt des Koordinatensystems verschieben
     * 2. Um einen Winkel rotieren
     * 3. In die Ursprüngliche Position zurück verschieben
     */
    private void turnRight() {
        angle.value += ( Math.PI / 22 ) % ( 2 * Math.PI );

        rotate(1);
    }

    /**
     * Diese Methode kann zum Rotieren des Ship-Objektes nach Links verwendet werden. Die Rotation
     * eines Objektes besteht aus den folgenden drei Schritten:
     * 1. In den Nullpunkt des Koordinatensystems verschieben
     * 2. Um einen Winkel rotieren
     * 3. In die Ursprüngliche Position zurück verschieben
     */
    private void turnLeft() {
        angle.value -= ( Math.PI / 22 ) % ( 2 * Math.PI );

        rotate(0);
    }

    private void rotate(int r){
        Vertex3f center=ship.getCenter();
        ship.getNode().add(new Translation(center.x,center.y,center.z));
        switch (r) {
            case 0:
                ship.getNode().add(new RotationZ( -Math.PI / 22));
                break;
            case 1:
                ship.getNode().add(new RotationZ(  Math.PI / 22));
                break;
        }
        ship.getNode().add(new Translation(-center.x,-center.y,-center.z));
    }

    public void setKeyEvent(int Key){
        this.KeyEvent = Key;
   }

    public Shot ShootWeapon(){
        // Richtigen Winkel übergeben mittels angle.value (wird mit Rotation manipuliert)
        return new Shot(Ship.getInstance().getCurrentPosition().x, Ship.getInstance().getCurrentPosition().y, new Angle(angle.value), new Velocity(30));
    }

}
