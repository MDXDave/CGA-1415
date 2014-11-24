import java.util.Random;

/**
 * Die SpreadFunction gibt an, in welche Richtung ein Paritkel freigesetzt wird.
 *
 * @author Alex Dobrynin
 */
public class SpreadDescriptor {

    private double minAngle;
    private double maxAngle;

    public SpreadDescriptor(double minAngle, double maxAngle) {
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    /**
     * Berechnet auf Basis von minAngle und maxAngle einen zufälligen Winkel.
     *
     * @return double Winkel
     */
    public double getSpreadingAngle() {
        double random = new Random().nextDouble();
        double result = minAngle + (random * (maxAngle - minAngle));
        System.out.println("RandomAngle: "+result);
        return result;
    }
}
