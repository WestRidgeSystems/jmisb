package org.jmisb.st0601.dto;

/**
 * Data transfer object for Location.
 *
 * <p>This is used by Airbase Locations (ST0601 Item 130) and Waypoint List (ST0601 Item 141).
 */
public class Location {
    private double latitude;
    private double longitude;
    private double hae = -1000;

    /**
     * Get the location latitude.
     *
     * @return the latitude of the location in degrees
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the location latitude.
     *
     * @param latitude the latitude of the location in degrees
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the location longitude.
     *
     * @return the longitude of the location in degrees
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the location longitude.
     *
     * @param longitude the longitude of the location in degrees
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the elevation of the location.
     *
     * @return the elevation in meters above the WGS84 ellipsoid, or -1000 for invalid / unknown
     */
    public double getHAE() {
        return hae;
    }

    /**
     * Set the elevation of the location.
     *
     * @param hae the elevation in meters above the WGS84 ellipsoid, or -1000 for invalid / unknown
     */
    public void setHAE(double hae) {
        this.hae = hae;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash =
                61 * hash
                        + (int)
                                (Double.doubleToLongBits(this.latitude)
                                        ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash =
                61 * hash
                        + (int)
                                (Double.doubleToLongBits(this.longitude)
                                        ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash =
                61 * hash
                        + (int)
                                (Double.doubleToLongBits(this.hae)
                                        ^ (Double.doubleToLongBits(this.hae) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return Double.doubleToLongBits(this.hae) == Double.doubleToLongBits(other.hae);
    }
}
