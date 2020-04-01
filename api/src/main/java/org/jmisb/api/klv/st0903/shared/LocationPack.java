package org.jmisb.api.klv.st0903.shared;

/**
 * Location Pack.
 *
 * This data transfer object supports various Boundary Series and Target Location.
 * <p>
 * Latitude, longitude and HAE are always required.
 * <p>
 * If setting individual components, be aware that the standard deviations are
 * conceptually a single group. Similarly, the correlation values are "grouped".
 * Unless all the elements in a group are set, it won't make sense. Further, if
 * the correlation group values are set, the standard deviation group values
 * also need to be set.
 */
public class LocationPack
{

    private Double lat;
    private Double lon;
    private Double hae;
    private Double sigEast;
    private Double sigNorth;
    private Double sigUp;
    private Double rhoEastNorth;
    private Double rhoEastUp;
    private Double rhoNorthUp;

    /**
     * Constructor.
     *
     * @param lat Latitude in degrees of a point with respect to the WGS84 datum.
     * @param lon Longitude in degrees of a point with respect to the WGS84 datum.
     * @param hae Height of a point in meters above the WGS84 Ellipsoid (HAE).
     */
    public LocationPack(Double lat, Double lon, Double hae)
    {
        this(lat, lon, hae, null, null, null);
    }
    /**
     * Constructor.
     *
     * @param lat Latitude in degrees of a point with respect to the WGS84 datum.
     * @param lon Longitude in degrees of a point with respect to the WGS84 datum.
     * @param hae Height of a point in meters above the WGS84 Ellipsoid (HAE).
     * @param sigEast Standard deviation of the location of the point with respect to the ENU coordinate system East axis.
     * @param sigNorth Standard deviation of the location of the point with respect to the ENU coordinate system North axis.
     * @param sigUp Standard deviation of the location of the point with respect to the ENU coordinate system Up axis.
     */
    public LocationPack(Double lat, Double lon, Double hae, Double sigEast, Double sigNorth, Double sigUp)
    {
        this(lat, lon, hae, sigEast, sigNorth, sigUp, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param lat Latitude in degrees of a point with respect to the WGS84 datum.
     * @param lon Longitude in degrees of a point with respect to the WGS84 datum.
     * @param hae Height of a point in meters above the WGS84 Ellipsoid (HAE).
     * @param sigEast Standard deviation of the location of the point with respect to the ENU coordinate system East axis.
     * @param sigNorth Standard deviation of the location of the point with respect to the ENU coordinate system North axis.
     * @param sigUp Standard deviation of the location of the point with respect to the ENU coordinate system Up axis.
     * @param rhoEastNorth Correlation coefficient between the East and North components of error.
     * @param rhoEastUp Correlation coefficient between East and Up components of error.
     * @param rhoNorthUp Correlation coefficient between North and Up components of error.
     */
    public LocationPack(Double lat, Double lon, Double hae, Double sigEast, Double sigNorth, Double sigUp, Double rhoEastNorth, Double rhoEastUp, Double rhoNorthUp)
    {
        this.lat = lat;
        this.lon = lon;
        this.hae = hae;
        this.sigEast = sigEast;
        this.sigNorth = sigNorth;
        this.sigUp = sigUp;
        this.rhoEastNorth = rhoEastNorth;
        this.rhoEastUp = rhoEastUp;
        this.rhoNorthUp = rhoNorthUp;
    }

    /**
     * Get the latitude.
     *
     * @return Latitude in degrees of a point with respect to the WGS84 datum.
     */
    public Double getLat()
    {
        return lat;
    }

    /**
     * Set the latitude.
     *
     * @param lat Latitude in degrees of a point with respect to the WGS84 datum.
     */
    public void setLat(Double lat)
    {
        this.lat = lat;
    }

    /**
     * Get the longitude.
     *
     * @return Longitude in degrees of a point with respect to the WGS84 datum.
     */
    public Double getLon()
    {
        return lon;
    }

    /**
     * Set the longitude.
     *
     * @param lon Longitude in degrees of a point with respect to the WGS84 datum.
     */
    public void setLon(Double lon)
    {
        this.lon = lon;
    }

    /**
     * Get the Height above Ellipsoid (HAE, or altitude).
     *
     * @return Height of a point in meters above the WGS84 Ellipsoid (HAE).
     */
    public Double getHae()
    {
        return hae;
    }

    /**
     * Set the Height above Ellipsoid (HAE, or altitude).
     *
     * @param hae Height of a point in meters above the WGS84 Ellipsoid (HAE).
     */
    public void setHae(Double hae)
    {
        this.hae = hae;
    }

    /**
     * Get the standard deviation for the location - East axis.
     *
     * @return Standard deviation of the location of the point with respect to the ENU coordinate system East axis.
     */
    public Double getSigEast()
    {
        return sigEast;
    }

    /**
     * Set the standard deviation for the location - East axis.
     *
     * @param sigEast Standard deviation of the location of the point with respect to the ENU coordinate system East axis.
     */
    public void setSigEast(Double sigEast)
    {
        this.sigEast = sigEast;
    }

    /**
     * Get the standard deviation for the location - North axis.
     *
     * @return Standard deviation of the location of the point with respect to the ENU coordinate system North axis.
     */
    public Double getSigNorth()
    {
        return sigNorth;
    }

    /**
     * Set the standard deviation for the location - North axis.
     *
     * @param sigNorth Standard deviation of the location of the point with respect to the ENU coordinate system North axis.
     */
    public void setSigNorth(Double sigNorth)
    {
        this.sigNorth = sigNorth;
    }

    /**
     * Get the standard deviation for the location - Up axis.
     *
     * @return Standard deviation of the location of the point with respect to the ENU coordinate system Up axis.
     */
    public Double getSigUp()
    {
        return sigUp;
    }

    /**
     * Get the standard deviation for the location - Up axis.
     *
     * @param sigUp Standard deviation of the location of the point with respect to the ENU coordinate system Up axis.
     */
    public void setSigUp(Double sigUp)
    {
        this.sigUp = sigUp;
    }

    /**
     * Get the correlation between the East and North axis parts of the location error estimate.
     *
     * @return Correlation coefficient between the East and North components of error.
     */
    public Double getRhoEastNorth()
    {
        return rhoEastNorth;
    }

    /**
     * Set the correlation between the East and North axis parts of the location error estimate.
     *
     * @param rhoEastNorth Correlation coefficient between the East and North components of error.
     */
    public void setRhoEastNorth(Double rhoEastNorth)
    {
        this.rhoEastNorth = rhoEastNorth;
    }

    /**
     * Get the correlation between the East and Up axis parts of the location error estimate.
     *
     * @return Correlation coefficient between the East and Up components of error.
     */
    public Double getRhoEastUp()
    {
        return rhoEastUp;
    }

    /**
     * Set the correlation between the East and Up axis parts of the location error estimate.
     *
     * @param rhoEastUp Correlation coefficient between the East and Up components of error.
     */
    public void setRhoEastUp(Double rhoEastUp)
    {
        this.rhoEastUp = rhoEastUp;
    }

    /**
     * Get the correlation between the North and Up axis parts of the location error estimate.
     *
     * @return Correlation coefficient between the North and Up components of error.
     */
    public Double getRhoNorthUp()
    {
        return rhoNorthUp;
    }

    /**
     * Set the correlation between the North and Up axis parts of the location error estimate.
     *
     * @param rhoNorthUp Correlation coefficient between the North and Up components of error.
     */
    public void setRhoNorthUp(Double rhoNorthUp)
    {
        this.rhoNorthUp = rhoNorthUp;
    }

}
