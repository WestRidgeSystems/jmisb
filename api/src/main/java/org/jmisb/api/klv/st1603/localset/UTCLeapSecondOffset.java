package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * UTC Leap Second Offset (ST 1603 Time Transfer Local Set Tag 2).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The UTC Leap Second Offset is an integer number of SI seconds used to convert a timestamp to
 * Universal Coordinated Time (UTC), and accounts for leap seconds since the MISP Epoch as defined
 * in the Motion Imagery Handbook. For GPS systems, the leap second difference between the MISP
 * Epoch and the GPS Epoch is 19-8=11. When GPS reports a leap second offset (relative to the GPS
 * Epoch) the value of 11 is added to make it relative to MISP Epoch.
 *
 * <p>The UTC Leap Second Offset is required to be included in a Motion Imagery stream along with
 * time reports; however, this value does not need to be present with every time report. The UTC
 * Leap Second Offset value usually changes at most once a year. During regular operation, the UTC
 * Leap Second Offset is delivered in the Time Transfer Local Set as infrequently as once every 30
 * seconds.
 *
 * <p>Some systems, such as GPS, announce months in advance when the next leap second will be added.
 * When a leap second change is going to occur within the next 30 seconds, the UTC Leap Second
 * Offset is included with every reported time. During and after the leap second change, the UTC
 * Leap Second Offset report continues to be included with every reported time for 30 seconds after
 * the change. After the update, the UTC Leap Second Offset reports at its nominal rate, but no more
 * than 30 seconds apart.
 *
 * </blockquote>
 */
public class UTCLeapSecondOffset implements ITimeTransferValue {
    private final long offset;

    /**
     * Create from value.
     *
     * @param offset The leap second offset.
     */
    public UTCLeapSecondOffset(long offset) {
        this.offset = offset;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public UTCLeapSecondOffset(byte[] bytes) throws KlvParseException {
        try {
            offset = PrimitiveConverter.variableBytesToInt64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise UTC Leap Second Offset: " + ex.getMessage());
        }
    }

    /**
     * Get the leap second offset.
     *
     * @return The leap second offset (in seconds)
     */
    public long getLeapSecondOffset() {
        return offset;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.int64ToVariableBytes(offset);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d seconds", offset);
    }

    @Override
    public final String getDisplayName() {
        return "UTC Leap Second Offset";
    }
}
