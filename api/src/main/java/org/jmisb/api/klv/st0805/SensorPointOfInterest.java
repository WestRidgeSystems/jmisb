package org.jmisb.api.klv.st0805;

import java.time.Clock;

/**
 * Represents a Sensor Point of Interest (SPI) CoT message.
 *
 * <p>Every SPI message should be linked to a "parent" {@link PlatformPosition} UID/type identifying
 * the platform producing the sensor feed.
 */
public class SensorPointOfInterest extends CotMessage {

    private Link link;

    SensorPointOfInterest(Clock clock) {
        super(clock);
        setType("b-m-p-s-p-i");
    }

    /**
     * Get the link detail for this CoT message.
     *
     * @return the link, or null if not set.
     */
    public Link getLink() {
        return link;
    }

    /**
     * Set the link detail for this CoT message.
     *
     * @param link the link to set.
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     * Write the SensorPointOfInterest out as XML.
     *
     * @return string serialization of the values.
     */
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        addXmlHeader(sb);
        writeAsXML(sb);
        return sb.toString();
    }

    @Override
    void writeAsXML(StringBuilder sb) {
        addEventLevelAttributesToXML(sb);
        closeEventStartInXML(sb);
        if (getPoint() != null) {
            getPoint().writeAsXML(sb);
        }
        writeStartElement(sb, "detail");
        writeFlowTags(sb);
        writeLink(sb);
        writeEndElement(sb, "detail");
        addEventEndToXML(sb);
    }

    protected void writeLink(StringBuilder sb) {
        if (getLink() != null) {
            getLink().writeAsXML(sb);
        }
    }
}
