import java.util.List;

public class Boid {
    public Vector position;
    public Vector velocity;
    public Vector acceleration;
    public double MAX_SPEED = 2.0;
    public double PERCEPTION_RADIUS = 50.0;
    public double MAX_FORCE = 0.05;
    public int WIDTH = 800;
    public int HEIGHT = 600;

    public Boid(double x, double y) {
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

    // Add methods: applyBehaviors(List<Boid> boids), edges(), etc.
    // TODO: IMPLEMENT THESE METHODS
    public Vector align(List<Boid> boids) {
        Vector avgVelocity = new Vector(0, 0);
        int count = 0;

        for (Boid other : boids) {
            if (other != this) {
                double distance = this.position.distanceTo(other.position);
                if (distance < PERCEPTION_RADIUS) {
                    avgVelocity.add(other.velocity);
                    count++;
                }
            }
        }

        if (count > 0) {
            avgVelocity.multiply(1.0 / count);  // average velocity

            // Turn into a steering force
            avgVelocity.normalize();
            avgVelocity.multiply(MAX_SPEED);

            Vector steering = avgVelocity.copy();
            steering.subtract(this.velocity);

            // Limit steering force
            if (steering.magnitude() > MAX_FORCE) {
                steering.normalize();
                steering.multiply(MAX_FORCE);
            }

            return steering;
        }

        return new Vector(0, 0);  // no neighbors
    }

    public Vector cohesion(List<Boid> boids) {
        Vector center = new Vector(0, 0);  // center of mass
        int count = 0;

        for (Boid other : boids) {
            if (other != this) {
                double distance = this.position.distanceTo(other.position);
                if (distance < PERCEPTION_RADIUS) {
                    center.add(other.position);
                    count++;
                }
            }
        }

        if (count > 0) {
            center.multiply(1.0 / count);  // average position = center of mass

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

        // No neighbors => no steering
        return new Vector(0, 0);
    }

    public Vector separation(List<Boid> boids) {
        Vector steering = new Vector(0, 0);
        int count = 0;

        for (Boid other : boids) {
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


    public void applyBehaviors(List<Boid> boids) {
        Vector sep = separation(boids);
        Vector ali = align(boids);
        Vector coh = cohesion(boids);
        Vector edgeForce = avoidEdges(WIDTH, HEIGHT);  // Use actual dimensions


        ali.multiply(1.4);
        sep.multiply(0.9);
        coh.multiply(1.0);
        edgeForce.multiply(2.0);

        sep.multiply(1.5);
        ali.multiply(1.0);
        coh.multiply(1.0);

        applyForce(edgeForce);
        applyForce(sep);
        applyForce(ali);
        applyForce(coh);
    }
}
