package entities;

import util.HitRecord;
import util.Hittable;
import util.Ray;
import util.Vec3;

/**
 * A perfect 3D sphere!
 */
public class Sphere implements Hittable {
    private Vec3 center;
    private float radius;

    public Sphere(float x, float y, float z, float radius) {
        this.center = new Vec3(x, y, z);
        this.radius = radius;
    }

    @Override
    public boolean hitAlongPath(Ray path, float tMin, float tMax, HitRecord record) {

        // Origin - center of the sphere
        Vec3 osubvc = path.origin().subt(center),
                direction = path.direction();

        // Determine the discriminant, which allows us to check if we've intersected
        //   the sphere once, twice or no times depending on its value.
        float a = direction.dotSelf(),
                b = 2 * direction.dot(osubvc),
                c = osubvc.dotSelf() - (radius * radius),
                discriminant = (b * b) - (4 * a * c);

        // If we intersect the sphere at least once, determine the front-most intersection
        if(discriminant >= 0) {
            float minusDisc = ( (-b) - ((float)Math.sqrt(discriminant)) ) / (2.0f * a),
                   plusDisc = ( (-b) + ((float)Math.sqrt(discriminant)) ) / (2.0f * a);

            boolean useMinus = minusDisc < tMax && minusDisc > tMin,
                    usePlus = plusDisc < tMax && plusDisc > tMin;

            // If we've got a valid point along our ray at some point on the sphere
            if(useMinus || usePlus) {

                // Find the t-value/point along the ray that intersects with the sphere
                record.t = useMinus ? minusDisc : plusDisc;

                // Find the point along this sphere that was intersected
                record.point = path.pointAt(record.t);

                // Calculate the surface normal vector. We do this by subtracting the point along the surface
                //   from the center, which gives us a not-normalized vector pointing out from the center. We then
                //   divide by its radius to ensure that the value stays somewhere between -1 and 1.
                record.normal = record.point.subt(center).div(radius);

                return true;
            }

            // Doesn't intersect
            return false;
        }
        // The ray doesn't intersect this sphere
        else {
            return false;
        }
    }
}
