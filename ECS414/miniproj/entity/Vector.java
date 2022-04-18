package entity;

public class Vector {
    public float x;
    public float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /**
     * Computes the distance from this vector to the target vector
     * @param v Target vector
     * @return Displacement as a Vector
     */
    public Vector displacement(Vector v) {
        return new Vector(v.x - x, v.y - y);
    }

    /**
     * a<sup>2</sup> + b<sup>2</sup>
     * @return Pythagorean theorem w/o sqrt
     */
    public double pythSqr() {
        return x*x + y*y;
    }

    /**
     * Computes the angle of this vector
     * @return Angle in radian, counterclockwise.
     */
    public double angle() {
        return Math.atan2(y, x);
    }

    /**
     * Computes the angle from this vector to the target vector
     * @param v Target vector
     * @return Angle in radian, counterclockwise.
     */
    public double angle(Vector v) {
        Vector delta = displacement(v);
        return Math.atan2(delta.y, delta.x);
    }

    /**
     * Adds the target vector to this vector
     * @param v Target vector
     */
    public void add(Vector v) {
        x += v.x;
        y += v.y;
    }
}
