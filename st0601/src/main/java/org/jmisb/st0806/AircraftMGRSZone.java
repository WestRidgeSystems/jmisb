package org.jmisb.st0806;

/**
 * Aircraft MGRS Zone (ST0806 Tag 14).
 *
 * <p>First two characters of Aircraft MGRS coordinates, UTM zone 01 through 60.
 */
public class AircraftMGRSZone extends AbstractMGRSZone implements IRvtMetadataValue {
    private static final String AIRCRAFT_MGRS_ZONE = "Aircraft MGRS Zone";

    /**
     * Create from value.
     *
     * @param value integer value, in the range 1 to 60.
     */
    public AircraftMGRSZone(int value) {
        super(AIRCRAFT_MGRS_ZONE, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, of length 1 byte.
     */
    public AircraftMGRSZone(byte[] bytes) {
        super(AIRCRAFT_MGRS_ZONE, bytes);
    }
}
