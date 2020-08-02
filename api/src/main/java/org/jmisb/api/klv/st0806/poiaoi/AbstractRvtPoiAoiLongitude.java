package org.jmisb.api.klv.st0806.poiaoi;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Shared implementation of Longitude (PoiLongitude, CornerLongitudePoint1, CornerLongitudePoint3).
 */
public abstract class AbstractRvtPoiAoiLongitude implements IRvtPoiAoiMetadataValue {
    private static final byte[] INVALID_BYTES =
            new byte[] {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static final double FLOAT_RANGE = 180.0;
    private static final double MAX_INT = 2147483647.0;
    private double degrees;

    /**
     * Create from value.
     *
     * @param degrees Longitude, in degrees [-180,180], or {@code Double.POSITIVE_INFINITY} to
     *     represent an error condition
     */
    public AbstractRvtPoiAoiLongitude(double degrees) {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -180 || degrees > 180)) {
            throw new IllegalArgumentException(getDisplayName() + " must be in range [-180,180]");
        }
        this.degrees = degrees;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Longitude, encoded as a 4-byte integer
     */
    public AbstractRvtPoiAoiLongitude(byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is a 4-byte int");
        }

        if (Arrays.equals(bytes, INVALID_BYTES)) {
            degrees = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt32(bytes);
            this.degrees = (intVal / MAX_INT) * FLOAT_RANGE;
        }
    }

    /**
     * Get the longitude in degrees.
     *
     * @return Degrees in range [-180,180], or Double.POSITIVE_INFINITY if error condition was
     *     specified.
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        if (degrees == Double.POSITIVE_INFINITY) {
            return INVALID_BYTES.clone();
        }
        int intVal = (int) Math.round((degrees / FLOAT_RANGE) * MAX_INT);
        return PrimitiveConverter.int32ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public abstract String getDisplayName();
}
