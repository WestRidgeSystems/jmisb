package org.jmisb.api.klv.st0805;

/**
 * Represents a Sensor Point of Interest (SPI) CoT message.
 *
 * <p>Every SPI message should be linked to a "parent" {@link PlatformPosition} UID/type identifying
 * the platform producing the sensor feed.
 */
public class SensorPointOfInterest extends CotMessage {
    public String linkType;
    public String linkUid;

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
     * Get the link UID (unique ID of the producing {@link PlatformPosition}).
     *
     * @return link UID
     */
    public String getLinkUid() {
        return linkUid;
    }

    /**
     * Set the link UID (unique ID of the producing {@link PlatformPosition}).
     *
     * @param linkUid link UID
     */
    public void setLinkUid(String linkUid) {
        this.linkUid = linkUid;
    }
}
