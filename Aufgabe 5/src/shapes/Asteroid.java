package shapes;

import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;
import helper.Angle;
import org.amcgala.Framework;
import org.amcgala.event.CollisionEvent;
import org.amcgala.event.InputHandler;
import org.amcgala.math.Matrix;
import org.amcgala.math.Vertex3f;
import org.amcgala.scenegraph.Node;
import org.amcgala.scenegraph.transform.Translation;
import org.amcgala.shape.Rectangle;
import model.AsteroidModel;
import org.amcgala.shape.Shape;
import org.amcgala.shape.util.collision.Collidable;
import org.amcgala.shape.util.collision.Collision;
import program.Asteroids;


import java.util.UUID;

/**
 * Die View eines Asteroiden, welches lediglich die visuelle Repräsentation des Asteroiden enthält. Die View ist
 * vom Model entkoppelt, weswegen die View problemlos durch ein anderes Shape ersetzt werden kann.
 *
 * @author Alex Dobrynin
 */
public class Asteroid extends Rectangle implements InputHandler, Collidable {

    private AsteroidModel asteroidModel;

    private Node node;
    private String name;
    private Angle angle;
    private boolean crushed;

    public Asteroid(Vertex3f v, float width, float height, Angle angle, boolean crushed) {
        super(v, width, height);
        this.asteroidModel = new AsteroidModel(this);
        this.name = "Asteroid_" + UUID.randomUUID().toString();
        this.node = new Node(name);
        this.angle = angle;
        this.crushed = crushed;

        Framework.getInstance().addInputHandler(this, name);
    }

    public Angle getAngle(){
        return this.angle;
    }

    public boolean isCollidingWith(Collidable otherObject) {

        if(otherObject instanceof Asteroid){
            return false;
        }

        Vertex3f otherPosition = otherObject.getCurrentPosition();
        Vertex3f asteroidPosition = getCurrentPosition();

        return Math.pow( getRadius() + otherObject.getRadius(), 2) > (Math.pow(otherPosition.x-asteroidPosition.x, 2)+ Math.pow(otherPosition.y-asteroidPosition.y, 2));

    }

    @Override
    public Optional<Collision> collideWith(Collidable target) {
        return isCollidingWith(target) ? Optional.of(new Collision(this, target, target.getCurrentPosition())) : Optional.<Collision>absent();
    }

    public Vertex3f getCurrentPosition() {
        Matrix transformMatrix = this.getNode().getTransformMatrix();
        Vertex3f am = transformMatrix.times(getV().toVector().toMatrix()).toVertex3f();
        Vertex3f[] vertices = new Vertex3f[4];
        vertices[0] = am;
        vertices[1] = new Vertex3f(am.x + getWidth(), am.y, 1);
        vertices[2] = new Vertex3f(am.x, am.y + getHeight(), 1);
        vertices[3] = new Vertex3f(am.x + getWidth(), am.y + getHeight(), 1);
        return new Vertex3f((vertices[0].x + vertices[1].x + vertices[2].x + vertices[3].x) * 1 / 4, (vertices[0].y + vertices[1].y + vertices[2].y + vertices[3].y) * 1 / 4, (vertices[0].z + vertices[1].z + vertices[2].z + vertices[3].z) * 1 / 4);
    }

    @Override
    public double getRadius() {
        return Math.sqrt(Math.pow(getWidth() / 2, 2) + Math.pow(getHeight() / 2, 2));
    }

    /**
     * Der Asteroid lauscht dem CollisionEvent und ueberprueft, ob es bei dem uebergebenen Event zu einer Kollision gekommen ist.
     * Wenn ja, wird unterschieden, der Asteroid mit einem Shot- oder Ship-Objekt kollidiert ist. Dementsprechend soll auf die
     * Situation reagiert werden.
     */
    @Subscribe
    public void onCollisionEvent(CollisionEvent event) {
        Optional<Collision> maybeCollision = collideWith(event.getCollidable());
        if (maybeCollision.isPresent()) { // Es ist zu einer Kollision gekommen
            // Das Shape, mit dem der Asteroid kollidiert ist und die Position der Kollision
            Shape collisionTarget = (Shape) maybeCollision.get().getTarget();
            Vertex3f collisionPosition = maybeCollision.get().getPosition();

            System.out.println("Kollision mit " + this.getNode().getLabel() + " durch " + collisionTarget);
            if (collisionTarget instanceof Shot) {


                if(!crushed){
                    // Asteroid splitten
                    removeIt(collisionTarget.getNode());

                    Asteroid SplitOne = new Asteroid(collisionPosition,this.getWidth()/2, this.getWidth()/2, new Angle(Math.random()*(2*Math.PI)), true);
                    Asteroid SplitTwo = new Asteroid(collisionPosition,this.getWidth()/2, this.getWidth()/2, new Angle(Math.random()*(2*Math.PI)), true);

                    Asteroids.scene.addShape(SplitOne, SplitOne.getNode());
                    Asteroids.scene.addShape(SplitTwo, SplitTwo.getNode());

                    Asteroids.increaseAsteroiden();

                }else{
                    //Asteroid entfernen
                    removeIt(collisionTarget.getNode());
                    Asteroids.decreaseAsteroiden();
                }

                if(Asteroids.countAsteroiden()<=0){
                    // Spiel beenden
                    System.out.println("Game Over!");
                    gameOver();
                }


            } else if (collisionTarget instanceof Ship) {
               // Ship.getInstance().getNode().add(new Translation(300, 0, 0));
                Ship.getInstance().resetPosition();
            }
        }
    }

    private void gameOver() {

        // G
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(180, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(190, 230, 0),30,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(190, 290, 0),30,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(210, 260, 0),10,30, new Angle(0), false));

        // A
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(240, 230, 0),30,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(240, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(270, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(240, 260, 0),30,10, new Angle(0), false));

        // M
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(340, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 230, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(310, 230, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(320, 240, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(330, 230, 0),10,10, new Angle(0), false));

        // E
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 230, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 260, 0),40,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 230, 0),40,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 290, 0),40,10, new Angle(0), false));


        // O
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(180, 310, 0),30,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(180, 310, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(210, 310, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(180, 370, 0),30,10, new Angle(0), false));

        // V
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(240, 310, 0),10,60, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(270, 310, 0),10,60, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(250, 370, 0),20,10, new Angle(0), false));

        // E
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 310, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 340, 0),40,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 310, 0),40,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(300, 370, 0),40,10, new Angle(0), false));

        // R
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 310, 0),30,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 310, 0),10,70, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(400, 310, 0),10,40, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(380, 350, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(390, 360, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(400, 370, 0),10,10, new Angle(0), false));
        Asteroids.scene.addShape(new Asteroid(new Vertex3f(370, 340, 0),30,10, new Angle(0), false));

        Asteroids.scene.removeNode(Ship.getInstance().getNode());
        Asteroids.gameOver();
    }

    public void removeIt(Node node){
        Framework.getInstance().getActiveScene().removeNode(this.getNode());
        Framework.getInstance().removeInputHandler(this.name);
        Framework.getInstance().getActiveScene().removeNode(node);
    }

    @Override
    public void update() {
        asteroidModel.onViewUpdate();
    }

    public Node getNode() {
        return node;
    }

    public AsteroidModel getAsteroidModel() {
        return asteroidModel;
    }
}