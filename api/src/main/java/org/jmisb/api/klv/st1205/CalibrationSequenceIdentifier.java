package org.jmisb.api.klv.st1205;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Calibration Sequence Identifier (ST 1205 Item 4).
 *
 * <p>The Calibration Sequence Identifier is the last three digits in the filename of the sequence.
 * It is anticipated that various test sequences will span the range of test requirements.
 */
public class CalibrationSequenceIdentifier implements ICalibrationMetadataValue {
    private final int value;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * @param identifier The calibration sequence identifier as an integer value
     */
    public CalibrationSequenceIdentifier(int identifier) {
        if (identifier < MIN_VALUE || identifier > MAX_VALUE) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s value must be in range [%d, %d]",
                            this.getDisplayName(), MIN_VALUE, MAX_VALUE));
        }
        this.value = identifier;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte
     * @throws KlvParseException if the byte array is not the correct length
     */
    public CalibrationSequenceIdentifier(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        value = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the sequence identifier value.
     *
     * @return The sequence identifier number
     */
    public int getSequenceIdentifier() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes((short) value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%03d", this.value);
    }

    @Override
    public final String getDisplayName() {
        return "Calibration Sequence Identifier";
    }
}
