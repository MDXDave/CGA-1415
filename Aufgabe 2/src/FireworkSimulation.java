import org.amcgala.Amcgala;
import org.amcgala.Scene;

/**
 * Die Klasse FireworkSimulation simuliert zwei Phasen einer Silvesterrakte. In der ersten Phase erzeugen
 * drei Partikelsysteme einen Feuerschweif, der nach unten gerichtet ist. Nach einem bestimmten Countdown
 * liegt die Rakete unter Berücksichtigung einer velocity empor und explodiert nach einer bestimmten Brennzeit.
 * Nachdem die Brennzeit abgelaufen ist, wird Phase zwei gestartet, indem die Rakete explodiert und ein viertes
 * Partikelsystem dafür sorgt, dass die Rakete kreisförmig Partikel abgibt. Alle Partikel haben eine Lebenszeit
 * (in Frames) und ändern ihre Farbe abhängig von dieser Zeit.
 *
 * @author Alex Dobrynin
 */
public class FireworkSimulation extends Amcgala {

    public FireworkSimulation() throws Exception {
        Scene scene = new Scene("FireworkScene");
        // Colordescriptor für die visuelle Darstellung der Partikel erzeugen
        ColorDescriptor colorDescriptor = new ColorDescriptor();

        // Phase 1 und Phase 2PartikelEmitter erzeugen
        ParticleEmitter internalParticle = createInternalParticleEmitter();
        ParticleEmitter externalLeftParticle = createExternalLeftParticleEmitter();
        ParticleEmitter externalRightParticle = createInternalRightParticleEmitter();
        ParticleEmitter phaseTwoEmitter = createPhaseTwoParticleEmitter(colorDescriptor);

        // Phase 1 PartikelEmitter mit ColorDescriptor verknüpfen
        ParticleEmitter[] phaseOneEmitter = {internalParticle, externalLeftParticle, externalRightParticle};
        for (ParticleEmitter aPhaseOneEmitter : phaseOneEmitter) {
            aPhaseOneEmitter.setColorDescriptor(colorDescriptor);
        }

        // Rakete mit allen Parametern erzeugen und mit PartikelEmitter versorgen
        Rocket rocket = new Rocket(50, 200, 10, phaseOneEmitter, phaseTwoEmitter);
        scene.addShape(rocket, rocket.getNode());

        framework.addScene(scene);
        framework.loadScene(scene);
    }

    /**
     * Erzeugt den Phase 2 PartikelEmitter. Der SpreadDescriptor erstreckt sich von 0 bis
     * 2 * PI (360 Grad).
     * @param colorDescriptor für Partikel
     *
     * @return ParticleEmitter
     */
    private ParticleEmitter createPhaseTwoParticleEmitter(ColorDescriptor colorDescriptor) {
        ParticleEmitter phaseTwoEmitter = new ParticleEmitter(1, 300);
        phaseTwoEmitter.setSpreadDescriptor(new SpreadDescriptor(0, 2 * Math.PI));
        phaseTwoEmitter.setPositionDescriptor(new PositionDescriptor(0, 10, 0, 10));
        phaseTwoEmitter.setColorDescriptor(colorDescriptor);
        return phaseTwoEmitter;
    }

    /**
     * Erzeugt den rechten Partikelemitter für Phase 1.
     *
     * @return ParticleEmitter
     */
    private ParticleEmitter createInternalRightParticleEmitter() {
        ParticleEmitter externalRightParticle = new ParticleEmitter(1, 300);
        externalRightParticle.setPositionDescriptor(new PositionDescriptor(0,10,0,10));
        externalRightParticle.setSpreadDescriptor(new SpreadDescriptor(Math.PI,Math.PI/20));
        // TODO setzen von Spread- und PositionDescriptor. Achten Sie auf die Auswirkung der Übergabeparameter
        return externalRightParticle;
    }

    /**
     * Erzeugt den linken PartikelEmitter für Phase 1.
     *
     * @return ParticleEmitter
     */
    private ParticleEmitter createExternalLeftParticleEmitter() {
        ParticleEmitter externalLeftParticle = new ParticleEmitter(1, 300);
        externalLeftParticle.setPositionDescriptor(new PositionDescriptor(0,5,0,5));
        externalLeftParticle.setSpreadDescriptor(new SpreadDescriptor(Math.PI,Math.PI/20));
        // TODO setzen von Spread- und PositionDescriptor. Achten Sie auf die Auswirkung der Übergabeparameter
        return externalLeftParticle;
    }

    /**
     * Erzeugt den internen PartikelEmitter für Phase 1.
     *
     * @return ParticleEmitter
     */
    private ParticleEmitter createInternalParticleEmitter() {
        ParticleEmitter internalParticle = new ParticleEmitter(1, 300);
        internalParticle.setPositionDescriptor(new PositionDescriptor(0,10,0,10));
        internalParticle.setSpreadDescriptor(new SpreadDescriptor(Math.PI / 22, Math.PI));
        return internalParticle;
    }

    public static void main(String[] args) {
        try {
            new FireworkSimulation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
