package org.jmisb.api.klv.st0806;

/**
 * Frame Center MGRS Easting (ST0806 Tag 2).
 *
 * <p>Sixth through tenth character of Frame Center MGRS coordinates. Range is from 0 to 99,999
 * representing the 5-digit Easting value in meters.
 *
 * <p>Resolution: 1 meter
 */
public class FrameCenterMGRSEasting extends AbstractMGRSEastingOrNorthing
        implements IRvtMetadataValue {
    public static final String FRAME_CENTRE_MGRS_EASTING = "Frame Center MGRS Easting";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public FrameCenterMGRSEasting(int value) {
        super(FRAME_CENTRE_MGRS_EASTING, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 3 bytes.
     */
    public FrameCenterMGRSEasting(byte[] bytes) {
        super(FRAME_CENTRE_MGRS_EASTING, bytes);
    }
}
