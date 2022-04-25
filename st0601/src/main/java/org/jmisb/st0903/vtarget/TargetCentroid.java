package org.jmisb.st0903.vtarget;

import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;

/**
 * Target Centroid Pixel Number (ST0903 VTarget Pack Tag 1).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Specifies the position of the target centroid within a frame (see below for equations). Numbering
 * commences with 1, at the top left pixel, and proceeds from left to right, top to bottom The
 * calculation of the pixel number uses the equation: Column + ((Row-1) x Frame Width)). The top
 * left pixel of a frame equates to (Column, Row) = (1, 1) and pixel number 1. The Frame Width is
 * the value of VMTI LS Tag 8, if present. If it is not present, then the Frame Width comes from the
 * underlying Motion Imagery. In the absence of underlying Motion Imagery, VMTI LS Tag 8 needs to be
 * present. Range: All integer values from 1 to 0xFFFFFFFFFFFF (281,474,976,710,655).
 *
 * <p>Two representations of the Target Centroid Pixel Number are possible specified using either
 * the Target Centroid Pixel Number (Tag 1), or the pair Target Centroid Pixel Row (Tag 19) and
 * Target Centroid Pixel Column (Tag 20).
 *
 * </blockquote>
 *
 * This item is also used for ST0903 VTrackItem Item 2. The corresponding frame width and frame
 * height are given in VTrackItem Item 20 and Item 21.
 */
public class TargetCentroid extends PixelNumber
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    /**
     * Create from value.
     *
     * @param num the centroid pixel number
     */
    public TargetCentroid(long num) {
        super(num);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetCentroid(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Target Centroid";
    }
}
