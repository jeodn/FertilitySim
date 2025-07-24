import java.util.List;

public abstract class Creature {
    public Vector position;
    public Vector velocity;
    public Vector acceleration;
    public double MAX_SPEED = 2.0;
    public double PERCEPTION_RADIUS = 50.0;
    public double MAX_FORCE = 0.05;
    public int WIDTH = Constants.SCREEN_WIDTH;
    public int HEIGHT = Constants.SCREEN_HEIGHT;

    public Creature(double x, double y) {
        position = new Vector(x, y);
        velocity = Vector.random2D();
        acceleration = new Vector(0, 0);
    }

    public void update() {
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.multiply(0); // reset acceleration
    }

    public void applyForce(Vector force) {
        acceleration.add(force);
    }

    public Vector avoidEdges(double width, double height) {
        double edgeBuffer = 50; // how close before reacting
        double steerStrength = MAX_FORCE; // how hard to steer back

        Vector steering = new Vector(0, 0);

        if (position.x < edgeBuffer) {
            steering.add(new Vector(steerStrength, 0)); // steer right
        } else if (position.x > width - edgeBuffer) {
            steering.add(new Vector(-steerStrength, 0)); // steer left
        }

        if (position.y < edgeBuffer) {
            steering.add(new Vector(0, steerStrength)); // steer down
        } else if (position.y > height - edgeBuffer) {
            steering.add(new Vector(0, -steerStrength)); // steer up
        }

        return steering;
    }

    abstract void applyBehaviors(List<Creature> creatures);


}
