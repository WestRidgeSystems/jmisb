package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Corner Offset (ST 0601 Items 26-33).
 *
 * <p>Items 26-33 encode the corner locations of the sensor footprint as offsets from the frame
 * center (Items 23 and 24). These values use only two bytes each, so their resolution is limited
 * (~0.25m at equator). For higher precision, see Items 82-89, Corner Latitude/Longitude Points
 * (Full).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Frame Latitude/Longitude, offset for X corner. Based on WGS84 ellipsoid. Use with Frame Center
 * Latitude/Longitude.
 *
 * <p>Map (-2^15-1)..(2^15-1) to +/-0.075. Use -2^15 as an "error" indicator. -2^15 = 0x8000.
 *
 * <p>Resolution: ~1.2 micro degrees, ~0.25 meters at equator.
 *
 * </blockquote>
 */
public class CornerOffset implements IUasDatalinkValue {
    /** Display name for first offset corner latitude. */
    public static final String CORNER_LAT_1 = "Offset Corner Latitude Point 1";
    /** Display name for first offset corner longitude. */
    public static final String CORNER_LON_1 = "Offset Corner Longitude Point 1";
    /** Display name for second offset corner latitude. */
    public static final String CORNER_LAT_2 = "Offset Corner Latitude Point 2";
    /** Display name for second offset corner longitude. */
    public static final String CORNER_LON_2 = "Offset Corner Longitude Point 2";
    /** Display name for third offset corner latitude. */
    public static final String CORNER_LAT_3 = "Offset Corner Latitude Point 3";
    /** Display name for third offset corner longitude. */
    public static final String CORNER_LON_3 = "Offset Corner Longitude Point 3";
    /** Display name for fourth offset corner latitude. */
    public static final String CORNER_LAT_4 = "Offset Corner Latitude Point 4";
    /** Display name for fourth offset corner longitude. */
    public static final String CORNER_LON_4 = "Offset Corner Longitude Point 4";

    private final double degrees;
    private static final byte[] invalidBytes = new byte[] {(byte) 0x80, (byte) 0x00};
    private static final double FLOAT_RANGE = 0.15;
    private static final double INT_RANGE = 65534.0; // 2^15-1
    private final String displayName;

    /**
     * Create from value.
     *
     * @param degrees The value in degrees, in range [-0.075,0.075]
     * @param displayName human readable (display) name for this type - see static values
     */
    public CornerOffset(double degrees, String displayName) {
        if (degrees != Double.POSITIVE_INFINITY && (degrees < -0.075 || degrees > 0.075)) {
            throw new IllegalArgumentException("Corner Offset must be in range [-0.075,0.075]");
        }
        this.degrees = degrees;
        this.displayName = displayName;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 2
     * @param displayName human readable (display) name for this type - see static values
     */
    public CornerOffset(byte[] bytes, String displayName) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException("Corner Offset encoding is a 2-byte signed int");
        }
        this.displayName = displayName;
        if (Arrays.equals(bytes, invalidBytes)) {
            degrees = Double.POSITIVE_INFINITY;
        } else {
            int intVal = PrimitiveConverter.toInt16(bytes);
            this.degrees = (intVal / INT_RANGE) * FLOAT_RANGE;
        }
    }

    /**
     * Get the value in degrees.
     *
     * @return The value in degrees, or {@code Double.POSITIVE_INFINITY} to indicate an error
     *     condition
     */
    public double getDegrees() {
        return degrees;
    }

    @Override
    public byte[] getBytes() {
        if (degrees == Double.POSITIVE_INFINITY) {
            return invalidBytes.clone();
        }

        short shortVal = (short) Math.round((degrees / FLOAT_RANGE) * INT_RANGE);
        return PrimitiveConverter.int16ToBytes(shortVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f\u00B0", degrees);
    }

    @Override
    public final String getDisplayName() {
        return displayName;
    }
}
