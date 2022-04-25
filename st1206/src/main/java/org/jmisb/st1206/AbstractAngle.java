package org.jmisb.st1206;

/** Shared ST1206 implementation of an angle value. */
public abstract class AbstractAngle implements ISARMIMetadataValue {

    /** Number of bytes used for encoding. */
    protected static final int NUM_BYTES = 2;

    /** The angular value. */
    protected double value;

    @Override
    public final String getDisplayableValue() {
        return String.format("%.1f\u00B0", value);
    }

    /**
     * Get the angle value.
     *
     * @return angle in degrees.
     */
    public final double getAngle() {
        return value;
    }
}
