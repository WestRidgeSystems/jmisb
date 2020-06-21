package org.jmisb.api.klv.st0601;

/**
 * Ground Range (ST 0601 tag 57)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Horizontal distance from ground position of aircraft relative to nadir, and target of interest.
 *
 * <p>Map 0..(2^32-1) to 0..5000000 meters.
 *
 * <p>Resolution: ~1.2 milli meters.
 *
 * </blockquote>
 */
public class GroundRange extends UasRange {
    /**
     * Create from value
     *
     * @param meters Ground range, in meters
     */
    public GroundRange(double meters) {
        super(meters);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Ground range, encoded as a 4-byte unsigned int
     */
    public GroundRange(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Ground Range";
    }
}
