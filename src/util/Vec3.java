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
     * The length of a vector can be found by squaring each component, adding those values together and taking
     *   the square root of that summation.
     *
     * Also known as the magnitude of the vector.
     *
     * @return The length of this vector.
     */
    public float length() {
        return (float) Math.sqrt( (e[0] * e[0]) + (e[1] * e[1]) + (e[2] * e[2]) );
    }

    /**
     * Add two vectors and return a new vector.
     */
    public Vec3 add(Vec3 v) {
        return new Vec3(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }

    /**
     * Subtracts some vector from this one, and returns new vector representing the subtraction.
     * @param v : Some vector being subtracted from this vector.
     * @return Vthis - v
     */
    public Vec3 subt(Vec3 v) {
        return new Vec3(e[0] - v.e[0], e[1] - v.e[1], e[2] - v.e[2]);
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

    /**
     * Divides each component of a vector by some factor.
     * @param f : The factor by which to divide this vector.
     * @return A new vector equal to this vector divided by a scalar factor.
     */
    public Vec3 div(float f) {
        return new Vec3((e[0] / f), (e[1] / f), (e[2] / f));
    }

    /**
     * Take two vectors, multiply each according element (X1 x X2) and sum up the results of each
     *   addition. Ends up looking like this:
     *
     *   (X1 x X2) + (Y1 x Y2) + (Z1 x Z2)
     *
     * @param v : The vector that we'll take the dot product with.
     * @return The dot product of two vectors.
     */
    public float dot(Vec3 v) {
        return (e[0] * v.e[0]) + (e[1] * v.e[1]) + (e[2] * v.e[2]);
    }

    /**
     * @return The dot product of this vector with itself.
     */
    public float dotSelf() {
        return (e[0] * e[0]) + (e[1] * e[1]) + (e[2] * e[2]);
    }

    /**
     * A unit vector can be obtained from by dividing the vector by it's magnitude, or length.
     *
     * This operation is also known as normalization in 2D/3D graphics. Essentially just returns
     *   a vector with the same direction but a length/magnitude of 1.
     *
     * @return The same vector, but with a length/magnitude of 1
     */
    public Vec3 unitVector() {
        return div(length());
    }

    /**
     * @return The summation of each squared vector component
     */
    public float squaredLength() {
        return (e[0] * e[0]) + (e[1] * e[1]) + (e[2] * e[2]);
    }

    @Override // For debugging purposes
    public String toString() {
        return e[0] + "," + e[1] + "," + e[2];
    }
}
