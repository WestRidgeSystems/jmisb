package org.jmisb.cumulo;

public class Camera {

    /**
     * Horizontal field of view, in radians
     */
    private final double fov;

    /**
     * Vertical field of view, in radians
     */
    private final double vfov;

    /**
     * Image width, in pixels
     */
    private final int width;

    /**
     * Image height, in pixels
     */
    private final int height;

    /**
     * Constructor
     *
     * @param fov    Horizontal field of view, in radians
     * @param vfov   Vertical field of view, in radians
     * @param width  Image width, in pixels
     * @param height Image height, in pixels
     */
    public Camera(double fov, double vfov, int width, int height) {
        this.fov = fov;
        this.vfov = vfov;
        this.width = width;
        this.height = height;
        validate();
    }

    /**
     * Check that parameters are valid and internally-consistent
     */
    private void validate() {
        if (fov < 0 || fov > Math.PI)
            throw new IllegalArgumentException("Invalid FOV " + fov + " in camera parameters");
        if (vfov < 0 || vfov > Math.PI)
            throw new IllegalArgumentException("Invalid VFOV " + vfov + " in camera parameters");
        if (width < 0)
            throw new IllegalArgumentException("Invalid width " + width + " in camera parameters");
        if (height < 0)
            throw new IllegalArgumentException("Invalid height " + height + " in camera parameters");
    }
}
