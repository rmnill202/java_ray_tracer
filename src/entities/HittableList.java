package entities;

import util.HitRecord;
import util.Hittable;
import util.Ray;

/**
 * Represents a list of {@linkplain Hittable} objects, and determines which object is closest to the viewer.
 */
public class HittableList implements Hittable {
    private Hittable[] hittables;
    
    public HittableList(Hittable... hittables) {
        this.hittables = hittables;
    }

    @Override
    public boolean hitAlongPath(Ray path, float tMin, float tMax, HitRecord record) {
        // Determine the closest entity to origin of the ray
        float closest = tMax;
        boolean hasHit = false;
        HitRecord rec = new HitRecord();

        for(Hittable item : hittables) {
            if(item.hitAlongPath(path, tMin, closest, rec)) {
                hasHit = true;
                closest = rec.t;
                record.normal = rec.normal;
                record.point = rec.point;
                record.t = rec.t;
            }
        }

        return hasHit;
    }
}
