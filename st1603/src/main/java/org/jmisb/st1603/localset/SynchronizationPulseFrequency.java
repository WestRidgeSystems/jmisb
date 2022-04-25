package org.jmisb.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Synchronization Pulse Frequency (ST 1603 Time Transfer Local Set Tag 4).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The Synchronization Pulse Frequency specifies the periodicity of the pulse transmitted by a
 * reference time source. The unit of measure is Pulse per [SI] Second (PPS) or Hertz (Hz). This
 * value is not required, and has a default value of one (1.0 PPS). When this value is provided, it
 * must appear in the stream at least once every 30 seconds.
 *
 * </blockquote>
 */
public class SynchronizationPulseFrequency implements ITimeTransferValue {
    private final double frequency;

    /**
     * Create from value.
     *
     * @param freq The synchronization pulse frequency in pulses per second (or Hz).
     */
    public SynchronizationPulseFrequency(double freq) {
        this.frequency = freq;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the float value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public SynchronizationPulseFrequency(byte[] bytes) throws KlvParseException {
        try {
            frequency = PrimitiveConverter.toFloat64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise Synchronization Pulse Frequency: " + ex.getMessage());
        }
    }

    /**
     * Get the synchronization pulse frequency.
     *
     * @return synchronization frequency in pulses per second or Hz.
     */
    public double getSynchronizationFrequency() {
        return frequency;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float64ToBytes(frequency);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.4f Hz", frequency);
    }

    @Override
    public final String getDisplayName() {
        return "Synchronization Pulse Frequency";
    }
}
