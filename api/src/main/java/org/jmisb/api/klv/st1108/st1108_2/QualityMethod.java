package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Interpretability Quality Method (ST 1108.2 Tag 4).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>Manual ratings are indicated by a zero. Non-zero entries indicate the version number of MISB
 * RP 1203, “Video Interpretability and Quality Measurement” used to produce the inserted rating.
 *
 * </blockquote>
 */
public class QualityMethod implements IInterpretabilityQualityMetadataValue {
    private int methodId;
    private static final int MIN_VALUE = 0;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * <p>Manual ratings are indicated by a zero. Non-zero entries indicate the version number of
     * MISB RP 1203, “Video Interpretability and Quality Measurement” used to produce the inserted
     * rating.
     *
     * @param method The IQ Method value.
     */
    public QualityMethod(int method) {
        if (method < MIN_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be greater than or equal to zero");
        }
        this.methodId = method;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public QualityMethod(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        methodId = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the method identifier.
     *
     * <p>Manual ratings are indicated by a zero. Non-zero entries indicate the version number of
     * MISB RP 1203, “Video Interpretability and Quality Measurement” used to produce the inserted
     * rating.
     *
     * @return The method identifier.
     */
    public int getMethodId() {
        return methodId;
    }

    @Override
    public String getDisplayableValue() {
        String expanded = "Manual";
        if (methodId > 0) {
            expanded = String.format("RP 1203.%d", methodId);
        }
        return String.format("%d (%s)", methodId, expanded);
    }

    @Override
    public final String getDisplayName() {
        return "Interpretability Quality Method";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.InterpretabilityQualityMethod.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) methodId);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
