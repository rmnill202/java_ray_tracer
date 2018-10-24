package util;

/**
 * Represents some object in our world that can be hit by a ray. Works with a {@linkplain HitRecord}
 *   to return the point that the ray hits on this object, the factor along the path that the ray
 *   intersects with that point, and the surface normal at that point.
 *
 * Currently uses a simplified shading model; doesn't account for any lighting. I think..
 */
public interface Hittable {

    /**
     * Given a ray, determine whether or not that ray manages to hit this object.
     *
     * @param path : The path that we're checking for intersection with this object.
     * @param tMin : The minimum t-value along the path that we'll allow to count as a hit.
     * @param tMax : The maximum t-value along the path that we'll allow to count as a hit.
     * @param record : A re-usable object that additional return data is placed into. Namely the
     *               t-value along the path that intersects with the object. That intersection point
     *               and the calculated surface normal at that point are also provided.
     * @return True if the object is hit by the path, false if otherwise.
     */
    public boolean hitAlongPath(Ray path, float tMin, float tMax, HitRecord record);
}
