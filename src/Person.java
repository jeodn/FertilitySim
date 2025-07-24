import java.util.List;

public class Person extends Creature {

    public Person(double x, double y) {
        super(x, y);
    }

    public Vector cohesion(List<Creature> boids) {
        Vector center = new Vector((double) Constants.SCREEN_WIDTH / 2, (double) Constants.SCREEN_HEIGHT / 2);  // center of mass

        center.multiply(1.0);

        // Desired direction to center
        Vector desired = center.copy();
        desired.subtract(this.position);  // vector pointing toward center
        desired.normalize();
        desired.multiply(MAX_SPEED);      // scale to max desired speed

        // Steering = desired velocity - current velocity
        Vector steering = desired.copy();
        steering.subtract(this.velocity);

        // Limit the steering force
        if (steering.magnitude() > MAX_FORCE) {
            steering.normalize();
            steering.multiply(MAX_FORCE);
        }

        return steering;
    }

    public Vector separation(List<Creature> boids) {
        Vector steering = new Vector(0, 0);
        int count = 0;

        for (Creature other : boids) {
            if (other != this) {
                double distance = this.position.distanceTo(other.position);
                if (distance < PERCEPTION_RADIUS) {
                    // Vector pointing away from neighbor
                    Vector diff = this.position.copy();
                    diff.subtract(other.position);
                    diff.multiply(1.0 / distance);  // weight by distance (closer = stronger)
                    steering.add(diff);
                    count++;
                }
            }
        }

        if (count > 0) {
            steering.multiply(1.0 / count);  // average

            // Turn into a steering force
            steering.normalize();
            steering.multiply(MAX_SPEED);
            steering.subtract(this.velocity);

            // Limit force
            if (steering.magnitude() > MAX_FORCE) {
                steering.normalize();
                steering.multiply(MAX_FORCE);
            }

            return steering;
        }

        // No close neighbors
        return new Vector(0, 0);
    }

    @Override
    public void applyBehaviors(List<Creature> creatures) {
        Vector sep = separation(creatures);
        Vector coh = cohesion(creatures);
        Vector edgeForce = avoidEdges(WIDTH, HEIGHT);  // Use actual dimensions

        sep.multiply(1.5);
        coh.multiply(1.0);
        edgeForce.multiply(2.0);

        applyForce(edgeForce);
        applyForce(sep);
        applyForce(coh);
    }
}
