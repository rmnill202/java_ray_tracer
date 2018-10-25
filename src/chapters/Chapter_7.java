package chapters;

import util.PPMPrinter;

/**
 * Chapter 7 focuses on building upon the work with anti-aliasing to create basic
 *   shading with diffuse (or matte) materials. In the last chapter, we took some number
 *   of samples around a single path and averaged the colors out. We can use the basis of
 *   this operation (random paths) to simulate light particles bouncing from a light
 *   source to points of our object and then back at our camera.
 *
 *
 */
public class Chapter_7 extends PPMPrinter{

    public Chapter_7() {

    }

    @Override
    public int imageWidth() {
        return 0;
    }

    @Override
    public int imageHeight() {
        return 0;
    }

    @Override
    public void calculatePixelRGB(float x, float y) {

    }
}
