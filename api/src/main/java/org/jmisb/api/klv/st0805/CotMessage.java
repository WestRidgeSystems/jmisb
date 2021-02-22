package org.jmisb.api.klv.st0805;

/** A Cursor-on-Target (CoT) message. */
public class CotMessage {
    private double pointLat;
    private double pointLon;
    private double pointHae;
    private double pointCe;
    private double pointLe;
    private String type;
    private String uid;
    private long time;
    private long start;
    private long stale;
    private String how;
    private final long flowTags;

    /** Constructor. */
    public CotMessage() {
        this.flowTags = System.currentTimeMillis();
    }

    /**
     * Get the latitude.
     *
     * @return latitude
     */
    public double getPointLat() {
        return pointLat;
    }

    /**
     * Set the latitude.
     *
     * @param pointLat The latitude
     */
    public void setPointLat(double pointLat) {
        this.pointLat = pointLat;
    }

    /**
     * Get the longitude.
     *
     * @return longitude
     */
    public double getPointLon() {
        return pointLon;
    }

    /**
     * Set the longitude.
     *
     * @param pointLon longitude
     */
    public void setPointLon(double pointLon) {
        this.pointLon = pointLon;
    }

    /**
     * Get the altitude (height above ellipsoid).
     *
     * @return altitude in meters HAE
     */
    public double getPointHae() {
        return pointHae;
    }

    /**
     * Set the altitude (height above ellipsoid).
     *
     * @param pointHae altitude in meters HAE
     */
    public void setPointHae(double pointHae) {
        this.pointHae = pointHae;
    }

    /**
     * Get the circular error.
     *
     * @return circular error
     */
    public double getPointCe() {
        return pointCe;
    }

    /**
     * Set the circular error.
     *
     * @param pointCe circular error
     */
    public void setPointCe(double pointCe) {
        this.pointCe = pointCe;
    }

    /**
     * Get the lateral error.
     *
     * @return lateral error
     */
    public double getPointLe() {
        return pointLe;
    }

    /**
     * Set the lateral error.
     *
     * @param pointLe lateral error
     */
    public void setPointLe(double pointLe) {
        this.pointLe = pointLe;
    }

    /**
     * Get the CoT type.
     *
     * @return CoT message type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the CoT type.
     *
     * @param type CoT message type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the unique ID.
     *
     * @return the unique ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set the unique ID.
     *
     * @param uid The unique ID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Get the time.
     *
     * @return time the message was generated
     */
    public long getTime() {
        return time;
    }

    /**
     * Set the time.
     *
     * @param time time the message was generated
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Get the start time.
     *
     * @return time the message becomes valid
     */
    public long getStart() {
        return start;
    }

    /**
     * Set the start time.
     *
     * @param start time the message becomes valid
     */
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * Get the stale time.
     *
     * @return time the message becomes invalid
     */
    public long getStale() {
        return stale;
    }

    /**
     * Set the stale time.
     *
     * @param stale time the message becomes invalid.
     */
    public void setStale(long stale) {
        this.stale = stale;
    }

    /**
     * Get the how string.
     *
     * @return how string
     */
    public String getHow() {
        return how;
    }

    /**
     * Set the how string.
     *
     * @param how string
     */
    public void setHow(String how) {
        this.how = how;
    }

    /**
     * Get the flow tags.
     *
     * @return flow tags
     */
    public long getFlowTags() {
        return flowTags;
    }
}
