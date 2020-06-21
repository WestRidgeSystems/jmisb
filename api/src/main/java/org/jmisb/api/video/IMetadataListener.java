package org.jmisb.api.video;

/** Interface for metadata arrival notifications */
public interface IMetadataListener {
    /**
     * Notification that new metadata has been received
     *
     * @param metadataFrame The metadata frame
     */
    void onMetadataReceived(MetadataFrame metadataFrame);
}
