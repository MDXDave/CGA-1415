import com.google.common.eventbus.Subscribe;
import org.amcgala.Amcgala;
import org.amcgala.Scene;
import org.amcgala.camera.OrthographicCamera;
import org.amcgala.event.InputHandler;
import org.amcgala.event.KeyPressedEvent;
import org.amcgala.event.Update;
import org.amcgala.math.Vector3d;
import org.amcgala.math.Vertex3f;
import org.amcgala.shape.Box;

import java.awt.event.KeyEvent;

/**
 * Einfache Szene, die eine 3D Box zeigt.
 */
public class Main extends Amcgala implements InputHandler {

    private Scene scene = new Scene("box3d");
    private Vector3d direction = new Vector3d(300, 300, 0);
    private Vector3d position = new Vector3d(300, 300, 1);
    private PerspectiveCam pCam = new PerspectiveCam(Vector3d.UNIT_Y, position, direction, 1000);
    private OrthographicCamera oCam = new OrthographicCamera(Vector3d.UNIT_Y, position, direction);
    private IsometricCam isoCam = new IsometricCam(Vector3d.UNIT_Y, position, direction);

    private int camChoice = 0;

    public Main() throws Exception {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    scene.addShape(new Box(new Vertex3f(-100 + i * 100, j * 120, -5 + k * 190), 40, 40, 40));
                }
            }

        }

        scene.setCamera(oCam);
        framework.addScene(scene);
        framework.loadScene(scene);
	    scene.addInputHandler(this, "switch");
    }

    @Subscribe
    public void animateCamera(Update updateEvent) {
        position.setX(position.getX() + 0.001);
        switch (camChoice) {
            case 0:
                oCam.setPosition(position);
                break;
            case 1:
                /* Isometrische Kamera */
                isoCam.setPosition(position);
                break;
            case 2:
                /* Perspektivische Kamera */
                pCam.setPosition(position);
                break;
        }
    }

    @Subscribe
    public void switchCamera(KeyPressedEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            camChoice = (++camChoice) % 3;

            switch (camChoice) {
                case 0:
                    scene.setCamera(oCam);
                    break;
                case 1:
                    scene.setCamera(isoCam);
                    break;
                case 2:
                   scene.setCamera(pCam);
                    break;
                default:
                    camChoice = 0;
                    scene.setCamera(oCam);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Weitere Informationen: https://mdxdave.de/cga4
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
