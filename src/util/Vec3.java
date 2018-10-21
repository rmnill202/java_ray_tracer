package util;

/**
 * A vector with three elements. Since we aren't performing normalization just yet, nor are we using transparent colors,
 *   we won't need a homogeneous coordinate.
 *
 * Vec3 instances are used for geometric vectors and colors.
 */
public class Vec3 {
    // Underlying data
    private float[] e;

    public Vec3(float e1, float e2, float e3) {
        e = new float[]{e1, e2, e3};
    }

    public Vec3() {
        this(0, 0, 0);
    }

    // Accessors for color and geometric use
    public float x() {return e[0];} public float y() {return e[1];} public float z() {return e[2];}
    public float r() {return e[0];} public float g() {return e[1];} public float b() {return e[2];}

    // Immutable vector operations
    /**
     * Add two vectors and return a new vector.
     */
    public Vec3 add(Vec3 v) {
        return new Vec3(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }

    /**
     * Multiply two vectors and return a new vector.
     */
    public Vec3 mult(Vec3 v) {
        return new Vec3(e[0] * v.e[0], e[1] * v.e[1], e[2] * v.e[2]);
    }

    /**
     * Multiply this vector's elements by some factor and return a new vector.
     */
    public Vec3 mult(float f) {
        return new Vec3(e[0] * f, e[1] * f, e[2] * f);
    }
}
