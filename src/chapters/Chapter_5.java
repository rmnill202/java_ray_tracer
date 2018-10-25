package chapters;

import entities.HittableList;
import entities.Sphere;
import util.HitRecord;
import util.PPMPrinter;
import util.Ray;
import util.Vec3;

/**
 * The focus of this chapter is primarily on surface normals. An abstract {@linkplain util.Hittable} class is suggested
 *   as well.
 *
 * A surface normal is a vector that is perpendicular to a surface at some point along that surface. Assuming that our
 *   sphere is perfectly round, we can find the surface normal of a given point P by tracing from the center of the
 *   sphere to that point and then extending that ray further.
 *
 */
public class Chapter_5 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255);

    // Data members
    private Vec3 lowerLeft, horizontal, vertical, origin;
    private HittableList entities;
    private HitRecord record;

    public Chapter_5() {
        // Set up some variables for representing our camera and projection plane
        lowerLeft = new Vec3(-2.0f, -1.0f, -1.0f);
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);
        origin = new Vec3(0.0f, 0.0f, 0.0f);

        // Set up a sphere
        entities = new HittableList(new Sphere(0, 0, -1, 0.5f),
                                                new Sphere(0, -100.5f, -1, 100));
        //entities = new HittableList(new Sphere(0, -100.5f, -1, 90));
        //entities = new HittableList(new Sphere(0, 0, -1, 0.5f));
        record = new HitRecord();
    }

    @Override
    public int imageWidth() {
        return 200;
    }

    @Override
    public int imageHeight() {
        return 100;
    }

    @Override
    public void calculatePixelRGB(float x, float y) {
        // If our projection were a 2D plane, use u and v to represent normalized points along that plane
        float u = x / ((float) imageWidth()),
                v = y / ((float) imageHeight());

        // Create a ray from our camera to the current pixel
        Ray ray = new Ray( origin, lowerLeft.add(horizontal.mult(u)).add(vertical.mult(v)) );

        // Check if we're hitting a sphere
        Vec3 color;

        // We check > 0, since the sphere should be in front of our camera
        if(entities.hitAlongPath(ray, 0, Float.MAX_VALUE, record)) {
            // If we've hit the sphere, represent the surface normal with a unique color based on the vector
            Vec3 normal = record.normal;
            color = new Vec3(normal.x() + 1, normal.y() + 1, normal.z() + 1).mult(0.5f).mult(254.99f);

        } else {
            // If we haven't hit the surface, just use the gradient background
            color = gradient(ray, v, V_BLUE, V_WHITE);
        }

        // Format the color values properly
        int ri = (int)(color.r()), gi = (int)(color.g()), bi = (int)(color.b());

        // Print out the pixel!
        System.out.println(ri + " " + gi + " " + bi);
    }
}
