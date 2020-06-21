package org.jmisb.api.klv.st0805;

/** A Cursor-on-Target (CoT) message */
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

    public CotMessage() {
        this.flowTags = System.currentTimeMillis();
    }

    public double getPointLat() {
        return pointLat;
    }

    public void setPointLat(double pointLat) {
        this.pointLat = pointLat;
    }

    public double getPointLon() {
        return pointLon;
    }

    public void setPointLon(double pointLon) {
        this.pointLon = pointLon;
    }

    public double getPointHae() {
        return pointHae;
    }

    public void setPointHae(double pointHae) {
        this.pointHae = pointHae;
    }

    public double getPointCe() {
        return pointCe;
    }

    public void setPointCe(double pointCe) {
        this.pointCe = pointCe;
    }

    public double getPointLe() {
        return pointLe;
    }

    public void setPointLe(double pointLe) {
        this.pointLe = pointLe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStale() {
        return stale;
    }

    public void setStale(long stale) {
        this.stale = stale;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public long getFlowTags() {
        return flowTags;
    }
}
