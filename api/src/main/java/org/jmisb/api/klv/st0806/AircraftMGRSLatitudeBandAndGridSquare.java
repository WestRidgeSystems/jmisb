package org.jmisb.api.klv.st0806;

/**
 * MGRS Latitude Band and Grid Square (ST0806 Tag 15).
 *
 * <p>Third, fourth and fifth characters of Aircraft MGRS coordinates. Third character is the alpha
 * code for the latitude band (A through Z, omitting I and O). Fourth and fifth characters are the
 * 2-character alpha code for the grid square designator (WGS 84). Note that latitude bands A &amp;
 * B correspond to Antarctic UPS regions and Y &amp; Z correspond to Arctic UPS regions.
 */
public class AircraftMGRSLatitudeBandAndGridSquare extends AbstractMGRSLatitudeBandAndGridSquare {
    private static final String AIRCRAFT_MGRS_LATITUDE_BAND_AND_GRID_SQUARE =
            "Aircraft MGRS Latitude Band and Grid Square";

    /**
     * Create from value.
     *
     * @param value The string value, which can only use the ASCII subset of UTF-8.
     */
    public AircraftMGRSLatitudeBandAndGridSquare(String value) {
        super(AIRCRAFT_MGRS_LATITUDE_BAND_AND_GRID_SQUARE, value);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public AircraftMGRSLatitudeBandAndGridSquare(byte[] bytes) {
        super(AIRCRAFT_MGRS_LATITUDE_BAND_AND_GRID_SQUARE, bytes);
    }
}
