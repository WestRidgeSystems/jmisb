package org.jmisb.api.klv.st1108.st1108_3;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Stream Bitrate.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * The Stream Bitrate mandatory item is the measured or known bitrate of the stream at the metric
 * calculation time. Stream Bitrate is a 16-bit unsigned integer measuring kilobits per second. For
 * example, 2200 Kbits/sec represents a stream bit rate of 2.2 Mbits/sec.
 *
 * </blockquote>
 */
public class StreamBitrate implements IInterpretabilityQualityMetadataValue {
    private int bitrate;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 65535;
    private static final int REQUIRED_BYTES = 2;

    /**
     * Create from value.
     *
     * @param rate The bit rate in kilobits per second.
     */
    public StreamBitrate(int rate) {
        if (rate < MIN_VALUE || rate > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.bitrate = rate;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2 bytes.
     */
    public StreamBitrate(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        bitrate = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the bit rate.
     *
     * @return The bit rate in kilobits per second.
     */
    public int getBitrate() {
        return bitrate;
    }

    @Override
    public String getDisplayableValue() {
        if (bitrate < 1000) {
            return bitrate + " Kbits/sec";
        } else {
            return String.format("%.3f Mbits/sec", bitrate / 1000.0);
        }
    }

    @Override
    public final String getDisplayName() {
        return "Stream Bitrate";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.StreamBitrate.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint16ToBytes(bitrate);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
