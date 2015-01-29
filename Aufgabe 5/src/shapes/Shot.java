package shapes;

import com.google.common.base.Optional;
import helper.Angle;
import helper.Velocity;
import model.ShotModel;
import org.amcgala.Framework;
import org.amcgala.animation.Updatable;
import org.amcgala.event.CollisionEvent;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.shape.Rectangle;
import org.amcgala.shape.util.collision.Collidable;
import org.amcgala.shape.util.collision.Collision;

import java.util.UUID;

/**
 * Die View eines Schusses, welches lediglich die visuelle Repräsentation eines Schusses enthält. Die View ist
 * vom Model entkoppelt, weswegen die View problemlos durch ein anderes Shape ersetzt werden kann.
 *
 * @author Alex Dobrynin
 */
// TODO Ihre Klasse Shot soll das Interface Collidable implementieren
public class Shot extends Rectangle implements Updatable, Collidable {

    private ShotModel shotModel;

    private Node node;
    private String name;

    public Shot(float x, float y, Angle angle, Velocity velocity) {
        super(new Vertex3f(x, y, 1), 2, 2);
        shotModel = new ShotModel(this, angle, velocity);
        this.name = "Shot_" + UUID.randomUUID().toString();
        this.node = new Node(name);
    }

    public void update() {
        // ShotModel updaten
        shotModel.onViewUpdate();

        if (this.getNode() != null) Framework.getInstance().getEventBus().post(new CollisionEvent(this));
    }

    public Node getNode() {
        return node;
    }

    /**
     * Die collideWith() Methode muss an dieser Stelle nicht implementiert werden, da die Gegenstelle (Asteroid Klasse)
     * die Implementierung uebernimmt.
     */
    // TODO Übernehmen Sie diese Methode
    @Override
    public Optional<Collision> collideWith(Collidable other) {
        return Optional.absent();
    }

    public Vertex3f getCurrentPosition() {
        return this.getNode().getTransformMatrix().times(getV().toVector().toMatrix()).toVertex3f();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Die Methode gibt den Radius einer nicht sichtbaren Box um den Schuss zurueck. Diese Methode kann zur
     * Kollisionserkennung verwendet werden.
     *
     * @return radius des Schusses
     */
    // TODO Übernehmen Sie diese Methode
    public double getRadius() {
        return 0;
    }

    public ShotModel getShotModel() {
        return shotModel;
    }
}
