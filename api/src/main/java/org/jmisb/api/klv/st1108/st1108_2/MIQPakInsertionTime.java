package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * MIQ Pak Insertion Time.
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The IQ Local data set Creation time is the current time when the IQ key value(s), chips, or
 * features are inserted into the stream.
 *
 * </blockquote>
 */
public class MIQPakInsertionTime implements IInterpretabilityQualityMetadataValue {

    private ST0603TimeStamp time;
    private static final int REQUIRED_BYTES = 8;

    /**
     * Create from value.
     *
     * @param creationTime the insertion time for the stream insertion
     */
    public MIQPakInsertionTime(final ST0603TimeStamp creationTime) {
        this.time = creationTime;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 8 bytes.
     */
    public MIQPakInsertionTime(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(getDisplayName() + " encoding is 8 byte");
        }
        time = new ST0603TimeStamp(bytes);
    }

    @Override
    public String getDisplayableValue() {
        return time.getDisplayableValue();
    }

    @Override
    public final String getDisplayName() {
        return "Insertion Time";
    }

    /**
     * Get the time.
     *
     * @return the time as a timestamp
     */
    public ST0603TimeStamp getTime() {
        return time;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.MIQPakInsertionTime.getIdentifier());
        byte[] valueBytes = time.getBytesFull();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
