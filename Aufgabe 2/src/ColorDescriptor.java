import org.amcgala.RGBColor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Der ColorDescriptor gibt die Farbe des Partikels abhängig von der Lebenszeit an. Jeder
 * Partikelemitter verfügt über einen ColorDescriptor.
 *
 * @author Alex Dobrynin
 */
public class ColorDescriptor {

    /**
     * Gibt auf Basis des Lebens eines Partikels die entsprechende Farbe als RGBColor zurück.
     * Ist life zwischen 300 und 200 wird die Farbe Rot zurückgegeben.
     * Ist life zwischen 200 und 100 wird die Farbe Orange zurückgegeben.
     * Ist life zwischen 100 und 0 wird die Farbe Gelb zurückgegeben.
     * Ansonsten wird die Farbe Weiß zurückgegeben
     * @param life des Partikels
     * @return RGBColor 
     */
    public RGBColor getColorFor(int life) {
        if(life>199&&life<301)
            return RGBColor.RED;
        else if(life>100)
            return RGBColor.ORANGE;
        else if(life<101)
            return RGBColor.YELLOW;
        else
            return RGBColor.WHITE;
    }
}
