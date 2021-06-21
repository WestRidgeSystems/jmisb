package org.jmisb.api.klv.st1108.st1108_3.metric;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Metric Time.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * Metric Time mandatory item is the time of metric assessment, which with respect to the Metric
 * (Period) pack may be a future value. Metric Time is a microsecond Precision Time Stamp as defined
 * in MISB ST 0603.
 *
 * </blockquote>
 */
public class MetricTime implements IMetricLocalSetValue {

    private ST0603TimeStamp time;
    private static final int REQUIRED_BYTES = 8;

    /**
     * Create from value.
     *
     * @param start the start time for the metrics
     */
    public MetricTime(final ST0603TimeStamp start) {
        this.time = start;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 8 bytes.
     */
    public MetricTime(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is 8 byte");
        }
        time = new ST0603TimeStamp(bytes);
    }

    @Override
    public byte[] getBytes() {
        return time.getBytesFull();
    }

    @Override
    public String getDisplayableValue() {
        return time.getDisplayableValue();
    }

    @Override
    public final String getDisplayName() {
        return "Metric Time";
    }

    /**
     * Get the time for this Metric Time.
     *
     * @return the time as a timestamp
     */
    public ST0603TimeStamp getTime() {
        return time;
    }
}
