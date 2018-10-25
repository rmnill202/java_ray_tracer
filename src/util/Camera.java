package util;

/**
 * Maintains an origin (the position of the camera) and it's projection.
 *
 * I'm a little curious about the effect that the z-index has on the projection
 *   so I might fiddle around with that to see what changes.
 */
public class Camera {
    private Vec3 origin, // The position of the camera in 3D space
            lowerLeft, height, width; // Used to determine what the camera sees

    public Camera() {
        this(new Vec3(0, 0, 0), -1f, 2f, 4f);
    }

    /**
     *
     * @param position : Origin of the camera in 3D space.
     * @param distance : Distance into the z-index that the projection exists. Essentially impacts FOV, should be negative.
     * @param height : The height of the projection.
     * @param width : The width of the projection.
     */
    public Camera(Vec3 position, float distance, float height, float width) {
        this.origin = position;
        this.height = new Vec3(0, height, 0);
        this.width = new Vec3(width, 0, 0);
        this.lowerLeft = new Vec3(-(width / 2), -(height / 2), distance);
    }

    /**
     * Returns the ray created from the origin of this camera to some normalized points along it's projection plane.
     *
     * @param u : Between 0 and 1, how far horizontally along the plane we're looking
     * @param v : Between 0 and 1, how far vertically along the plane we're looking
     * @return The ray created from the origin to (u,v)
     */
    public Ray getRay(float u, float v) {
        return new Ray(origin, getDirection(u, v));
    }

    /**
     * Transforms normalized coordinates into a direction.
     */
    private Vec3 getDirection(float u, float v) {
        // lower left + u*width + v*height - origin
        return lowerLeft.add(width.mult(u)).add(height.mult(v)).subt(origin);
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public Vec3 getLowerLeft() {
        return lowerLeft;
    }

    public Vec3 getHeight() {
        return height;
    }

    public Vec3 getWidth() {
        return width;
    }
}
