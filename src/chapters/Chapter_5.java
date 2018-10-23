package chapters;

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
    private Vec3 lowerLeft, horizontal, vertical, origin, sphereCenter;

    public Chapter_5() {
        /////// Explanation can be found in the Chapter_3 constructor

        lowerLeft = new Vec3(-2.0f, -1.0f, -1.0f);
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);
        origin = new Vec3(0.0f, 0.0f, 0.0f);
        sphereCenter = new Vec3(0, 0, -1); // Located in the center of our screen, and on the projection plane
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
        float t = hitSphere(ray, this.sphereCenter, 0.5f); // The factor along the ray that hits the sphere

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

    /**
     * Initial method of normal calculation.
     *
     * Takes a ray, determines if it can hit a given sphere, and returns
     *   the factor along that ray that intersects the sphere at some point.
     *
     * @param ray : The ray we're checking
     * @param sphereCenter : The center of the sphere
     * @param sphereRadius : The radius of the sphere
     *
     * @return The factor along the ray (often called t) that the sphere
     *   intersects with the ray, or -1 if the ray doesn't intersect the sphere.
     */
    public float hitSphere(Ray ray, Vec3 sphereCenter, float sphereRadius) {
        /** Where the discriminant is b^2 - 4ac
         ** And where origin = O, direction = D, Vc is the center of the sphere
         *      Let a = DoD
         *      Let b = 2*Do(O - Vc)
         *      Let c = (O-Vc)o(O-Vc)
         */
        Vec3 osubvc = origin.subt(sphereCenter), direction = ray.direction();

        // Determine the discriminant, which allows us to determine if we've intersected
        //   the sphere once, twice or no times depending on its value.
        float a = direction.dotSelf(),
                b = 2 * direction.dot(osubvc),
                c = osubvc.dotSelf() - (sphereRadius * sphereRadius),
                discriminant = (b * b) - (4 * a * c);

        // If we intersect the sphere at least once, determine the front-most intersection
        if(discriminant >= 0) {
            return ( (-b) - ((float)Math.sqrt(discriminant)) ) / (2.0f * a);
        }
        // The ray doesn't intersect the given sphere
        else {
            return -1;
        }
    }
}
