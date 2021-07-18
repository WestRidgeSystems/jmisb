package org.jmisb.api.klv.st0805;

import java.time.Clock;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/** A Cursor-on-Target (CoT) message. */
public abstract class CotMessage extends CotElement {
    private static final String COT_VERSION = "2.0";
    private static final DateTimeFormatter DT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private CotPoint point;
    private String type;
    private String uid = "jmisb";
    private Long time;
    private Long start;
    private Long stale;
    private String how = "m";
    private final FlowTags flowTags = new FlowTags();

    /**
     * Constructor.
     *
     * @param clock reference clock for the message
     */
    public CotMessage(Clock clock) {
        ZonedDateTime now = clock.instant().atZone(ZoneOffset.UTC);
        flowTags.addFlowTag("ST0601CoT", now);
    }

    /**
     * Get the point location associated with this message.
     *
     * @return the point location, or null if not set
     */
    public CotPoint getPoint() {
        return point;
    }

    /**
     * Set the point location associated with this message.
     *
     * @param point the point location
     */
    public void setPoint(CotPoint point) {
        this.point = point;
    }

    /**
     * Get the CoT type.
     *
     * @return CoT message type
     */
    public final String getType() {
        return type;
    }

    /**
     * Set the CoT type.
     *
     * @param type CoT message type
     */
    public final void setType(String type) {
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
     * @return time the message was generated, or null if not set
     */
    public Long getTime() {
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
     * @return time the message becomes valid, or null if not set
     */
    public Long getStart() {
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
     * @return time the message becomes invalid, or null if not set
     */
    public Long getStale() {
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
    public FlowTags getFlowTags() {
        return flowTags;
    }

    protected void closeEventStartInXML(StringBuilder sb) {
        sb.append(">");
    }

    protected void addEventLevelAttributesToXML(StringBuilder sb) {
        sb.append("<event");
        writeAttribute(sb, "version", COT_VERSION);
        writeAttribute(sb, "type", getType());
        writeAttribute(sb, "uid", getUid());
        if (getTime() != null) {
            writeAttribute(
                    sb, "time", new ST0603TimeStamp(getTime()).getDateTime().format(DT_FORMATTER));
        }
        if (getStart() != null) {
            writeAttribute(
                    sb,
                    "start",
                    new ST0603TimeStamp(getStart()).getDateTime().format(DT_FORMATTER));
        }
        if (getStale() != null) {
            writeAttribute(
                    sb,
                    "stale",
                    new ST0603TimeStamp(getStale()).getDateTime().format(DT_FORMATTER));
        }
        writeAttribute(sb, "how", getHow());
    }

    protected void addEventEndToXML(StringBuilder sb) {
        writeEndElement(sb, "event");
    }

    protected void addXmlHeader(StringBuilder sb) {
        sb.append("<?xml version='1.0' standalone='yes'?>");
    }

    protected void writeFlowTags(StringBuilder sb) {
        getFlowTags().writeAsXML(sb);
    }
}
