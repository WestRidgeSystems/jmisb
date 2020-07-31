package org.jmisb.api.klv.st0601;

/**
 * Slant Range (ST 0601 Item 21).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Slant range in meters. Distance to target.
 *
 * <p>Map 0..(2^32-1) to 0..5000000 meters.
 *
 * <p>Resolution: ~1.2 milli meters.
 *
 * </blockquote>
 */
public class SlantRange extends UasRange {
    /**
     * Create from value.
     *
     * @param meters Slant range, in meters
     */
    public SlantRange(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Slant range, encoded as a 4-byte unsigned int
     */
    public SlantRange(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Slant Range";
    }
}
