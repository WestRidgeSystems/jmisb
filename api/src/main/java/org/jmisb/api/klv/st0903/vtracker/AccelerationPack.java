package org.jmisb.api.klv.st0903.vtracker;

/**
 * Acceleration Pack.
 *
 * <p>This data transfer object supports the VTarget Acceleration.
 *
 * <p>The Acceleration DLP structure captures data about the acceleration of a moving object. The
 * DLP is a truncation pack where groups of data are optional unless needed (from the end only).
 * There are three defined groups as shown in Figure 18. The first, and highest priority group
 * includes East, North and Up components; these measurements provide acceleration along the
 * coordinate axes of the East-North-Up coordinate system specified by the Location pack for the
 * location of the moving object. The second group, Standard Deviations and medium priority group,
 * provides the standard deviations for the first group measurements. The third group, Correlation
 * Coefficients and lowest priority group, provides the three correlation coefficients for the
 * values in the first group.
 *
 * <p>East, North and Up are always required.
 *
 * <p>If setting individual components, be aware that the standard deviations are conceptually a
 * single group. Similarly, the correlation values are "grouped". Unless all the elements in a group
 * are set, it won't make sense. Further, if the correlation group values are set, the standard
 * deviation group values also need to be set.
 */
public class AccelerationPack {

    private Double east;
    private Double north;
    private Double up;
    private Double sigEast;
    private Double sigNorth;
    private Double sigUp;
    private Double rhoEastNorth;
    private Double rhoEastUp;
    private Double rhoNorthUp;

    /**
     * Constructor.
     *
     * @param east Acceleration along the East axis of the East-North-Up coordinate system.
     * @param north Acceleration along the North axis of the East-North-Up coordinate system.
     * @param up Acceleration along the Up axis of the East-North-Up coordinate system.
     */
    public AccelerationPack(Double east, Double north, Double up) {
        this(east, north, up, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param east Acceleration along the East axis of the East-North-Up coordinate system.
     * @param north Acceleration along the North axis of the East-North-Up coordinate system.
     * @param up Acceleration along the Up axis of the East-North-Up coordinate system.
     * @param sigEast Standard deviation along East axis.
     * @param sigNorth Standard deviation along North axis.
     * @param sigUp Standard deviation along Up axis.
     */
    public AccelerationPack(
            Double east, Double north, Double up, Double sigEast, Double sigNorth, Double sigUp) {
        this(east, north, up, sigEast, sigNorth, sigUp, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param east Acceleration along the East axis of the East-North-Up coordinate system.
     * @param north Acceleration along the North axis of the East-North-Up coordinate system.
     * @param up Acceleration along the Up axis of the East-North-Up coordinate system.
     * @param sigEast Standard deviation along East axis.
     * @param sigNorth Standard deviation along North axis.
     * @param sigUp Standard deviation along Up axis.
     * @param rhoEastNorth Correlation coefficient between the East and North components of error.
     * @param rhoEastUp Correlation coefficient between East and Up components of error.
     * @param rhoNorthUp Correlation coefficient between North and Up components of error.
     */
    public AccelerationPack(
            Double east,
            Double north,
            Double up,
            Double sigEast,
            Double sigNorth,
            Double sigUp,
            Double rhoEastNorth,
            Double rhoEastUp,
            Double rhoNorthUp) {
        this.east = east;
        this.north = north;
        this.up = up;
        this.sigEast = sigEast;
        this.sigNorth = sigNorth;
        this.sigUp = sigUp;
        this.rhoEastNorth = rhoEastNorth;
        this.rhoEastUp = rhoEastUp;
        this.rhoNorthUp = rhoNorthUp;
    }

    /**
     * Get the east component of the acceleration.
     *
     * @return Acceleration in metres per second per second.
     */
    public Double getEast() {
        return east;
    }

    /**
     * Set the east component of the acceleration.
     *
     * @param east Acceleration in metres per second per second.
     */
    public void setEast(Double east) {
        this.east = east;
    }

    /**
     * Get the north component of the acceleration.
     *
     * @return Acceleration in metres per second per second.
     */
    public Double getNorth() {
        return north;
    }

    /**
     * Set the north component of the acceleration.
     *
     * @param north Acceleration in metres per second per second.
     */
    public void setNorth(Double north) {
        this.north = north;
    }

    /**
     * Get the vertical component of acceleration
     *
     * @return Acceleration in metres per second per second (up positive).
     */
    public Double getUp() {
        return up;
    }

    /**
     * Set the vertical component of acceleration.
     *
     * @param up Acceleration in metres per second per second (up positive).
     */
    public void setUp(Double up) {
        this.up = up;
    }

    /**
     * Get the standard deviation for the acceleration - East axis.
     *
     * @return Standard deviation of the acceleration with respect to the ENU coordinate system East
     *     axis.
     */
    public Double getSigEast() {
        return sigEast;
    }

    /**
     * Set the standard deviation for the acceleration - East axis.
     *
     * @param sigEast Standard deviation of the acceleration with respect to the ENU coordinate
     *     system East axis.
     */
    public void setSigEast(Double sigEast) {
        this.sigEast = sigEast;
    }

    /**
     * Get the standard deviation for the acceleration - North axis.
     *
     * @return Standard deviation of the acceleration with respect to the ENU coordinate system
     *     North axis.
     */
    public Double getSigNorth() {
        return sigNorth;
    }

    /**
     * Set the standard deviation for the acceleration - North axis.
     *
     * @param sigNorth Standard deviation of the acceleration with respect to the ENU coordinate
     *     system North axis.
     */
    public void setSigNorth(Double sigNorth) {
        this.sigNorth = sigNorth;
    }

    /**
     * Get the standard deviation for the acceleration - Up axis.
     *
     * @return Standard deviation of the acceleration with respect to the ENU coordinate system Up
     *     axis.
     */
    public Double getSigUp() {
        return sigUp;
    }

    /**
     * Get the standard deviation for the acceleration - Up axis.
     *
     * @param sigUp Standard deviation of the acceleration with respect to the ENU coordinate system
     *     Up axis.
     */
    public void setSigUp(Double sigUp) {
        this.sigUp = sigUp;
    }

    /**
     * Get the correlation between the East and North axis parts of the acceleration error estimate.
     *
     * @return Correlation coefficient between the East and North components of error.
     */
    public Double getRhoEastNorth() {
        return rhoEastNorth;
    }

    /**
     * Set the correlation between the East and North axis parts of the acceleration error estimate.
     *
     * @param rhoEastNorth Correlation coefficient between the East and North components of error.
     */
    public void setRhoEastNorth(Double rhoEastNorth) {
        this.rhoEastNorth = rhoEastNorth;
    }

    /**
     * Get the correlation between the East and Up axis parts of the acceleration error estimate.
     *
     * @return Correlation coefficient between the East and Up components of error.
     */
    public Double getRhoEastUp() {
        return rhoEastUp;
    }

    /**
     * Set the correlation between the East and Up axis parts of the acceleration error estimate.
     *
     * @param rhoEastUp Correlation coefficient between the East and Up components of error.
     */
    public void setRhoEastUp(Double rhoEastUp) {
        this.rhoEastUp = rhoEastUp;
    }

    /**
     * Get the correlation between the North and Up axis parts of the acceleration error estimate.
     *
     * @return Correlation coefficient between the North and Up components of error.
     */
    public Double getRhoNorthUp() {
        return rhoNorthUp;
    }

    /**
     * Set the correlation between the North and Up axis parts of the acceleration error estimate.
     *
     * @param rhoNorthUp Correlation coefficient between the North and Up components of error.
     */
    public void setRhoNorthUp(Double rhoNorthUp) {
        this.rhoNorthUp = rhoNorthUp;
    }
}
