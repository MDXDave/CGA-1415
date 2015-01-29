package model;

import helper.Velocity;
import org.amcgala.Framework;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Shape;
import shapes.Asteroid;

/**
 * Das Model eines Asteroiden, welches jegliche Anwendungslogik eines Asteroiden enthält. Die Anwendungslogik des
 * Models ist von der View entkoppelt, weswegen theoretisch jedes Shapes das Verhalten eines Asteroiden (Reckteck,
 * Linie, Polygon) simulieren kann.
 *
 * @author Alex Dobrynin
 */
public class AsteroidModel {

    private Asteroid asteroid;
    private Velocity velocity;

    public AsteroidModel(Shape shape) {
        this.velocity=new Velocity(0.5);
        this.asteroid = (Asteroid) shape;
    }

    public void onViewUpdate() {
        asteroid.getNode().add(new Translation(this.velocity.value*Math.cos(asteroid.getAngle().value),this.velocity.value*Math.sin(asteroid.getAngle().value),0));
        Vertex3f currentPosition=asteroid.getCurrentPosition();

        if(currentPosition.x >= Framework.getInstance().getWidth())
            asteroid.getNode().add(new Translation(-Framework.getInstance().getWidth(),0,0));

        if(currentPosition.y >= Framework.getInstance().getHeight())
            asteroid.getNode().add(new Translation(0,-Framework.getInstance().getHeight(),0));

        if(currentPosition.x <= 0)
            asteroid.getNode().add(new Translation(Framework.getInstance().getWidth(),0,0));

        if(currentPosition.y <= 0)
            asteroid.getNode().add(new Translation(0,Framework.getInstance().getHeight(),0));


    }

}
