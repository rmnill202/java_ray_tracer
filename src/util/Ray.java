package util;

/**
 * A ray is a line with an origin point that heads in a particular direction. You can move along this line with
 *   an additional parameter.
 *
 * We essentially have two points in 3D space, and apply a positive or negative factor to travel in a straight line
 *   that extends between (and beyond) said two points.
 */
public class Ray {
    // Use vectors to represent the origin and the direction it's traveling in
    Vec3 origin, direction;

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * @param t : The ray parameter, or how far along the ray you travel.
     * @return A new vector representing some point in 3D space that falls along the line represented by this ray.
     */
    public Vec3 pointAt(float t) {
        return (origin.add(direction.mult(t)));
    }

    @Override // For debugging purposes
    public String toString() {
        return origin.toString() + " -> " + direction.toString();
    }
}
