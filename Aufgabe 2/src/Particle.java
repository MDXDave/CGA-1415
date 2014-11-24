import org.amcgala.Framework;
import org.amcgala.RGBColor;
import org.amcgala.animation.Updatable;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Point;

import java.util.UUID;

/**
 * Ein Particle wird vom ParticleEmitter erzeugt und bewegt sich abhängig von seiner Beschleunigung in
 * einem bestimmten Winkel. Wenn seine Lebenszeit abgelaufen ist, wird er vom Bildschirm entfernt.
 *
 * @author Alex Dobrynin
 */
public class Particle extends Point implements Updatable {

    private final ColorDescriptor colorDescriptor;
    private final ParticleLife particleLife;
    private final double velocity;
    private double angle;
    private Node node;
    private String name;

    public Particle(float x, float y, double angle, double velocity, ParticleLife particleLife, ColorDescriptor colorDescriptor) {
        super(x, y, 1);
        this.name = "Particle_" + UUID.randomUUID().toString();
        this.node = new Node(name);
        this.colorDescriptor = colorDescriptor;
        this.particleLife = particleLife;
        this.velocity = velocity;
        this.angle = angle;
    }

    /**
     * Die update Methode wird 1 Mal pro Frame aufgerufen und simuliert seine Bewegung mithilfe
     * von Translationen auf dem Knoten. Benutzen Sie für das Reduzieren der Lebenszeit die Methoden
     * der ParticleLife Klasse
     */
    @Override
    public void update() {
        particleLife.decreaseLife();
        int life = particleLife.getLife();
        if(life > 0) {

            double phi = angle;
            int x = (int) (velocity*Math.cos(phi));
            int y = (int) (velocity*Math.sin(phi));

            node.add(new Translation(x,y,1));

            // TODO Knoten entfernen, wenn Lebenszeit abgelaufen ist
            // Bewegung mithilfe einer Translation implementieren (x = v * cos(phi), y = v * sin(phi))

            // Farbe des Partikels abhängig von particleLife mithilfe des colorDescriptors setzen
            RGBColor rgb = colorDescriptor.getColorFor(particleLife.getLife());
            super.setColor(rgb);


        }else{
            node.removeShapes();
            //Framework.getInstance().getActiveScene().removeNode(node);
        }
    }

    public Vertex3f getCurrentPosition() {
        return this.getNode().getTransformMatrix().times(getPoint().toVector().toMatrix()).toVertex3f();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Node getNode() {
        return node;
    }
}
