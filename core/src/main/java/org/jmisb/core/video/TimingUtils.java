package org.jmisb.core.video;

/** Utility methods for timing */
public class TimingUtils {
    private TimingUtils() {}

    /**
     * Wait for a short period, ignoring thread interruptions
     *
     * @param millis Time to wait, in ms
     */
    public static void shortWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // don't care
        }
    }
}
