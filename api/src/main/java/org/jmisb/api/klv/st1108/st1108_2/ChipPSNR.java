package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Chip Peak Signal to Noise Ratio (PSNR) (ST 1108.2 Tag 14).
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The Chip PSNR is calculated by taking 10*log10 of the ratio of the square of the greatest
 * possible pixel intensity value to the mean-square-error between the compressed and uncompressed
 * chip. If the PSNR is computed over the entire frame, then the chip size is the frame size.
 *
 * </blockquote>
 */
public class ChipPSNR implements IInterpretabilityQualityMetadataValue {
    private int psnr;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value.
     *
     * @param psnr The PSNR.
     */
    public ChipPSNR(int psnr) {
        if (psnr < MIN_VALUE || psnr > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,100]");
        }
        this.psnr = psnr;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 1 byte.
     */
    public ChipPSNR(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        psnr = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the PSNR.
     *
     * @return The PSNR as an integer, in units of dB.
     */
    public int getPSNR() {
        return psnr;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d dB", psnr);
    }

    @Override
    public final String getDisplayName() {
        return "Chip PSNR";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipPSNR.getIdentifier());
        byte[] valueBytes = PrimitiveConverter.uint8ToBytes((short) psnr);
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
