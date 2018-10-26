package chapters;

import entities.HittableList;
import entities.Sphere;
import util.*;

/**
 * Chapter 7 focuses on building upon the work with anti-aliasing to create basic
 *   shading with diffuse (or matte) materials. In the last chapter, we took some number
 *   of samples around a single path and averaged the colors out. We can use the basis of
 *   this operation (random paths) to simulate light particles bouncing from a light
 *   source to points of our object and then back at our camera.
 *
 *
 */
public class Chapter_7 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255);

    // Determines how many samples we use for AA
    private final static int SAMPLES = 100;

    // Data members
    private Camera cam;
    private HittableList entities;
    private HitRecord record;

    public Chapter_7() {
        // Use the default camera positioning/size
        cam = new Camera();

        // Add some spheres to our scene
        entities = new HittableList(    new Sphere(0, 0, -1, 0.5f),
                new Sphere(0, -100.5f, -1, 100)     );

        // Temp object for getting multiple return variables
        record = new HitRecord();
    }

    @Override
    public int imageWidth() {
        return 400;
    }

    @Override
    public int imageHeight() {
        return 200;
    }

    @Override
    public void calculatePixelRGB(float x, float y) {
        // Take samples of the path hitting areas around this pixel, average the colors together for basic anti-aliasing
        Vec3 color = new Vec3();

        // We want to run our color-calculation some number of times at random points around the pixel
        for(int sample = 0; sample < SAMPLES; sample++) {
            // Get normalized points, offset by our samples
            float u = ((float)Math.random() + x) / ((float) imageWidth()),
                    v = ((float)Math.random() + y) / ((float) imageHeight());

            Ray ray = cam.getRay(u, v);
            color = color.add(color(ray, v));
        }

        // Get the average color
        color = color.div(SAMPLES);

        // Convert to (0,255) RGB values
        int ri = (int)(color.r() * 255.9f), gi = (int)(color.g() * 255.9f), bi = (int)(color.b() * 255.9f);

        // Print out the pixel!
        System.out.println(ri + " " + gi + " " + bi);
    }

    /**
     * Return the color of a pixel created by some path through our entities.
     * @param ray : The path through our entities.
     * @param v : The vertical position along the screen.
     * @return The color at a given pixel position.
     */
    private Vec3 color(Ray ray, float v) {
        // Check if we've hit any entities
        if(entities.hitAlongPath(ray, 0, Float.MAX_VALUE, record)) {
            // Represent the surface normal with a unique color based on the normal vector
            //Vec3 normal = record.normal;

            // Convert from a range of (-1, 1) to (0, 2) to (0, 1)
            //return new Vec3(normal.x() + 1, normal.y() + 1, normal.z() + 1).mult(0.5f);//.mult(255.9f);

            Vec3 target = record.point.add(record.normal).add(randomInUnitSphere());
            return color( new Ray(record.point, (target.subt(record.point))), v);

        } else {
            // If we haven't hit the surface, just use the gradient background
            //return gradient(ray, v, V_BLUE, V_WHITE);
            Vec3 unitDirection = ray.direction().unitVector();
            float t = (unitDirection.y() + 1) * 0.5f;
            return new Vec3(1,1,1).add((new Vec3(0.5f, 0.7f, 1.0f).mult(t))).mult(1.0f - t);
        }
    }

    Vec3 randomInUnitSphere() {
        Vec3 p;
        do {
            p = new Vec3((float)Math.random(), (float)Math.random(), (float)Math.random()).mult(2).subt(new Vec3(1, 1, 1));
        } while(p.squaredLength() >= 1.0);
        return p;
    }
}
