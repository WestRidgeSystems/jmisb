package org.jmisb.api.klv.st0805;

import java.time.Clock;

/** Represents a Platform Position CoT message. */
public class PlatformPosition extends CotMessage {

    public CotSensor sensor;

    PlatformPosition(Clock clock) {
        super(clock);
        setType("a-f");
    }

    /**
     * Get the Sensor detail.
     *
     * @return sensor detail, or null if not set
     */
    public CotSensor getSensor() {
        return sensor;
    }

    /**
     * Set the Sensor detail.
     *
     * @param sensor the sensor detail
     */
    public void setSensor(CotSensor sensor) {
        this.sensor = sensor;
    }

    /**
     * Write the PlatformPosition out as XML.
     *
     * @return string serialization of the values.
     */
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        addXmlHeader(sb);
        this.writeAsXML(sb);
        return sb.toString();
    }

    @Override
    public void writeAsXML(StringBuilder sb) {
        addEventLevelAttributesToXML(sb);
        closeEventStartInXML(sb);
        if (getPoint() != null) {
            getPoint().writeAsXML(sb);
        }
        writeStartElement(sb, "detail");
        writeFlowTags(sb);
        if (getSensor() != null) {
            getSensor().writeAsXML(sb);
        }
        writeEndElement(sb, "detail");
        addEventEndToXML(sb);
    }
}
