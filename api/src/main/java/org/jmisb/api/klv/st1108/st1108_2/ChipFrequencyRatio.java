package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Chip Frequency Ratio (ST 1108.2 Tag 13).
 *
 * <p>The Chip Frequency Ratio is computed by taking the ratio of high-pass to low-pass energy.
 *
 * <p>See ST 1108.2 Section 7.13 and RP 1203.3.
 */
public class ChipFrequencyRatio implements IInterpretabilityQualityMetadataValue {
    private int ratio;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 65535;
    private static final int REQUIRED_BYTES = 2;

    /**
     * Create from value.
     *
     * @param ratio The chip frequency ratio.
     */
    public ChipFrequencyRatio(int ratio) {
        if (ratio < MIN_VALUE || ratio > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.ratio = ratio;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 2 bytes.
     */
    public ChipFrequencyRatio(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is two byte unsigned integer");
        }
        ratio = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the frequency ratio.
     *
     * @return The ratio as an unscaled integer value
     */
    public int getRatio() {
        return ratio;
    }

    @Override
    public String getDisplayableValue() {
        // TODO: this should possibly be scaled, but ST 1108.2 doesn't say how.
        // Will ask MISB if they have any ideas.
        return String.format("%d", ratio);
    }

    @Override
    public final String getDisplayName() {
        return "Chip Frequency Ratio";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipFrequencyRatio.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint16ToBytes(ratio);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
