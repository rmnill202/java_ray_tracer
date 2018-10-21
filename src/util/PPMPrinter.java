package util;

/**
 * A utility class for printing PPM images. Assumes that we're working with ASCII values. The particular image height,
 *   width and drawing method is entirely up to the implementation of this class. Doesn't restrict subclasses to a
 *   particular form of output, assumes that's taken care of with the calculation method.
 *
 * Also maintains some useful utility functions
 */
public abstract class PPMPrinter {

    // Class definition

    public abstract int imageWidth();
    public abstract int imageHeight();

    /**
     * Assuming that we're in PPM format, calculate the color at a particular point of the image.
     * @param x : The x-coordinate of the pixel, going from left to right
     * @param y : The y-coordinate of the pixel, going from top to bottom
     */
    public abstract void calculatePixelRGB(float x, float y);

    public void print() {
        int imageX = this.imageWidth(), imageY = this.imageHeight();

        // File header. P3 is used to denote the use of ASCII for color definitions, and
        //   PPM files should be headed by the number of columns and rows
        System.out.println(String.format("P3\n%d %d\n255\n", imageX, imageY));

        // Write out pixels in rows from TOP to BOTTOM
        for(int y = imageY - 1; y >= 0; y--) {
            // A given row runs from LEFT to RIGHT
            for(int x = 0; x < imageX; x++) {
                this.calculatePixelRGB(x, y);
            }
        }
    }

    // Utility functions
    /**
     * Given two colors, and a lerp value between 0 and 1, return a color that matches a gradient between those
     *   colors at the given lerp value.
     *
     * @param lerp : Some value between 0 and 1, where 0 returns start and 1 returns end
     * @param start : The start of the gradient
     * @param end : The end of the gradient
     *
     * @return The color of a gradient between the start/end colors at some point along that gradient.
     */
    protected Vec3 gradient(Ray r, float lerp, Vec3 start, Vec3 end) {
        Vec3 a = end.mult(1f - lerp);
        Vec3 b = start.mult(lerp);
        return a.add(b);
    }
}
