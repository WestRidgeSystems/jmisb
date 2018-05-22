package org.jmisb.api.video;

/**
 * Interface for metadata arrival notifications
 */
public interface MetadataListener
{
    /**
     * Notification that new metadata has been received
     *
     * @param metadataFrame The metadata frame
     */
    void onFrameReceived(MetadataFrame metadataFrame);
}
