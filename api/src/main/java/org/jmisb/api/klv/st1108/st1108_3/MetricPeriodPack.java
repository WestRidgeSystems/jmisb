package org.jmisb.api.klv.st1108.st1108_3;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Metric Period Pack.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Metric Period Pack documents the time period over which a calculated metric applies indicated
 * by a beginning image Frame timestamp (i.e. Start Time) and an ending period image Frame time
 * offset (i.e. Time Offset). This Local Set item is mandatory and encoded as a defined length pack
 * (DLP) where the beginning Frame timestamp is a Precision Time Stamp as specified in MISB ST 0603
 * and the ending Frame timestamp calculated as a positive offset added to the beginning Frame
 * timestamp.
 *
 * </blockquote>
 */
public class MetricPeriodPack implements IInterpretabilityQualityMetadataValue {

    private ST0603TimeStamp startTime;
    private long timeOffset;
    private static final int REQUIRED_BYTES_TIMESTAMP = 8;
    private static final int REQUIRED_BYTES_OFFSET = 4;
    private static final int REQUIRED_BYTES = REQUIRED_BYTES_TIMESTAMP + REQUIRED_BYTES_OFFSET;

    /**
     * Create from value.
     *
     * @param start the start time for the metrics
     * @param offset the time offset for the last image
     */
    public MetricPeriodPack(final ST0603TimeStamp start, final long offset) {
        this.startTime = start;
        this.timeOffset = offset;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 12 bytes.
     */
    public MetricPeriodPack(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is 12 byte defined length pack");
        }
        long microseconds =
                PrimitiveConverter.variableBytesToUint64(bytes, 0, REQUIRED_BYTES_TIMESTAMP);
        this.startTime = new ST0603TimeStamp(microseconds);
        this.timeOffset = PrimitiveConverter.toUint32(bytes, REQUIRED_BYTES_TIMESTAMP);
    }

    /**
     * Get the value as a byte array.
     *
     * <p>This is the V part of a TLV encoding.
     *
     * @return the value of this pack, as a byte array.
     */
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.append(startTime.getBytesFull());
        arrayBuilder.appendAsUInt32Primitive(timeOffset);
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%s, %d", startTime.getDisplayableValue(), timeOffset);
    }

    @Override
    public final String getDisplayName() {
        return "Metric Period";
    }

    /**
     * Get the start time for this Metric Period Pack.
     *
     * @return the start time as a timestamp
     */
    public ST0603TimeStamp getStartTime() {
        return startTime;
    }

    /**
     * Get the time offset for this Metric Period Pack.
     *
     * @return the time offset in microseconds.
     */
    public long getTimeOffset() {
        return timeOffset;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.MetricPeriodPack.getIdentifier());
        byte[] valueBytes = getBytes();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
