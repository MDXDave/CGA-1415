/**
 * ParticleLife gibt an, wie viele Frames ein Partikel am Leben bleibt.
 *
 * @author Alex Dobrynin
 */
public class ParticleLife {

    private int life;

    public ParticleLife(int life) {
        this.life = life;
    }

    /**
     * Reduziert das Leben des Partikels
     *
     * @return int verbleibende Frames
     */
    public int decreaseLife() {
        return this.life -= 2;
    }

    /**
     * Gibt die verbleibenden Frames zurück
     *
     * @return int verbleibende Frames
     */
    public int getLife() {
        return life;
    }
}
