package org.jmisb.api.klv.st0805;

/** Represents a Sensor Point of Interest (SPI) CoT message */
public class SensorPointOfInterest extends CotMessage {
    public String linkType;
    public String linkUid;

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkUid() {
        return linkUid;
    }

    public void setLinkUid(String linkUid) {
        this.linkUid = linkUid;
    }
}
