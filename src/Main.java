/**
 * Runs the ray tracing program and returns a ppm formatted file as a result.
 */
public class Main {
    public static void main(String[] args) {

        /* Create a 200 by 100 gradient image, using the simple PPM file format.
         *
         * Image Parameters :
         * Red    (0 -> 1.0)   (Left -> Right)
         * Green  (0 -> 1.0)   (Bottom -> Top)
         * Blue   (0.2)        (Constant)
         */
        int imageX = 200, imageY = 100;

        // Floats for representing each color, which run from 0 to 255
        float r, g, b = 0.2f;

        // Integers representing the end result of each color after computation
        int ri, gi, bi = 51;

        // File header. P3 is used to denote the use of ASCII for color definitions, and
        //   PPM files should be headed by the number of columns and rows
        System.out.println(String.format("P3\n%d %d\n255\n", imageX, imageY));

        // Write out pixels in rows from TOP to BOTTOM
        for(int y = imageY - 1; y >= 0; y--) {
            // A given row runs from LEFT to RIGHT
            for(int x = 0; x < imageX; x++) {
                // Calculate the RGB value for each pixel, given the image parameters and the current position
                r = ((float) x) / ((float) imageX); // Calculate some value between 0.0 and 1.0
                g = ((float) y) / ((float) imageY);

                // Apply them to get a integer values between 0 and 255
                ri = (int)(r * 255.99f);
                gi = (int)(g * 255.99f);

                // Print out the pixel with some value
                System.out.println(ri + " " + gi + " " + bi);
            }
        }
    }
}
