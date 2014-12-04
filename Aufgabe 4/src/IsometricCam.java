
import org.amcgala.camera.AbstractCamera;
import org.amcgala.math.Matrix;
import org.amcgala.math.Vector3d;
import org.amcgala.renderer.Pixel;

/**
 * Kamera, die die isometrische Projektion implementiert.
 * Weitere Informationen: https://mdxdave.de/cga4
 */
public class IsometricCam extends AbstractCamera {

    /**
     *  @param vup Das Oben der Kamera
     * @param position  Die Position der Kamera
     * @param direction Der Punkt, zu dem die Kamera blickt
     */
    public IsometricCam(Vector3d vup, Vector3d position, Vector3d direction) {
        this.up = vup;
        this.location = position;
        this.direction = direction;
        update();
    }

    @Override
    protected Matrix getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public Pixel project(Vector3d vector3d) {
        Matrix point = projectionMatrix.times(vector3d.toMatrix());
        return new Pixel(point.get(0, 0) / point.get(3, 0), point.get(1, 0) / point.get(3, 0));
    }

    @Override
    public void update() {
        this.n = this.direction.sub(this.location).times(-1.0D);
        this.u = this.up.cross(this.n).normalize();
        this.v = this.n.cross(this.u).normalize();
        // Matrize für Multiplikation
        // Weitere Informationen: https://mdxdave.de/cga4
        double[][] var1 = new double[][]{{0.5*Math.sqrt(3), 0.0D, -(0.5*Math.sqrt(3)), 0.0D}, {-0.5, 1.0D, -0.5, 0.0D}, {0.0D, 0.0D, 0.0D, 0.0D}, {0.0D, 0.0D, 0.0D, 1.0D}};
        Matrix var2 = new Matrix(var1);
        Vector3d var3 = Vector3d.createVector3d(this.location.dot(this.u), this.location.dot(this.v), this.location.dot(this.n));
        double[][] var4 = new double[][]{{this.u.getX(), this.u.getY(), this.u.getZ(), var3.x}, {this.v.getX(), this.v.getY(), this.v.getZ(), var3.y}, {this.n.getX(), this.n.getY(), this.n.getZ(), var3.z}, {0.0D, 0.0D, 0.0D, 1.0D}};
        Matrix var5 = new Matrix(var4);
        this.projectionMatrix = var2.times(var5);
    }
}
