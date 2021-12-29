package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * PSNR Coefficient Identifier (ST 1108.2 Tag 5).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>MISB RP 1203 estimates the PSNR without a reference by applying a coefficients to a set of
 * image features. The coefficients are updated as additional test materials are analyzed. This key
 * designates the coefficient set number. It may be in the future, that each system, or scene type
 * may have its own coefficient set, e.g. one set for snow, another for urban, etc.
 *
 * </blockquote>
 */
public class PSNRCoefficientIdentifier implements IInterpretabilityQualityMetadataValue {
    private int identifier;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * @param coefficientIdentifier The PSNR coefficient identifier.
     */
    public PSNRCoefficientIdentifier(int coefficientIdentifier) {
        if (coefficientIdentifier < MIN_VALUE || coefficientIdentifier > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,255]");
        }
        this.identifier = coefficientIdentifier;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public PSNRCoefficientIdentifier(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        identifier = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the PSNR coefficient identifier.
     *
     * @return The identifier as an integer value.
     */
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", identifier);
    }

    @Override
    public final String getDisplayName() {
        return "PSNR Coefficient Identifier";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.PSNRCoefficientIdentifier.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) identifier);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
