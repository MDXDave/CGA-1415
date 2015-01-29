package helper;

/**
 * Beschreibt einen Winkel in Bogenmaß
 *
 * @author Alex Dobrynin
 */
public class Angle {

    public double value;

    public Angle(double value) {
        this.value = value % (2 * Math.PI);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
