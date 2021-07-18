package org.jmisb.api.klv.st0805;

/**
 * Cursor On Target (CoT) Point.
 *
 * <p>This is part of the core event schema, but is managed separately as a complex element
 * representation within that event.
 *
 * <p>CoT uses the WGS-84 ellipsoid as a reference. Angles are in degrees (positive north / east).
 * Altitudes and size / distance measures are in meters.
 */
public class CotPoint extends CotElement {
    private Double lat;
    private Double lon;
    private Double hae;
    private double ce = 9_999_999.0;
    private double le = 9_999_999.0;

    /**
     * Get the latitude.
     *
     * @return latitude, or null if not set
     */
    public Double getLat() {
        return lat;
    }

    /**
     * Set the latitude.
     *
     * @param pointLat The latitude
     */
    public void setLat(double pointLat) {
        this.lat = pointLat;
    }

    /**
     * Get the longitude.
     *
     * @return longitude, or null if not set
     */
    public Double getLon() {
        return lon;
    }

    /**
     * Set the longitude.
     *
     * @param pointLon longitude
     */
    public void setLon(double pointLon) {
        this.lon = pointLon;
    }

    /**
     * Get the altitude (height above ellipsoid).
     *
     * @return altitude in meters HAE, or null if not set
     */
    public Double getHae() {
        return hae;
    }

    /**
     * Set the altitude (height above ellipsoid).
     *
     * @param pointHae altitude in meters HAE
     */
    public void setHae(double pointHae) {
        this.hae = pointHae;
    }

    /**
     * Get the circular error.
     *
     * @return circular error
     */
    public double getCe() {
        return ce;
    }

    /**
     * Set the circular error.
     *
     * @param pointCe circular error
     */
    public void setCe(double pointCe) {
        this.ce = pointCe;
    }

    /**
     * Get the lateral error.
     *
     * @return lateral error
     */
    public double getLe() {
        return le;
    }

    /**
     * Set the lateral error.
     *
     * @param pointLe lateral error
     */
    public void setLe(double pointLe) {
        this.le = pointLe;
    }

    /**
     * Write out the content of this CoT Point as XML.
     *
     * <p>The point will only be written if it would be valid (i.e. all required values are
     * available).
     *
     * @param sb the string builder to write to.
     */
    @Override
    void writeAsXML(StringBuilder sb) {
        if ((getLat() != null) && (getLon() != null) && (getHae() != null)) {
            sb.append("<point");
            writeAttribute(sb, "lat", getLat());
            writeAttribute(sb, "lon", getLon());
            writeAttribute(sb, "hae", getHae());
            writeAttribute(sb, "ce", String.format("%.1f", getCe()));
            writeAttribute(sb, "le", String.format("%.1f", getLe()));
            sb.append("/>");
        }
    }
}
