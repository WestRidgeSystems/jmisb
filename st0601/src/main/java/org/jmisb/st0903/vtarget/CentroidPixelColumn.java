package org.jmisb.st0903.vtarget;

import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;

/**
 * Centroid Pixel Column (ST0903 VTarget Item 20 and VTrackItem Pack Item 4).
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
 *
 * For VTrackItem Pack, the centroid pixel row is Item 3 and the centroid pixel number is Item 2.
 */
public class CentroidPixelColumn extends AbstractPixelIndex
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
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
