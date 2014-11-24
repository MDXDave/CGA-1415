import org.amcgala.Amcgala;
import org.amcgala.RGBColor;
import org.amcgala.Scene;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Line;

import java.util.Collection;

/**
 * Der Harmonische Oszillator soll eine Schleifenbewegung mithilfe einer gegebenen Gleichung simulieren.
 * Die berechnenten Punkte sollen anschließend sinnvoll auf dem Bildschirm ausgegeben werden.
 *
 * @author Alex Dobrynin
 */
public class Oszillator extends Amcgala {

    public Oszillator() throws Exception {
        Scene scene = new Scene("Oszillator");

        // Root-Knoten finden und anschließend den Nullpunkt mithilfe einer Translation in die Mitte des Bildschirms setzen
        Collection<Node> nodes = scene.getNodes();
        for (Node node : nodes) {
            if (node.getLabel().equals("root")) {
                //
                node.add(new Translation(300, 300, 0));
            }
        }

        // Das Koordinantensystem wird mithilfe von zwei Linien-Objekten gezeichnet. Hierfür werden die
        // beiden Objekte erzeugt und anschließend unter einem Knoten im Szenengraphen hinzugefügt
        Line yAchse = new Line(new Vertex3f(0, 300, 1), new Vertex3f(0, -300, 1));
        scene.addShape(yAchse, new Node("y-achse"));
        Line xAchse = new Line(new Vertex3f(-300, 0, 1), new Vertex3f(300, 0, 1));
        scene.addShape(xAchse, new Node("x-achse"));

        // Initialen Punkt der Pendelbewegung erzeugen und anschließend im Scenengraphen hinzufügen
        PendelPoint point = new PendelPoint(1, 1, 1);

        point.setColor(RGBColor.RED);
        scene.addShape(point, point.getNode());

        // Den Szenengraphen als "aktiv" setzen.
        framework.addScene(scene);
        framework.loadScene(scene);
    }

    public static void main(String[] args) {
        try {
            new Oszillator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
