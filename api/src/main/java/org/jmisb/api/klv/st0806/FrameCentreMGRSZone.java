package org.jmisb.api.klv.st0806;

/**
 * Frame Centre MGRS Zone (ST0806 Tag 18).
 *
 * <p>First two characters of Frame Centre MGRS coordinates, UTM zone 01 through 60.
 */
public class FrameCentreMGRSZone extends AbstractMGRSZone implements IRvtMetadataValue {
    public static final String FRAME_CENTRE_MGRS_ZONE = "Frame Center MGRS Zone";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public FrameCentreMGRSZone(int value) {
        super(FRAME_CENTRE_MGRS_ZONE, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 1 byte.
     */
    public FrameCentreMGRSZone(byte[] bytes) {
        super(FRAME_CENTRE_MGRS_ZONE, bytes);
    }
}
