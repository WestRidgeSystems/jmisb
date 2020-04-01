package org.jmisb.api.klv.st0903.vtarget;

/**
 * Boundary Bottom Right Pixel Number (ST0903 VTarget Pack Tag 3).
 * <p>
 * From ST0903:
 * <blockquote>
 * Specifies the position of the bottom right corner of the target bounding box
 * within the frame as a pixel number. Numbering commences with 1, at the top
 * left pixel, and proceeds from left to right, top to bottom. The calculation
 * of the pixel number uses the equation: Column + ((Row-1) x Frame Width)). The
 * top left pixel of the frame equates to (Column, Row) = (1, 1) and pixel
 * number 1. The Frame Width comes from VMTI LS Tag 8, if present. If it is not
 * present, then the Frame Width comes from the underlying Motion Imagery. In
 * the absence of underlying Motion Imagery, VMTI LS Tag 8 needs to be present.
 * <p>
 * It is important for bit efficiency to rely on variable length payloads for
 * this value.
 * </blockquote>
 */
public class BoundaryBottomRight extends PixelNumber
{
    /**
     * Create from value.
     *
     * @param num the bounding box bottom right pixel number
     */
    public BoundaryBottomRight(long num)
    {
        super(num);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public BoundaryBottomRight(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public final String getDisplayName()
    {
        return "Boundary Bottom Right";
    }
}
