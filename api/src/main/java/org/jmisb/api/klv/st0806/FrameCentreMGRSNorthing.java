package org.jmisb.api.klv.st0806;

/**
 * Frame Center MGRS Northing (ST0806 Tag 2).
 *
 * Eleventh through fifteenth character of Frame Center MGRS coordinates. Range
 * is from 0 to 99,999 representing the 5-digit Northing value in meters.
 * <p>
 * Resolution: 1 meter
 */
public class FrameCentreMGRSNorthing extends AbstractMGRSEastingOrNorthing implements IRvtMetadataValue
{
    public static final String FRAME_CENTRE_MGRS_NORTHING = "Frame Center MGRS Northing";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public FrameCentreMGRSNorthing(int value)
    {
        super(FRAME_CENTRE_MGRS_NORTHING, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 3 bytes.
     */
    public FrameCentreMGRSNorthing(byte[] bytes)
    {
        super(FRAME_CENTRE_MGRS_NORTHING, bytes);
    }
}
