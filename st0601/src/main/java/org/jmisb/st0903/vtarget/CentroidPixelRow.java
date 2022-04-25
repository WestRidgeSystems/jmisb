package org.jmisb.st0903.vtarget;

import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;

/**
 * Centroid Pixel Row (ST0903 VTarget Item 19 and VTrackItem Pack Item 3).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Specifies the row of the target centroid within the Motion Imagery frame in pixels. Numbering
 * commences from 1, denoting the top row. May be used with Target Centroid Pixel Column (Tag 20) to
 * provide an alternate method to specify Target Pixel Centroid Number (Tag 1), the pixel location
 * of the target centroid. If present, Target Centroid Pixel Column (Tag 20) must also be present.
 *
 * <p>Valid Values: Integer values in the range 1 to 2^32-1.
 *
 * </blockquote>
 *
 * For VTrackItem Pack, the centroid pixel column is Item 4, and the centroid pixel number is Item
 * 2.
 */
public class CentroidPixelRow extends AbstractPixelIndex
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    /**
     * Create from value.
     *
     * @param row the pixel row (min 1, max 2^32-1)
     */
    public CentroidPixelRow(long row) {
        super(row);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public CentroidPixelRow(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Centroid Pixel Row";
    }
}
