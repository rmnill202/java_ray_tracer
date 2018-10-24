package util;

/**
 * A mutable value object for storing the results of the hit function in {@linkplain Hittable}.
 */
public class HitRecord {
    /**
     * The point along a particular ray that we've hit some sort of object in our scene.
     */
    public float t;

    /**
     * The point that we've hit on the object.
     */
    public Vec3 point;

    /**
     * The surface normal calculated for this hit.
     * // TO DO - Explain how that calculation is done
     */
    public Vec3 normal;
}
