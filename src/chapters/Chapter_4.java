package chapters;

import util.PPMPrinter;
import util.Ray;
import util.Vec3;

/**
 * For chapter 4, we'll be adding a 3D sphere to our world. Spheres are defined by the following equation:
 *
 *      (x - cx)^2   +   (y - cy)^2   +   (z - cz)^2   =   R^2
 *
 * Center of the sphere         cx, cy, cz   =   Vc
 * Any point along the sphere   x, y, z      =   Vp
 * Sphere radius                R
 *
 * The equation works well with vector math. We can simply take the dot product of (Vp - Vc) to find R^2.
 *
 *      (Vp - Vc) dot (Vp - Vc) = R^2
 *
 * Assuming we're working with our {@linkplain util.Ray} class, we want to find out whether some point along our ray
 *   (going from the camera to some part of our world) intersects with the sphere. We can check individual points along
 *   the ray using our pointAt(t) function. So we simply plug our point into the equation like so...
 *
 *      (Vt - Vc) dot (Vt - Vc) = R^2
 *          (Note: Where Vt = ray.pointAt(t))
 *
 * This might be a little expensive to compute over and over again. Remember that ray.pointAt(t) breaks down into this:
 *
 *      ray.pointAt(t) = origin + (direction * t)
 *
 * Let origin = O, direction = D and let's keep using t. If we plug back into our dot equation...
 *
 *      (O + D*t - Vc) dot (O + D*t - Vc) = R^2
 *
 * Using the law of distribution, the equation spans out to become...
 *
 *      OoO + D*toD*t + VcoVc + 2OoD*t - 2OoVc - 2D*toVc
 *          - I gave up on writing dot between everything :(
 *
 * More simply it looks like this: (O = A, D*t = B, Vc = C)
 *
 *      AoA + BoB + CoC + 2AoB - 2AoC - 2BoC
 *
 * Let's try and pull out as many constants as we can from this.
 *
 *      AoA + BoB + CoC + 2(AoB - AoC - BoC)
 *      AoA + BoB + CoC + 2(AoB - BoC - AoC)        // Swapped the last two
 *      AoA + BoB + CoC + 2( Bo(A - C) - AoC)       // Distributive
 *      AoA + BoB + CoC + 2Bo(A - C) - 2AoC         // Distributive
 *      BoB + 2Bo(A - C) + (AoA + CoC - 2AoC)       // Shifting things around again
 *          - Assuming that last bit actually looked like A^2 + C^2 - 2AC, we could get (A-B)^2
 *      BoB + 2Bo(A - C) + (A-C)o(A-C)
 *
 * Now we just plug everything back in!
 *      D*toD*t + 2D*to(O - Vc) + (O-Vc)o(O-Vc)
 *      t*t*(DoD) + 2*t*(Do(O - Vc)) + (O-Vc)o(O-Vc) // Finally, distribute the scalar t
 *
 *
 * Let's add R^2 back in, and shift it to the left side to our our final equation:
 *      t*t*(DoD) + 2*t*(Do(O - Vc)) + (O-Vc)o(O-Vc) - R^2 = 0
 *
 *
 * The dot products and R are all constant, which is pretty great! Now how do we figure out if our ray has intersected
 *   our sphere?
 *
 * Given a sphere and a ray, you can have either 0, 1 or 2 intersections. Additionally, our equation seems to fit
 *   the form of a quadratic equation (ax^2 + bx + c). For a quadratic equation, if the discriminant is positive, we
 *   have two real answers (ie: two intersection points). If the discriminant is zero, it only intersects once. If
 *   the discriminant is negative, then we don't intersect the sphere at all.
 *
 * Where the discriminant is b^2 - 4ac
 *      Let a = DoD
 *      Let b = 2*Do(O - Vc)
 *      Let c = (O-Vc)o(O-Vc)
 */
public class Chapter_4 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255),
            V_RED = new Vec3(255, 0, 0);

    // Data members
    private Vec3 lowerLeft, horizontal, vertical, origin, sphereCenter;

    public Chapter_4() {
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
        if(hittingSphere(ray, this.sphereCenter, 0.5f)) {
            color = V_RED;
        } else {
            color = gradient(ray, v, V_BLUE, V_WHITE);
        }

        // Format the color values properly
        int ri = (int)(color.r()), gi = (int)(color.g()), bi = (int)(color.b());

        // Print out the pixel!
        System.out.println(ri + " " + gi + " " + bi);
    }

    /**
     * Determines if a given ray travels through a sphere.
     *
     * @param ray : The ray we're checking
     * @param sphereCenter : The center of the sphere
     * @param sphereRadius : The radius of the sphere
     * @return True if we've hit the sphere at least once, false if otherwise
     */
    public boolean hittingSphere(Ray ray, Vec3 sphereCenter, float sphereRadius) {
        /** Where the discriminant is b^2 - 4ac
         ** And where origin = O, direction = D, Vc is the center of the sphere
         *      Let a = DoD
         *      Let b = 2*Do(O - Vc)
         *      Let c = (O-Vc)o(O-Vc)
         */
        Vec3 osubvc = origin.subt(sphereCenter), direction = ray.direction();

        float a = direction.dotSelf(),
                b = 2 * direction.dot(osubvc),
                c = osubvc.dotSelf() - (sphereRadius * sphereRadius),
                discriminant = (b * b) - (4 * a * c);

        return discriminant > 0;
    }
}
