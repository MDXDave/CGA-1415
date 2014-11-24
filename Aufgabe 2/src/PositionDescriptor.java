import java.util.Random;

/**
 * Der PositionDescriptor gibt an, wie hoch der Offset beim Erzeugen eines Partikel sein kann.
 *
 * @author Alex Dobrynin
 */
public class PositionDescriptor {

    private float minXOffset;
    private float maxXOffset;
    private float minYOffset;
    private float maxYOffset;
    private Random random = new Random(System.currentTimeMillis());

    public PositionDescriptor(float minXOffset, float maxXOffset, float minYOffset, float maxYOffset) {
        this.minXOffset = minXOffset;
        this.maxXOffset = maxXOffset;
        this.minYOffset = minYOffset;
        this.maxYOffset = maxYOffset;
    }

    /**
     * Berechnet auf Basis des minXOffset und maxXOffset einen zufälligen x-Offset.
     *
     * @return float xOffset
     */
    public float getXOffset() {
        float xOffset = random.nextFloat() * (maxXOffset - minXOffset) + minXOffset;
        return random.nextBoolean() ? xOffset * -1 : xOffset;
    }

    /**
     * Berechnet auf Basis des minYOffset und maxYOffset einen zufälligen y-Offset.
     *
     * @return float yOffset
     */
    public float getYOffset() {
        float yOffset = random.nextFloat() * (maxYOffset - minYOffset) + minYOffset;
        return random.nextBoolean() ? yOffset * -1 : yOffset;
    }
}
