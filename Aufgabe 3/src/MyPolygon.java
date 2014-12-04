import org.amcgala.math.Vector3d;
import org.amcgala.math.Vertex3f;
import org.amcgala.shape.Point;
import org.amcgala.shape.Polygon;
import org.amcgala.shape.Triangle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * MyPolygon
 *
 * @author Alex Dobrynin
 */
public class MyPolygon extends Polygon {

    private Vector3d[] polygon;
    public static final float EPSILON = 0.0000000001f;

    public MyPolygon(Vector3d... points) {
        super(points);
        polygon = points;
    }

    /**
     * Die Methode berechnet die in dem Polygon befindlichen Dreicke und gibt diese als Liste zurück
     */
    @Override
    protected List<Triangle> tesselatePolygon() {
        List<Triangle> triangles = new ArrayList<>();

        // Werfe Exception, wenn das Polygon aus weniger als drei Vertices besteht
        int length = polygon.length;
        if(length < 3) throw new IllegalStateException("Das Polygon muss aus mind. 3 Vertices bestehen!");

        /* Hilfsarray, um die Indizes der vertices zwischenzuspeichern */
        int[] vertices = new int[length];
        if (0 < getArea(polygon)) {
            for (int v = 0; v < length; v++) vertices[v] = v;
        }
         else {
            for (int v = 0; v < length; v++) vertices[v] = (length-1)-v;
        }

        int currentIndex = length-1;
        while (length > 2) {
            // Es werden immer die nächsten drei aufeinanderfolgenden Vertices im Polygon betrachtet */
            int previousIndex = currentIndex; if (length <= previousIndex) previousIndex = 0; /* vorheriger Vertex */
            currentIndex = previousIndex+1; if (length <= currentIndex) currentIndex = 0; /* aktueller Vertex */
            int nextIndex = currentIndex+1; if (length <= nextIndex) nextIndex = 0; /* nächster Vertex */

            // Überprüfung, ob eine Zerlegung in ein Dreick möglich ist
            if (snip(polygon, previousIndex, currentIndex, nextIndex, length, vertices)) {
                int a, b, c;

                /* Koordinaten des Dreiecks aus dem Hilfsarray extrahieren */
                a = vertices[previousIndex]; b = vertices[currentIndex]; c = vertices[nextIndex];
                /* Aus den extrahierten Koordinaten das Dreieck erzeugen und der Liste hinzufügen*/
                Triangle triangle = new Triangle(polygon[a].toVertex3f(), polygon[b].toVertex3f(), polygon[c].toVertex3f());
                triangles.add(triangle);

                /* Das Hilfsarray um einen Index verschieben */
                for (int q = currentIndex, p=currentIndex+1; p < length; q++, p++) {
                    vertices[q] = vertices[p];
                }
                /* Ein Vertex weniger muss betrachtet werden */
                length--;
            }
        }

        return triangles;
    }

    /**
     * Überprüft, ob auf Basis der übergebenen Koordinaten eine Zerlegung in ein Dreieck in Frage kommt
     */
    private boolean snip(Vector3d[] polygon, int previousIndex, int currentIndex, int nextIndex, int length, int[] vertices) {
        /* Das Dreieck aus den Indizes extrahieren */
        Vertex3f a = new Vertex3f((float) polygon[vertices[previousIndex]].getX(), (float) polygon[vertices[previousIndex]].getY(), 1);
        Vertex3f b = new Vertex3f((float) polygon[vertices[currentIndex]].getX(), (float) polygon[vertices[currentIndex]].getY(), 1);
        Vertex3f c = new Vertex3f((float) polygon[vertices[nextIndex]].getX(), (float) polygon[vertices[nextIndex]].getY(), 1);

        /* Konkave Ecken ausschießen  */
        if (EPSILON > (((b.x-a.x)*(c.y-a.y)) - ((b.y-a.y)*(c.x-a.x))) ) return false;

        for (int i = 0; i < length; i++) {
            /* Es soll keiner der Punkte des Dreiecks sein */
            if( (i == previousIndex) || (i == currentIndex) || (i == nextIndex) ) continue;
            /* Nächst gefundenen Punkt extrahieren und prüfen */
            Point point = new Point((float) polygon[vertices[i]].getX(), (float) polygon[vertices[i]].getY(), 1);
            Triangle triangle = new Triangle(a, b, c);
            if (isInsideTriangle(point, triangle)) return false;
        }
        
        return true;
    }

    private float calculate(Vertex3f p1, Vertex3f p2, Vertex3f p3)
    {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    /**
     * Überprüft, ob der übergebene Punkt im übergebenen Dreieck liegt.
     */
    private boolean isInsideTriangle(Point point, Triangle triangle) {
        // Herausfinden, ob der übergebene Punkt im Dreieck liegt

        Vertex3f pointV = point.getPoint();
        Vertex3f p1 = triangle.getA();
        Vertex3f p2 = triangle.getB();
        Vertex3f p3 = triangle.getC();

        boolean m, d, x;

        m = calculate(pointV, p1, p2) < 0f;
        d = calculate(pointV, p2, p3) < 0f;
        x = calculate(pointV, p3, p1) < 0f;

        return ((m == d) && (d == x));
    }

    /**
     * Berechnet die Fläche des übergebenen Polygons. Die Formel können Sie der
     * Aufgabenbeschreibung entnehmen.
     */
    private double getArea(Vector3d[] polygon) {

        int num = polygon.length;

        double area = 0;
        Vector3d lastVector = polygon[num-1];

        for (Vector3d thisVector : polygon) {
            area = area + (lastVector.getX() + thisVector.getX()) * (lastVector.getY() + thisVector.getY());
            lastVector = thisVector;
        }
        return area/2;
    }

}


