import org.amcgala.Amcgala;
import org.amcgala.Scene;
import org.amcgala.math.Vector3d;
import org.amcgala.scenegraph.Node;

/**
 * TesselatePolygon erzeugt beispielhafte Polygone, um die Triangulation zu testen.
 *
 * @author Alex Dobrynin
 */
public class TesselatePolygon extends Amcgala {

    public TesselatePolygon() throws Exception {
        Scene scene = new Scene("one");

        //Das ist das Testpolygon, dessen Triangulation Sie Schritt für Schritt auf einem Blatt papier nachvollziehen müssen
        MyPolygon testPolygon = new MyPolygon(
                new Vector3d(400, 400, 1),
                new Vector3d(450, 410, 1),
                new Vector3d(450, 490, 1),
                new Vector3d(370, 550, 1),
                new Vector3d(340, 480, 1)
        );

        MyPolygon testPolygon2 = new MyPolygon(
                new Vector3d(40, 10, 1),
                new Vector3d(130, 100, 1),
                new Vector3d(180,110, 1),
                new Vector3d(160,180, 1),
                new Vector3d(100,230, 1),
                new Vector3d(20,140, 1),
                new Vector3d(60,120, 1),
                new Vector3d(70, 180, 1)
        );

        MyPolygon testPolygon3 = new MyPolygon(
                new Vector3d(200, 210, 1),
                new Vector3d(300, 210, 1),
                new Vector3d(300, 280, 1),
                new Vector3d(200, 280, 1)
        );

        scene.addShape(testPolygon, new Node("polygon"));
        scene.addShape(testPolygon2, new Node("polygon2"));
        scene.addShape(testPolygon3, new Node("polygon3"));

        // Weitere Test-Polygone der Klasse MyPolygon erzeugen und der Scene hinzufügen, um die Triangulation zu testen

        framework.addScene(scene);
        framework.loadScene(scene);
    }

    public static void main(String[] args) {
        try {
            new TesselatePolygon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
