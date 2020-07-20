package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Heading-style Angle.
 *
 * <p>Used by ST 0601 Item 5 (Platform Heading Angle), Item 35 (Wind Direction), Item 64 (Platform
 * Magnetic Heading) and Item 71 (Alternate Platform Heading).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Map 0..(2^16-1) to 0..360
 *
 * <p>Resolution: ~5.5 milli degrees
 *
 * </blockquote>
 */
public abstract class UasDatalinkAngle360 implements IUasDatalinkValue {
    protected static final double RANGE = 360.0;
    protected static final double MAXINT = 65535.0; // 2^16 - 1
    protected double degrees;

    /**
     * Create from value
     *
     * @param degrees angle, in degrees
     */
    public UasDatalinkAngle360(double degrees) {
        if (degrees < 0 || degrees > 360) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,360]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Encoded byte array
     */
    public UasDatalinkAngle360(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    this.getDisplayName()
                            + " Platform Heading Angle encoding is a 2-byte unsigned int");
        }

        int intVal = PrimitiveConverter.toUint16(bytes);
        this.degrees = (intVal / MAXINT) * RANGE;
    }

    /**
     * Get the value in degrees
     *
     * @return angle, in degrees
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        int intVal = (int) Math.round((degrees / RANGE) * MAXINT);
        return PrimitiveConverter.uint16ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }
}
