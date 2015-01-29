package helper;

/**
 * Beschreibt die Geschwindigkeit
 *
 * @author Alex Dobrynin
 */
public class Velocity {

    public double value;

    public Velocity(double value){
        this.value = value;
    }

    /**
     * Erhöht die Beschleunigung um den übergebenen Wert
     */
    public double increaseValueBy(double increase) {
        this.value += increase;
        return value;
    }

    /**
     * Setzt die Beschleunigung auf einen Startwert zurück
     */
    public void resetValue() {
        this.value = 0.5;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
