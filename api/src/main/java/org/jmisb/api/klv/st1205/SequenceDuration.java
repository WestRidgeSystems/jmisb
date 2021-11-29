package org.jmisb.api.klv.st1205;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Sequence Duration (ST 1205 Item 2).
 *
 * <p>The number of motion imagery frames in a calibration test sequence duration. The maximum value
 * is 65535.
 */
public class SequenceDuration implements ICalibrationMetadataValue {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 65535;
    private static final int REQUIRED_BYTE_ARRAY_LENGTH = 2;
    private final int value;

    /**
     * Create from value.
     *
     * @param frames the number of frames in the calibration sequence, in the range [0, 65535].
     */
    public SequenceDuration(int frames) {
        if ((frames < MIN_VALUE) || (frames > MAX_VALUE)) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s value must be in range [%d, %d]",
                            this.getDisplayName(), MIN_VALUE, MAX_VALUE));
        }
        this.value = frames;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes number of columns, encoded as 2 byte unsigned integer
     * @throws KlvParseException if the byte array is not the correct length
     */
    public SequenceDuration(byte[] bytes) throws KlvParseException {
        if (bytes.length != REQUIRED_BYTE_ARRAY_LENGTH) {
            throw new KlvParseException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        this.value = PrimitiveConverter.toUint16(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Sequence Duration";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", value);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(value);
    }

    /**
     * The number of frames.
     *
     * @return the number of frames in the sequence.
     */
    public int getDurationInFrames() {
        return this.value;
    }
}
