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
            ////// Fill out the record object with new data.
            // Find the t-value/point along the ray that intersects with the sphere
            record.t = ( (-b) - ((float)Math.sqrt(discriminant)) ) / (2.0f * a);

            // Find the point along this sphere that was intersected
            record.point = path.pointAt(record.t);

            // Calculate the surface normal
            record.normal = record.point.subt(center).div(radius);

            return true;
        }
        // The ray doesn't intersect this sphere
        else {
            return false;
        }
    }
}
