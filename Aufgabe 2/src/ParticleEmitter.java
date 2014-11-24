import org.amcgala.Framework;
import org.amcgala.math.Vertex3f;
import org.lwjgl.Sys;

/**
 * Ein ParticleEmitter erzeugt je nach SpawnRate Partikel, die sich in einem bestimmten Winkel
 * frei im Raum bewegen. Ein Partikel hat eine bestimmte Lebenszeit in Frames und passt seine
 * Farbe abhängig der Lebenszeit an.
 *
 * @author Alex Dobrynin
 */
public class ParticleEmitter {

    private int spawnRate;
    private int life;

    private SpreadDescriptor spreadDescriptor;
    private ColorDescriptor colorDescriptor;
    private PositionDescriptor positionDescriptor;

    /**
     * Die SpawnRate gibt an, welcher Frame pro Sekunde einen Partikel erzeugt.
     * Life gibt an, wie viele Frames ein Partikel existiert.
     *
     * @param spawnRate der Partikel
     * @param life der Partikel
     */
    public ParticleEmitter(int spawnRate, int life) {
        this.spawnRate = spawnRate;
        this.life = life;
    }

    public void update(Rocket rocket) {
        this.update(rocket, spreadDescriptor.getSpreadingAngle());
    }

    /**
     * Erzeugt abhängig von der spawnRate, dem Winkel und einer Position einen neuen Partikel
     *
     * @param rocket wird für die aktuelle Position verwendet
     * @param angle ist der Winkel für die Bewegung des Partikels
     */
    public void update(Rocket rocket, double angle) {
        Vertex3f position = rocket.getCurrentPosition();
        //System.out.println(spawnRate);

        // TODO ruft abhängig der spawnRate die Methode emitParticleShape auf

        emitParticleShape(new Particle(
                position.x +
                        positionDescriptor.getXOffset(),
                position.y + 10 +
                        positionDescriptor.getYOffset(),
                angle,
                rocket.getVelocity(),
                new ParticleLife(life),
                colorDescriptor));
    }

    private void emitParticleShape(Particle particle) {
        Framework.getInstance().getActiveScene().addShape(particle, particle.getNode());
    }

    public void setSpreadDescriptor(SpreadDescriptor spreadDescriptor) {
        this.spreadDescriptor = spreadDescriptor;
    }

    public void setColorDescriptor(ColorDescriptor colorDescriptor) {
        this.colorDescriptor = colorDescriptor;
    }

    public void setPositionDescriptor(PositionDescriptor positionDescriptor) {
        this.positionDescriptor = positionDescriptor;
    }

}
