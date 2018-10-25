package chapters;

import entities.HittableList;
import entities.Sphere;
import util.*;

/**
 * The focus for this chapter is on anti-aliasing. A {@linkplain util.Camera} has been implemented to make scene setup a
 *   little simpler to do.
 *
 * I'd like to learn a little bit more about this form of anti-aliasing. It seems like an interesting approach. I'd
 *   also like to see if I can implement a different form of anti-aliasing later on!
 */
public class Chapter_6 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255);

    // Determines how many samples we use for AA
    private final static int SAMPLES = 100;

    // Data members
    private Camera cam;
    private HittableList entities;
    private HitRecord record;

    public Chapter_6() {
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
        return 200;
    }

    @Override
    public int imageHeight() {
        return 100;
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
        int ri = (int)(color.r()), gi = (int)(color.g()), bi = (int)(color.b());

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
            Vec3 normal = record.normal;

            // Convert from a range of (-1, 1) to (0, 2) to (0, 1)
            return new Vec3(normal.x() + 1, normal.y() + 1, normal.z() + 1).mult(0.5f).mult(255.9f);

        } else {
            // If we haven't hit the surface, just use the gradient background
            return gradient(ray, v, V_BLUE, V_WHITE);
        }
    }
}
