package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Platform Vertical Speed (ST 0601 Item 51).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Vertical speed of the aircraft relative to zenith.
 *
 * <p>Positive ascending, negative descending.
 *
 * <p>Platform Vertical Speed is the climb or decent rate in meters per second of an airborne
 * platform in the zenith direction. Positive values indicate an ascending platform, while negative
 * value indicate descending.
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-180m/s. Use -(2^15) as "out of range" indicator. -(2^15) =
 * 0x8000.
 *
 * <p>Resolution: ~0.0055 meters/second
 *
 * </blockquote>
 */
public class PlatformVerticalSpeed implements IUasDatalinkValue {
    private double verticalSpeed;
    private static final byte[] invalidBytes = new byte[] {(byte) 0x80, (byte) 0x00};
    private static final double FLOAT_RANGE = 360.0;
    private static final double INT_RANGE = 65534.0;

    /**
     * Create from value
     *
     * @param speed The value in metres per second, or {@code Double.POSITIVE_INFINITY} to represent
     *     "out of range"
     */
    public PlatformVerticalSpeed(final double speed) {
        if (speed != Double.POSITIVE_INFINITY && (speed < -180 || speed > 180)) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " must be in range [-180,180]");
        }

        this.verticalSpeed = speed;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public PlatformVerticalSpeed(final byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 2-byte signed int");
        }

        if (Arrays.equals(bytes, invalidBytes)) {
            this.verticalSpeed = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt16(bytes);
            this.verticalSpeed = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in metres per second
     *
     * @return The value in metres per second, or {@code Double.POSITIVE_INFINITY} if "out of range"
     */
    public double getVerticalSpeed() {
        return this.verticalSpeed;
    }

    @Override
    public byte[] getBytes() {
        if (verticalSpeed == Double.POSITIVE_INFINITY) {
            return invalidBytes.clone();
        }

        short shortVal = (short) Math.round((verticalSpeed / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3fm/s", verticalSpeed);
    }

    @Override
    public final String getDisplayName() {
        return "Platform Vertical Speed";
    }
}
