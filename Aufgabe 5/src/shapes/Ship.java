package shapes;

import com.google.common.base.Optional;
import org.amcgala.Framework;
import org.amcgala.animation.Updatable;
import org.amcgala.event.CollisionEvent;
import org.amcgala.math.Matrix;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.shape.Triangle;
import model.ShipModel;
import org.amcgala.shape.util.collision.Collidable;
import org.amcgala.shape.util.collision.Collision;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Die View eines Raumschiffes, welches lediglich die visuelle Repräsentation des Schiffes enthält. Die View ist
 * vom Model entkoppelt, weswegen die View problemlos durch ein anderes Shape ersetzt werden kann.
 *
 * @author Alex Dobrynin
 */
public final class Ship extends Triangle implements Updatable, Collidable {

    private static Ship instance;
    private ShipModel shipModel;

    private Node node;
    private String name;

    private Ship(Vertex3f a, Vertex3f b, Vertex3f c) {
        super(a, b, c);
        this.shipModel = new ShipModel(this);
        this.name = "Ship_" + UUID.randomUUID().toString();
        this.node = new Node(name);
    }

    /**
     * Das Ship-Objekt ist ein Singleton-Objekt (existiert nur 1 Mal)
     */
    public static Ship createInstance(Vertex3f a, Vertex3f b, Vertex3f c) {
        checkArgument(instance == null, "there can only be one instance of ship");
        instance = new Ship(a, b, c);
        return instance;
    }

    /**
     * Auf das Ship-Objekt kann global zugegriffen werden
     */
    public static Ship getInstance() {
        if (instance == null) {
            throw new IllegalArgumentException("no instance of ship available");
        } else {
            return instance;
        }
    }

    @Override
    public Optional<Collision> collideWith(Collidable other) {
        return Optional.absent();
    }

    @Override
    public void update() {
        Framework.getInstance().getEventBus().post(new CollisionEvent(this));
        shipModel.onViewUpdate();
    }

    /**
     * Gibt die aktuelle Position des Schiffes unter Berücksichtigung jeglicher
     * Transformationsmatrizen zurück
     *
     * @return Vertex3f der aktuellen Position
     */
    public Vertex3f getCurrentPosition() {
        Matrix transformMatrix = this.getNode().getTransformMatrix();
        Vertex3f am = transformMatrix.times(getA().toVector().toMatrix()).toVertex3f();
        Vertex3f bm = transformMatrix.times(getB().toVector().toMatrix()).toVertex3f();
        Vertex3f cm = transformMatrix.times(getC().toVector().toMatrix()).toVertex3f();
        return new Vertex3f((am.x + bm.x + cm.x) * 1 / 3, (am.y + bm.y + cm.y) * 1 / 3, (am.z + bm.z + cm.z) * 1 / 3);
    }

    @Override
    public double getRadius() {
        return (Math.sqrt(3) / 3 * (30 - 10));
    }

    /**
     * Gibt den Mittelpunkt des Schiffes zurück. Wird zum Rotieren um die eigene Achse verwendet!
     *
     * @return Vertex3f des Mittelpunkts
     */
    public Vertex3f getCenter() {
        return new Vertex3f((getA().x + getB().x + getC().x) * 1 / 3, (getA().y + getB().y + getC().y) * 1 / 3, (getA().z + getB().z + getC().z) * 1 / 3);
    }


    public void resetPosition(){
        Framework.getInstance().getActiveScene().removeNode(this.getNode());
        instance = null;
        Ship ship = createInstance(new Vertex3f(280, 215, 0), new Vertex3f(240, 200, 0), new Vertex3f(240,230,0));
        Framework.getInstance().getActiveScene().addShape(ship, ship.getNode());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Node getNode() {
        return node;
    }

    public ShipModel getShipModel() {
        return shipModel;
    }
}
