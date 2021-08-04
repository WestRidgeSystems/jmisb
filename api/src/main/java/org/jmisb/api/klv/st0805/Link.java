package org.jmisb.api.klv.st0805;

/**
 * A Cursor on Target Link instance.
 *
 * <p>This is one of the things that can be included in the {@code detail} part of an Event.
 */
public class Link extends CotElement {
    public String linkType;
    public String linkUid;
    public String linkRelation;

    /**
     * Get the link type (CoT type of the producing {@link PlatformPosition}).
     *
     * @return link type
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * Set the link type (CoT type of the producing {@link PlatformPosition}).
     *
     * @param linkType link type
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    /**
     * Get the link UID.
     *
     * <p>For Sensor Point of Interest, this is the unique ID of the producing {@link
     * PlatformPosition}.
     *
     * @return link UID
     */
    public String getLinkUid() {
        return linkUid;
    }

    /**
     * Set the link UID.
     *
     * <p>For Sensor Point of Interest, this is the unique ID of the producing {@link
     * PlatformPosition}.
     *
     * @param linkUid link UID
     */
    public void setLinkUid(String linkUid) {
        this.linkUid = linkUid;
    }

    /**
     * Get the link relation.
     *
     * <p>This is the relationship from this message to the link target, typically {@code "p-p"} for
     * parent producer for SensorPointOfInterest.
     *
     * @return the link relation
     */
    public String getLinkRelation() {
        return linkRelation;
    }

    /**
     * Set the link relation.
     *
     * <p>This is the relationship from this message to the link target, typically {@code "p-p"} for
     * parent producer for SensorPointOfInterest.
     *
     * @param relation the link relation
     */
    public void setLinkRelation(String relation) {
        this.linkRelation = relation;
    }

    @Override
    void writeAsXML(StringBuilder sb) {
        if ((getLinkRelation() != null) && (getLinkType() != null) && (getLinkUid() != null)) {
            sb.append("<link");
            writeAttribute(sb, "relation", getLinkRelation());
            writeAttribute(sb, "type", getLinkType());
            writeAttribute(sb, "uid", getLinkUid());
            sb.append("/>");
        }
    }
}
