package org.jmisb.elevation.geoid;

import java.io.IOException;

/**
 * Geoid (EGM 96) conversion routines.
 *
 * <p>This provides convenience API for converting between heights given relative to the WGS-84
 * Ellipsoid and "MSL", which is approximated by the EGM 96 geoid.
 *
 * <p>Generally, you want to create an instance of this class, then use {@code toHAE()} to convert
 * from MSL to HAE, or use {@code toMSL()} to convert from HAE to MSL.
 *
 * <p>The standard usage is bicubic interpolation, which will give accuracy to order millimeters.
 * Bilinear interpolation is also supported, and will give accuracy of the order 10s of centimeters
 * while being slightly less complex to calculate. Using nearest neighbor might be order low meters
 * of error, and is computationally equivalent to bilinear interpolation.
 */
public class Geoid {

    private final Grid grid;
    private static final float HALF_WAY = 0.5f;
    private static final int NUM_ROWS_BICUBIC = 4;
    private static final int ROW_0_INDEX = 0;
    private static final int ROW_1_INDEX = 1;
    private static final int ROW_2_INDEX = 2;
    private static final int ROW_3_INDEX = 3;

    /**
     * Constructor.
     *
     * @throws IOException if the grid could not be initialised.
     */
    public Geoid() throws IOException {
        this.grid = Grid.fromEGM96Grid();
    }

    /**
     * Convert MSL elevation to HAE for a given location.
     *
     * @param lat latitude of the location (degrees, WGS-84, north positive)
     * @param lon longitude of the location (degrees, WGS-84, east positive)
     * @param mslElevation the MSL elevation in meters
     * @return the HAE elevation in meters
     */
    public double toHAE(final double lat, final double lon, final double mslElevation) {
        return mslElevation + getValueBicubic(lat, lon);
    }

    /**
     * Convert HAE elevation to MSL for a given location.
     *
     * @param lat latitude of the location (degrees, WGS-84, north positive)
     * @param lon longitude of the location (degrees, WGS-84, east positive)
     * @param haeElevation the HAE elevation in meters
     * @return the MSL elevation in meters
     */
    public double toMSL(final double lat, final double lon, final double haeElevation) {
        return haeElevation - getValueBicubic(lat, lon);
    }

    /**
     * Get the geoidal separation at a given location.
     *
     * <p>This version uses nearest neighbor, which is accurate to low numbers of meters.
     *
     * @param lat latitude of the location (degrees, WGS-84, north positive)
     * @param lon longitude of the location (degrees, WGS-84, east positive)
     * @return geoidal separation in meters (positive for geoid above ellipsoid)
     */
    public float getValueNearest(final double lat, final double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float topLeft = grid.findValue(baseRow, baseColumn);
        float topRight = grid.findValue(baseRow, baseColumn + 1);
        float bottomLeft = grid.findValue(baseRow + 1, baseColumn);
        float bottomRight = grid.findValue(baseRow + 1, baseColumn + 1);
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        double xOffset = getOffsetFromBaseColumn(lon, baseColumn);
        if (yOffset < HALF_WAY) {
            /* Closer to the top */
            if (xOffset < HALF_WAY) {
                return topLeft;
            } else {
                return topRight;
            }
        } else {
            /* Closer to the bottom */
            if (xOffset < HALF_WAY) {
                return bottomLeft;
            } else {
                return bottomRight;
            }
        }
    }

    /**
     * Get the geoidal separation at a given location. This version uses bilinear interpolation,
     * which is accurate to 10s of centimeters.
     *
     * @param lat latitude of the location (degrees, WGS-84, north positive)
     * @param lon longitude of the location (degrees, WGS-84, east positive)
     * @return geoidal separation in meters (positive for geoid above ellipsoid)
     */
    public float getValueBilinear(final double lat, final double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float topLeft = grid.findValue(baseRow, baseColumn);
        float topRight = grid.findValue(baseRow, baseColumn + 1);
        float bottomLeft = grid.findValue(baseRow + 1, baseColumn);
        float bottomRight = grid.findValue(baseRow + 1, baseColumn + 1);
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        double xOffset = getOffsetFromBaseColumn(lon, baseColumn);
        float value =
                interpolateBilinear(
                        topLeft,
                        topRight,
                        bottomLeft,
                        bottomRight,
                        (float) yOffset,
                        (float) xOffset);
        return value;
    }
    /**
     * Get the geoidal separation at a given location. This version uses bicubic interpolation,
     * which is accurate to order millimeters.
     *
     * @param lat latitude of the location (degrees, WGS-84, north positive)
     * @param lon longitude of the location (degrees, WGS-84, east positive)
     * @return geoidal separation in meters (positive for geoid above ellipsoid)
     */
    public float getValueBicubic(final double lat, final double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float[] interpolatedRows = new float[NUM_ROWS_BICUBIC];
        for (int i = 0; i < NUM_ROWS_BICUBIC; ++i) {
            float v00 = grid.findValue(baseRow - 1 + i, baseColumn - 1);
            float v01 = grid.findValue(baseRow - 1 + i, baseColumn);
            float v02 = grid.findValue(baseRow - 1 + i, baseColumn + 1);
            float v03 = grid.findValue(baseRow - 1 + i, baseColumn + 2);
            float xOffset = (float) getOffsetFromBaseColumn(lon, baseColumn);
            interpolatedRows[i] = interpolateCubic(v00, v01, v02, v03, xOffset);
        }
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        float value =
                interpolateCubic(
                        interpolatedRows[ROW_0_INDEX],
                        interpolatedRows[ROW_1_INDEX],
                        interpolatedRows[ROW_2_INDEX],
                        interpolatedRows[ROW_3_INDEX],
                        (float) yOffset);
        return value;
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private static float interpolateCubic(
            final float v00,
            final float v01,
            final float v02,
            final float v03,
            final float xOffset) {
        return (-0.5f * v00 + 1.5f * v01 - 1.5f * v02 + 0.5f * v03) * xOffset * xOffset * xOffset
                + (v00 - 2.5f * v01 + 2f * v02 - 0.5f * v03) * xOffset * xOffset
                + (-0.5f * v00 + 0.5f * v02) * xOffset
                + v01;
    }

    /**
     * Get the X-offset from the base column for a given longitude.
     *
     * @param lon the longitude
     * @param baseColumn the base column number (0 for west-most).
     * @return the proportion that {@code lon} is into the given cell.
     */
    double getOffsetFromBaseColumn(final double lon, final int baseColumn) {
        return (lon - (baseColumn * grid.getxResolution())) / grid.getxResolution();
    }

    /**
     * Get the Y-offset from the base row for a given latitude.
     *
     * @param lat the latitude
     * @param baseRow the row column number (0 for north-most).
     * @return the proportion that {@code lat} is into the given cell.
     */
    double getOffsetFromBaseRow(final double lat, final int baseRow) {
        return ((grid.getTop() - (baseRow * grid.getxResolution())) - lat) / grid.getxResolution();
    }

    /**
     * Get the column number for a given longitude.
     *
     * @param lon the longitude
     * @return the column "band" for the longitude value.
     */
    int getBaseColumn(final double lon) {
        return (int) Math.floor(lon / grid.getxResolution());
    }

    /**
     * Get the row number for a given latitude.
     *
     * @param lat the latitude
     * @return the row "band" for the latitude value.
     */
    int getBaseRow(final double lat) {
        return (int) Math.floor((grid.getTop() - lat) / grid.getyResolution());
    }

    private float interpolateBilinear(
            final float topLeft,
            final float topRight,
            final float bottomLeft,
            final float bottomRight,
            final float xOffset,
            final float yOffset) {
        float a = (1 - xOffset) * topLeft + xOffset * topRight;
        float b = (1 - xOffset) * bottomLeft + xOffset * bottomRight;
        return (1 - yOffset) * a + yOffset * b;
    }
}
