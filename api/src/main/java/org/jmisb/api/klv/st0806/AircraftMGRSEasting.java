package org.jmisb.api.klv.st0806;

/**
 * Aircraft MGRS Easting (ST0806 Tag 16).
 *
 * Sixth through tenth character of Aircraft MGRS coordinates. Range is from 0
 * to 99,999 representing the 5-digit Easting value in meters.
 * <p>
 * Resolution: 1 meter
 */
public class AircraftMGRSEasting extends AbstractMGRSEastingOrNorthing implements IRvtMetadataValue
{
    public static final String AIRCRAFT_MGRS_EASTING = "Aircraft MGRS Easting";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public AircraftMGRSEasting(int value)
    {
        super(AIRCRAFT_MGRS_EASTING, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 3 bytes.
     */
    public AircraftMGRSEasting(byte[] bytes)
    {
        super(AIRCRAFT_MGRS_EASTING, bytes);
    }
}
