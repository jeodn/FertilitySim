public class Vector {
    public double x, y;

    public Vector(double x, double y) { this.x = x; this.y = y; }

    public void add(Vector v) { this.x += v.x; this.y += v.y; }
    public void subtract(Vector v) { this.x -= v.x; this.y -= v.y; }
    public void multiply(double n) { this.x *= n; this.y *= n; }
    public double magnitude() { return Math.sqrt(x*x + y*y); }
    public void normalize() { double m = magnitude(); if (m != 0) multiply(1/m); }

    public static Vector random2D() {
        double angle = Math.random() * 2 * Math.PI;
        return new Vector(Math.cos(angle), Math.sin(angle));
    }

    public Vector copy() { return new Vector(x, y); }

    public double distanceTo(Vector other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
