package org.jmisb.api.klv.eg0104;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Width value for EG 0104.
 *
 * <p>This was Target Width in the NIMA-MIPO memo, but was in feet instead of metres.
 */
public class TargetWidth implements IPredatorMetadataValue {

    private final float width;

    public TargetWidth(byte[] bytes) {
        width = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f m", width);
    }

    @Override
    public String getDisplayName() {
        return "Target Width";
    }

    /**
     * Get the target width distance.
     *
     * @return distance in meters.
     */
    public float getDistance() {
        return width;
    }
}
