package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Centroid Pixel Column (ST0903 VTarget Tag 20).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Specifies the column of the target centroid within the Motion Imagery frame in pixels. Numbering
 * commences from 1, denoting the left column. May be used with Target Centroid Pixel Row (Tag 19)
 * to provide an alternate method to specify Target Pixel Centroid Number (Tag 1), the pixel
 * location of the target centroid. If present, Target Centroid Pixel Row (Tag 19) must also be
 * present.
 *
 * <p>Valid Values: Integer values in the range 1 to 2^32-1.
 *
 * </blockquote>
 */
public class CentroidPixelColumn extends AbstractPixelIndex implements IVmtiMetadataValue {
    /**
     * Create from value.
     *
     * @param column the pixel column (min 1, max 2^32-1)
     */
    public CentroidPixelColumn(long column) {
        super(column);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public CentroidPixelColumn(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Centroid Pixel Column";
    }
}
