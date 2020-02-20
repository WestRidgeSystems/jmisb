package org.jmisb.api.klv.st0601.dto;

/**
 * Data transfer object for Location.
 *
 * This is used by Airbase Locations (ST0601 Tag 130) and Waypoint List
 * (ST0601 Tag 141).
 */
public class Location
{
    private double latitude;
    private double longitude;
    private double hae;

    /**
     * Get the location latitude
     * @return the latitude of the location in degrees
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * Set the location latitude
     * @param latitude the latitude of the location in degrees
     */
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Get the location longitude
     * @return the longitude of the location in degrees
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     * Set the location longitude
     * @param longitude the longitude of the location in degrees
     */
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    /**
     * Get the elevation of the location
     * @return the elevation in metres above the WGS84 ellipsoid
     */
    public double getHAE()
    {
        return hae;
    }

    /**
     * Set the elevation of the location
     * @param hae the elevation in metres above the WGS84 ellipsoid
     */
    public void setHAE(double hae)
    {
        this.hae = hae;
    }
}
