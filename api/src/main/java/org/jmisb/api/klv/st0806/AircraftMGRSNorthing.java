package org.jmisb.api.klv.st0806;

/**
 * Aircraft MGRS Northing (ST0806 Tag 17).
 *
 * Eleventh through fifteenth character of Aircraft MGRS coordinates. Range is
 * from 0 to 99,999 representing the 5-digit Northing value in meters.
 * <p>
 * Resolution: 1 meter.
 */
public class AircraftMGRSNorthing extends AbstractMGRSEastingOrNorthing implements IRvtMetadataValue
{
    public static final String AIRCRAFT_MGRS_NORTHING = "Aircraft MGRS Northing";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public AircraftMGRSNorthing(int value)
    {
        super(AIRCRAFT_MGRS_NORTHING, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 3 bytes.
     */
    public AircraftMGRSNorthing(byte[] bytes)
    {
        super(AIRCRAFT_MGRS_NORTHING, bytes);
    }
}
