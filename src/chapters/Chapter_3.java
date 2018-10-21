package chapters;

import util.PPMPrinter;
import util.Ray;
import util.Vec3;

/**
 * Sets up the ground work for using rays to determine the color for a given pixel. Depending on what a ray intersects
 *   when traveling between the camera/eye and a particular pixel, we'll print out a particular color for that point.
 */
public class Chapter_3 extends PPMPrinter {

    // Our background will be a blue-to-white gradient!
    private final static Vec3 V_BLUE = new Vec3(146, 219, 232), V_WHITE = new Vec3(255,255,255);

    // Data members
    private Vec3 lowerLeft, horizontal, vertical, origin;

    /**
     * Initialize some variables that are needed to run through the chapter 3 activity.
     */
    public Chapter_3() {
        /////// In place of a formal camera/projection matrix, let's use some basic values

        // First we have the lower left corner of our projection. Assume that going "into" the screen our z value
        //   decreases, so we use -1 for the z value of our projection.
        lowerLeft = new Vec3(-2.0f, -1.0f, -1.0f);

        // Then, we determine how large our projection is horizontally and vertically. These are simply the SIZES of
        //   the projection, which explains why the z value isn't also set to -1.
        horizontal = new Vec3(4.0f, 0.0f, 0.0f);
        vertical = new Vec3(0.0f, 2.0f, 0.0f);

        // We also assume we have an origin at point 0,0,0
        origin = new Vec3(0.0f, 0.0f, 0.0f);
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

        // For now, we're just coloring the background using a gradient
        Vec3 color = gradient(ray, v, V_BLUE, V_WHITE);

        // Format the color values properly
        int ri = (int)(color.r()), gi = (int)(color.g()), bi = (int)(color.b());

        // Print out the pixel!
        System.out.println(ri + " " + gi + " " + bi);
    }


}
