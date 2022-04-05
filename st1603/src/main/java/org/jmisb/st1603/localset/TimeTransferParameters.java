package org.jmisb.st1603.localset;

import java.util.EnumSet;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;

/**
 * Time Transfer Parameters (ST 1603 Time Transfer Local Set Tag 3).
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>Time Transfer Parameters is a binary value combining three items: The Reference Source Status,
 * Correction Method, and Time Transfer Method.
 *
 * <p>The Time Transfer Parameters value is not required for every time report, but it must appear
 * in a Motion Imagery stream at least once every 30 seconds.
 *
 * </blockquote>
 */
public class TimeTransferParameters implements ITimeTransferValue, INestedKlvValue {

    private static final byte REFERENCE_SOURCE_BITMASK = 0b00000011;
    private static final byte CORRECTION_METHOD_BITMASK = 0b00001100;
    private static final int CORRECTION_METHOD_BITSHIFT = 2;
    private static final byte TIME_TRANSFER_METHOD_BITMASK = (byte) 0b11110000;
    private static final int TIME_TRANSFER_METHOD_BITSHIFT = 4;

    private final ReferenceSource referenceSource;
    private final CorrectionMethod correctionMethod;
    private final TimeTransferMethod timeTransferMethod;

    /**
     * Create from value.
     *
     * @param referenceSource the reference source status
     * @param correctionMethod the correction method in use
     * @param timeTransferMethod the time transfer method
     */
    public TimeTransferParameters(
            final ReferenceSource referenceSource,
            final CorrectionMethod correctionMethod,
            final TimeTransferMethod timeTransferMethod) {
        this.referenceSource = referenceSource;
        this.correctionMethod = correctionMethod;
        this.timeTransferMethod = timeTransferMethod;
    }

    /**
     * Create from encoded byte array.
     *
     * <p>The Time Transfer Parameters is one-byte long but could become larger in future versions
     * of ST 1603. The least two significant bits (i.e. bit 0 and bit 1) indicate the Reference
     * Source; the next two significant bits (i.e. bit 2 and bit 3) indicate the Correction Method;
     * the next four significant bits (bit 4, 5, 6 and 7) indicate the Time Transfer Method.
     *
     * <p>When future versions add additional bytes, they will be added as more significant bytes,
     * where the current byte will be the Least Significant Byte (LSB).
     *
     * @param bytes Byte array encoding of the unsigned integer value (variable number of bytes).
     * @throws KlvParseException if the encoded bytes could not be deserialised
     */
    public TimeTransferParameters(byte[] bytes) throws KlvParseException {
        if (bytes.length == 0) {
            throw new KlvParseException(
                    "Cannot initialise Time Transfer Parameters from zero length array");
        }
        int leastSignificantByteOffset = bytes.length - 1;
        byte parameters = bytes[leastSignificantByteOffset];
        this.referenceSource = ReferenceSource.lookupValue(parameters & REFERENCE_SOURCE_BITMASK);
        this.correctionMethod =
                CorrectionMethod.lookupValue(
                        (parameters & CORRECTION_METHOD_BITMASK) >> CORRECTION_METHOD_BITSHIFT);
        this.timeTransferMethod =
                TimeTransferMethod.lookupValue(
                        (parameters & TIME_TRANSFER_METHOD_BITMASK)
                                >> TIME_TRANSFER_METHOD_BITSHIFT);
    }

    /**
     * Get the reference source status part of the time transfer parameters.
     *
     * @return reference source status as an enumerated value
     */
    public ReferenceSource getReferenceSource() {
        return referenceSource;
    }

    /**
     * Get the correction method part of the time transfer parameters.
     *
     * @return correction method as an enumerated value.
     */
    public CorrectionMethod getCorrectionMethod() {
        return correctionMethod;
    }

    /**
     * Get the time transfer method part of the time transfer parameters.
     *
     * @return time transfer method as an enumerated value.
     */
    public TimeTransferMethod getTimeTransferMethod() {
        return timeTransferMethod;
    }

    @Override
    public byte[] getBytes() {
        int value =
                (timeTransferMethod.getValue() << TIME_TRANSFER_METHOD_BITSHIFT)
                        + (correctionMethod.getValue() << CORRECTION_METHOD_BITSHIFT)
                        + referenceSource.getValue();
        return new byte[] {(byte) (value & 0xFF)};
    }

    @Override
    public String getDisplayableValue() {
        return "Parameters";
    }

    @Override
    public final String getDisplayName() {
        return "Time Transfer Parameters";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return getField((TimeTransferParametersKey) tag);
    }

    /**
     * Get the time transfer parameter value for a given key.
     *
     * <p>This is used for the nested value implementation.
     *
     * @param key the key to use to look up the parameter value
     * @return the corresponding parameter value.
     */
    public IKlvValue getField(TimeTransferParametersKey key) {
        switch (key) {
            case ReferenceSource:
                return getReferenceSource();
            case CorrectionMethod:
                return getCorrectionMethod();
            case TimeTransferMethod:
                return getTimeTransferMethod();
            default:
                throw new AssertionError(key.name());
        }
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return EnumSet.allOf(TimeTransferParametersKey.class);
    }
}
