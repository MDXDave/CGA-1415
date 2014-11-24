import org.amcgala.Framework;
import org.amcgala.animation.Updatable;
import org.amcgala.math.Matrix;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Triangle;

import java.util.UUID;

/**
 * Die Klasse Rocket simuliert eine Rakte, die nach einer countDownTime unter Berücksichtigung der
 * Velocity empor steigt und nach einer burnTime explodiert. Während des Aufsteigens und nach der
 * Explosion werden Partikel durch die entsprechenden ParticleEmitter freigesetzt.
 *
 * @author Alex Dobrynin
 */
public final class Rocket extends Triangle implements Updatable {

    private Node node;
    private String name;
    private double velocity = 0.1;
    private int maxVelocity;
    private int burnTime;
    private int countDownTime;
    private ParticleEmitter[] phaseOneEmitter;
    private ParticleEmitter phaseTwoEmitter;
    boolean phase1 = true;
    int frames = 0;


    private Rocket() {
        super(new Vertex3f(300, 450, 1), new Vertex3f(310, 480, 1), new Vertex3f(290, 480, 1));
        this.name = "Ship_" + UUID.randomUUID().toString();
        this.node = new Node(name);
    }

    public Rocket(int countDownTime, int burnTime, int maxVelocity, ParticleEmitter[] phaseOneEmitter, ParticleEmitter phaseTwoEmitter) {
        this();
        this.countDownTime = countDownTime;
        this.burnTime = burnTime;
        this.maxVelocity = maxVelocity;
        this.phaseOneEmitter = phaseOneEmitter;
        this.phaseTwoEmitter = phaseTwoEmitter;
    }

    /**
     * Die Update-Methode wird pro Frame 1 Mal ausgeführt und soll die Rakete nach der countDownTime
     * und unter Berücksichtiung der maxVelocity beschleunigen. Des Weiteren soll hier auf Basis der
     * burntTime der Phasenwechsel implementiert werden.
     *
     * Während sich die Rakete In Phase 1 befindet werden auf allen Phase 1 ParticleEmitter die Methode
     * update(this) aufgeführt. In Phase 2 wird auf dem Phase 2 ParticleEmitter die Methode update(this, angle)
     * ausgeführt. Der Winkel soll die kreisförmige Explosion der Particle simulieren. Hierfür muss mit jedem Aufruf
     * der Winkel etwas erhöht werden, bis der Kreis geschlossen (2 * PI) ist.
     *
     * Um die Rakete zu beginn von Phase 2 zu entfernen, kann der Aufruf Framework.getInstance().getActiveScene().removeNode(this.node);
     * benutzt werden.
     */
    @Override
    public void update() {
        frames++;
        //System.out.println("Frame: " + frames);

        if(frames>this.countDownTime) {
            /*Vertex3f[] values = getCurrentVertices();
            Vertex3f vertex3f = getCurrentPosition();
            int x = (int) vertex3f.x;
            int y = (int) vertex3f.y;
            int z = (int) vertex3f.z;*/

            if (maxVelocity > this.velocity) {
                velocity = velocity+0.02;
            }

            if(frames>burnTime)
                phase1 = false;



            if (phase1) {
                //node.add(new Translation(0,-velocity,0));
                node.add(new Translation(velocity * -Math.cos(Math.PI/2),velocity * -Math.sin(1),0));
                System.out.println("Phase 1");
                for (ParticleEmitter aPhaseOneEmitter : phaseOneEmitter) {
                    aPhaseOneEmitter.update(this);

                }
            } else {
                if(node.getShapes().size() > 0) {
                    System.out.println("Phase 2");
                    //node.removeShapes();
                    Framework.getInstance().getActiveScene().removeNode(this.node);
                    // Phase 2
                    /*for (int i = 0; i < Math.PI; i++) {
                        double angle = (Math.random()*50);;
                        phaseTwoEmitter.update(this, angle);
                    }*/

                    for (double i = Math.PI / 150; i < 2 * Math.PI; i += Math.PI / 150) {
                        //double angle = (Math.random()*50);;
                        phaseTwoEmitter.update(this, i);
                    }
                }
            }

        }





        // TODO Bewegung der Rakete mitnilfe einer Translation auf dem Knoten implementieren:
        // TODO Die Gleichung hierfür ist für x = v * -cos(phi) und für y = v * -sin(phi). v ist die Beschleinigung und phi der Winkel
    }

    private Vertex3f[] getCurrentVertices() {
        Matrix transformMatrix = this.getNode().getTransformMatrix();
        Vertex3f[] vertices = new Vertex3f[3];
        vertices[0] = transformMatrix.times(getA().toVector().toMatrix()).toVertex3f();
        vertices[1] = transformMatrix.times(getB().toVector().toMatrix()).toVertex3f();
        vertices[2] = transformMatrix.times(getC().toVector().toMatrix()).toVertex3f();
        return vertices;
    }

    private Vertex3f getCenterOf(Vertex3f am, Vertex3f bm, Vertex3f cm) {
        return new Vertex3f((am.x + bm.x + cm.x) * 1 / 3, (am.y + bm.y + cm.y) * 1 / 3, (am.z + bm.z + cm.z) * 1 / 3);
    }

    public Vertex3f getCurrentPosition() {
        Vertex3f[] currentVertices = getCurrentVertices();
        return getCenterOf(currentVertices[0], currentVertices[1], currentVertices[2]);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Node getNode() {
        return node;
    }

    public double getVelocity() {
        return velocity;
    }
}
