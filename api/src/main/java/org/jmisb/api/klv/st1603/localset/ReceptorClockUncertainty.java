package org.jmisb.api.klv.st1603.localset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Receptor Clock Uncertainty (ST 1603 Time Transfer Local Set Tag 9).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * The Receptor Clock Uncertainty is the estimated error of the receptor clock (i.e. standard
 * deviation of the measured time, see Motion Imagery Handbook Section 6). When this value is
 * provided, it must appear in the stream at least once every 30 seconds. When this value is not
 * included in the stream, it means the Receptor Clock Uncertainty is unknown. The units for this
 * measurement are the same as the units of the parent time.
 *
 * </blockquote>
 */
public class ReceptorClockUncertainty implements ITimeTransferValue {
    private final long uncertainty;
    private static final int MIN_VALUE = 0;

    /**
     * Create from value.
     *
     * @param uncertainty the standard deviation of the measured time (in units of the measured
     *     time)
     */
    public ReceptorClockUncertainty(long uncertainty) {
        if (uncertainty < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be non-negative");
        }
        this.uncertainty = uncertainty;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised (usually bad length)
     */
    public ReceptorClockUncertainty(byte[] bytes) throws KlvParseException {
        try {
            uncertainty = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Unable to deserialise Receptor Clock Uncertainty: " + ex.getMessage());
        }
    }

    /**
     * Get the receptor clock uncertainty.
     *
     * @return the standard deviation of the measured time (in units of the measured time)
     */
    public long getUncertainty() {
        return uncertainty;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(uncertainty);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", uncertainty);
    }

    @Override
    public final String getDisplayName() {
        return "Receptor Clock Uncertainty";
    }
}
