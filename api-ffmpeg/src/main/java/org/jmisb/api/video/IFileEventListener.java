package org.jmisb.api.video;

/** Interface for file event notifications. */
public interface IFileEventListener {
    /** Notification that end-of-file has been reached. */
    void onEndOfFile();
}
