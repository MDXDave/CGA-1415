package model;

import helper.Angle;
import helper.Velocity;
import org.amcgala.Framework;
import org.amcgala.math.Vector3d;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Shape;
import shapes.Shot;

/**
 * Das Model eines Schusses, welches jegliche Anwendungslogik zum Steuern des Schusses enthält. Die Anwendungslogik des
 * Models ist von der View entkoppelt, weswegen theoretisch jedes Shapes das Verhalten eines Schusses simulieren kann.
 *
 * @author Alex Dobrynin
 */
public class ShotModel {

    private Shot shot;
    private Angle angle;
    private Velocity velocity;

    public ShotModel(Shape shape, Angle angle, Velocity velocity) {
        this.shot = (Shot) shape;
        this.angle = angle;
        this.velocity = velocity;
    }

    public void onViewUpdate() {
        // Bewegen der Schüsse
        this.shot.getNode().add(new Translation(velocity.value * Math.cos(this.angle.value), velocity.value * Math.sin(this.angle.value), 0));


        // Entfernen der Schüsse
        Vertex3f position = shot.getCurrentPosition();
        Framework framework = Framework.getInstance();
        if(position.y <= 0 || position.x <= 0 || position.y >= framework.getHeight() || position.x >= framework.getWidth())
            framework.getActiveScene().removeNode(shot.getNode());

   }

}
