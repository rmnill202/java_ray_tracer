package chapters;

import entities.Sphere;
import util.PPMPrinter;
import util.Ray;
import util.Vec3;

/**
 *
 */
public class Chapter_5 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255),
            V_RED = new Vec3(255, 0, 0);

    // Data members
    private Vec3 lowerLeft, horizontal, vertical, origin;
    private Sphere sphere;

    public Chapter_5() {
        // Set up some variables for representing our camera and projection plane
        lowerLeft = new Vec3(-2.0f, -1.0f, -1.0f);
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);
        origin = new Vec3(0.0f, 0.0f, 0.0f);

        // Set up a sphere
        sphere = new Sphere(0, 0, -1, 0.5f);
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
        //float t = sphere.hitAlongPath(ray, 0, 5f, )(ray, this.sphereCenter, 0.5f); // The factor along the ray that hits the sphere

        // We check > 0, since the sphere should be in front of our camera
        if(t > 0) { // If we've hit the sphere, represent the surface normal with a unique color

            // Still trying to fully understand this calculation
            Vec3 normal = ray.pointAt(t).subt(sphereCenter).unitVector();
            color = new Vec3(normal.x() + 1, normal.y() + 1, normal.z() + 1).mult(0.5f).mult(254.99f);
        } else { // If we haven't hit the surface, just use the gradient background
            color = gradient(ray, v, V_BLUE, V_WHITE);
        }

        // Format the color values properly
        int ri = (int)(color.r()), gi = (int)(color.g()), bi = (int)(color.b());

        // Print out the pixel!
        System.out.println(ri + " " + gi + " " + bi);
    }
}
