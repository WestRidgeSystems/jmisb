package org.jmisb.cumulo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

/**
 * Singleton class to look up EGM-96 geoid offsets
 */
public class Geoid {

    /**
     * Singleton instance
     */
    private static Geoid instance;

    public static final String FILE_GEOID_GZ = "/EGM96complete.dat.gz";

    private static final int ROWS = 719;  // (89.75 + 89.75)/0.25 + 1 = 719
    private static final int COLS = 1440; // 359.75/0.25 + 1 = 1440

    public static final double LATITUDE_MAX = 90.0;
    public static final double LATITUDE_MAX_GRID = 89.74;
    public static final double LATITUDE_ROW_FIRST = 89.50;
    public static final double LATITUDE_ROW_LAST = -89.50;
    public static final double LATITUDE_MIN_GRID = -89.74;
    public static final double LATITUDE_MIN = -90.0;
    public static final double LATITUDE_STEP = 0.25;

    public static final double LONGITUDE_MIN_GRID = 0.0;
    public static final double LONGITUDE_MAX_GRID = 359.75;
    public static final double LONGITUDE_STEP = 0.25;

    public static final String INVALID_OFFSET = "-9999.99";
    public static final String COMMENT_PREFIX = "//";

    private static double[][] offset = new double[ROWS][COLS];
    private static double offsetNorthPole = 0;
    private static double offsetSouthPole = 0;

    /**
     * Private constructor to prevent instantiation
     *
     * @throws IOException if the geoid file could not be loaded
     */
    private Geoid() throws IOException {

        InputStream is = Geoid.class.getResourceAsStream(FILE_GEOID_GZ);
        GZIPInputStream gis = new GZIPInputStream(is);
        InputStreamReader isr = new InputStreamReader(gis);
        BufferedReader br = new BufferedReader(isr);

        if (!readGeoidOffsets(br)) {
            throw new IOException("Error parsing geoid data");
        }
    }

    /**
     * Get the singleton instance
     *
     * @return The singleton instance
     * @throws IOException if the geoid data file could not be loaded
     */
    public static Geoid getInstance() throws IOException {
        if (instance == null) {
            instance = new Geoid();
        }
        return instance;
    }

    /**
     * Get the geoid offset for a given latitude/longitude
     *
     * @param latitude Latitude, in degrees
     * @param longitude Longitude, in degrees
     * @return Geoid offset
     */
    public double getOffset(double latitude, double longitude) {

        // normalize coordinates
        Location loc = new Location(latitude, longitude);

        // special case for exact grid positions
        if (isGridPoint(loc)) {
            return getGridOffset(latitude, longitude);
        }

        // get four grid locations surrounding the target location for interpolation
        Location lowerLeft = loc.floor();
        Location upperLeft = getUpperLocation(lowerLeft);
        Location lowerRight = getRightLocation(lowerLeft);
        Location upperRight = getUpperLocation(lowerRight);

        return bilinearInterpolation(loc, lowerLeft, upperLeft, lowerRight, upperRight);
    }

    private static double bilinearInterpolation(Location target, Location lowerLeft, Location upperLeft,
                                                Location lowerRight, Location upperRight) {
        double fq11 = getGridOffset(lowerLeft);
        double fq12 = getGridOffset(upperLeft);
        double fq21 = getGridOffset(lowerRight);
        double fq22 = getGridOffset(upperRight);

        double x1 = lowerLeft.getLongitude();
        double x2 = upperRight.getLongitude();
        double y1 = upperRight.getLatitude();
        double y2 = lowerLeft.getLatitude();

        // special case for latitude moving from 359.75 -> 0
        if (x1 == 359.75 && x2 == 0.0) {
            x2 = 360.0;
        }

        double x = target.getLongitude();
        double y = target.getLatitude();

        double f11 = fq11 * (x2 - x) * (y2 - y);
        double f12 = fq12 * (x2 - x) * (y - y1);
        double f21 = fq21 * (x - x1) * (y2 - y);
        double f22 = fq22 * (x - x1) * (y - y1);

        return (f11 + f12 + f21 + f22) / ((x2 - x1) * (y2 - y1));
    }

    private Location getUpperLocation(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        if (lat == LATITUDE_MAX_GRID) {
            lat = LATITUDE_MAX;
        } else if (lat == LATITUDE_ROW_FIRST) {
            lat = LATITUDE_MAX_GRID;
        } else if (lat == LATITUDE_MIN) {
            lat = LATITUDE_MIN_GRID;
        } else if (lat == LATITUDE_MIN_GRID) {
            lat = LATITUDE_ROW_LAST;
        } else {
            lat += LATITUDE_STEP;
        }

        return new Location(lat, lng);
    }

    private Location getRightLocation(Location location) {
        return new Location(location.getLatitude(), location.getLongitude() + LONGITUDE_STEP);
    }

    private static double getGridOffset(Location location) {
        return getGridOffset(location.getLatitude(), location.getLongitude());
    }

    private static double getGridOffset(double lat, double lng) {

        if (!latIsGridPoint(lat) || !lngIsGridPoint(lng)) {
            throw new IllegalArgumentException("Invalid grid point: " + lat + ", " + lng);
        }

        if (latIsPole(lat)) {
            if (lat == LATITUDE_MAX) {
                return offsetNorthPole;
            } else {
                return offsetSouthPole;
            }
        }

        int i = latToI(lat);
        int j = lngToJ(lng);

        return offset[i][j];
    }

    private boolean readGeoidOffsets(BufferedReader br) throws IOException {
        assignMissingOffsets();

        String line = br.readLine();
        int index = 1;

        while (line != null) {
            index++;

            // skip comment lines
            if (lineIsOk(line)) {
                StringTokenizer t = new StringTokenizer(line);
                int c = t.countTokens();

                if (c != 3) {
                    throw new IOException("Error on line " + index + ": found " + c + " tokens (expected 3): '" + line + "'");
                }

                double lat = Double.parseDouble(t.nextToken());
                double lng = Double.parseDouble(t.nextToken());
                double off = Double.parseDouble(t.nextToken());

                if (latLongOk(lat, lng, index)) {
                    int latIndex = 0;

                    if (lat == LATITUDE_MAX) {
                        offsetNorthPole = off;
                    } else if (lat == LATITUDE_MIN) {
                        offsetSouthPole = off;
                    } else {
                        if (lat == LATITUDE_MIN_GRID) {
                            latIndex = ROWS - 1;
                        } else if (lat != LATITUDE_MAX_GRID) {
                            latIndex = (int) ((LATITUDE_MAX - lat) / LATITUDE_STEP) - 1;
                        }

                        int lngIndex = (int) (lng / LONGITUDE_STEP);
                        offset[latIndex][lngIndex] = off;
                    }
                }
            }

            line = br.readLine();
        }

        // test if we read all values as expected
        return !hasMissingOffsets();
    }

    private static boolean lineIsOk(String line) {
        // skip comment lines
        if (line.startsWith(COMMENT_PREFIX)) {
            return false;
        }

        // skip lines with not offset value
        return !line.endsWith(INVALID_OFFSET);
    }

    private static void assignMissingOffsets() {

        offsetNorthPole = Double.POSITIVE_INFINITY;
        offsetSouthPole = Double.POSITIVE_INFINITY;

        for (double[] row : offset) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }
    }

    private static boolean hasMissingOffsets() {
        if (offsetNorthPole == Double.POSITIVE_INFINITY) {
            return true;
        }
        if (offsetSouthPole == Double.POSITIVE_INFINITY) {
            return true;
        }

        for (double[] row : offset) {
            for (double val : row) {
                if (val == Double.POSITIVE_INFINITY) return true;
            }
        }

        return false;
    }

    private static boolean latLongOk(double lat, double lng, int line) {
        if (isLatInvalid(lat)) {
            System.err.println("error on line " + line + ": latitude " + lat + " out or range [" + LATITUDE_MIN + "," + LATITUDE_MAX + "]");
            return false;
        }

        if (isGridLngInvalid(lng)) {
            System.err.println("error on line " + line + ": longitude " + lng + " out or range [" + LONGITUDE_MIN_GRID + "," + LONGITUDE_MAX_GRID + "]");
            return false;
        }

        return true;
    }

    private static boolean isLatInvalid(double lat) {
        return !(lat >= LATITUDE_MIN) || !(lat <= LATITUDE_MAX);
    }

    private static boolean isGridLngInvalid(double lng) {
        return !(lng >= LONGITUDE_MIN_GRID) || !(lng <= LONGITUDE_MAX_GRID);
    }

    private static boolean isGridPoint(Location location) {
        return latIsGridPoint(location.getLatitude()) && lngIsGridPoint(location.getLongitude());
    }

    private static boolean latIsGridPoint(double lat) {
        if (isLatInvalid(lat)) {
            return false;
        }

        if (latIsPole(lat)) {
            return true;
        }

        if (lat == LATITUDE_MAX_GRID || lat == LATITUDE_MIN_GRID) {
            return true;
        }

        return lat <= LATITUDE_ROW_FIRST && lat >= LATITUDE_ROW_LAST &&
            lat / LATITUDE_STEP == Math.round(lat / LATITUDE_STEP);
    }

    private static boolean lngIsGridPoint(double lng) {
        if (isGridLngInvalid(lng)) {
            return false;
        }
        return lng / LONGITUDE_STEP == Math.round(lng / LONGITUDE_STEP);
    }

    private static boolean latIsPole(double lat) {
        return lat == LATITUDE_MAX || lat == LATITUDE_MIN;
    }

    private static int latToI(double lat) {
        if (lat == LATITUDE_MAX_GRID) {
            return 0;
        }
        if (lat == LATITUDE_MIN_GRID) {
            return ROWS - 1;
        } else {
            return (int) ((LATITUDE_ROW_FIRST - lat) / LATITUDE_STEP) + 1;
        }
    }

    private static int lngToJ(double lng) {
        return (int) (lng / LONGITUDE_STEP);

    }

    /**
     * Normalized lat/lng, where lat range is [-90,90] and lng range is [0,360]
     */
    private static class Location {
        private static final double EPSILON = 0.00000001;

        private double lat;
        private double lng;

        Location(double latitude, double longitude) {
            lat = normalizeLat(latitude);
            lng = normalizeLong(longitude);
        }

        double getLatitude() {
            return lat;
        }

        double getLongitude() {
            return lng;
        }

        private double normalizeLat(double lat) {
            if (lat > 90.0) {
                return normalizeLatPositive(lat);
            } else if (lat < -90) {
                return -normalizeLatPositive(-lat);
            }

            return lat;
        }

        private double normalizeLatPositive(double lat) {
            double delta = (lat - 90.0) % 360.0;

            if (delta <= 180.0) {
                lat = 90.0 - delta;
            } else {
                lat = delta - 270.0;
            }

            return lat;
        }

        private double normalizeLong(double lng) {
            lng %= 360.0;

            if (lng >= 0.0) {
                return lng;
            } else {
                return lng + 360;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (!(o instanceof Location)) {
                return false;
            }

            Location other = (Location) o;
            if (Math.abs(getLatitude() - other.getLatitude()) > EPSILON) {
                return false;
            }
            return !(Math.abs(getLongitude() - other.getLongitude()) > EPSILON);
        }

        @Override
        public String toString() {
            return "(" + getLatitude() + "," + getLongitude() + ")";
        }

        Location floor() {
            final double latFloor = Math.floor(getLatitude() / LATITUDE_STEP) * LATITUDE_STEP;
            final double lngFloor = Math.floor(getLongitude() / LATITUDE_STEP) * LATITUDE_STEP;
            return new Location(latFloor, lngFloor);
        }
    }
}
