package org.jmisb.api.klv.st0903.vmask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.ArrayUtils;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Bit Mask Series (ST0903 VMask Local Set Tag 2).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * A run-length encoding as a Series Type of a bit mask describing the pixels that subtend the
 * target within the Motion Imagery Frame. Pixel-number-plus-run-length pairs, each describing the
 * starting pixel number and the number of pixels in a run (see Figure 14). Pixel numbering
 * commences with 1, at the top left pixel, proceeding from left to right, top to bottom. Encode
 * pixel numbers using the Length-Value construct of a Variable-Length Pack. Encode the length of
 * each run using BER-Length encoding. The criterion used to decide whether a pixel “covers” a
 * portion of the target is somewhat arbitrary and left to the implementer. The implementer is free
 * to decide whether overlap with all, a majority, or just a fraction of the pixel constitutes
 * “covering” the target.
 *
 * <p>The calculation of the pixel number is pixel number Column + ((Row-1) x frame width)). The top
 * left pixel of the frame equates to (Column, Row) = (1, 1) with pixel number=1, then encoded using
 * the Length-Value construct of a Variable-Length Pack.
 *
 * </blockquote>
 */
public class BitMaskSeries implements IVmtiMetadataValue {
    private final List<PixelRunPair> bitMask = new ArrayList<>();

    /**
     * Create from value.
     *
     * @param runs the pixel number plus run pairs that make up the mask.
     */
    public BitMaskSeries(List<PixelRunPair> runs) {
        bitMask.addAll(runs);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array comprising the bit mask
     */
    public BitMaskSeries(byte[] bytes) {
        int index = 0;
        while (index < bytes.length - 1) {
            BerField lengthField = BerDecoder.decode(bytes, index, false);
            index += lengthField.getLength();
            byte[] valueBytes = Arrays.copyOfRange(bytes, index, index + lengthField.getValue());
            PixelRunPair run = parsePixelRunPair(valueBytes);
            bitMask.add(run);
            index += lengthField.getValue();
        }
    }

    @Override
    public byte[] getBytes() {
        int len = 0;
        List<byte[]> chunks = new ArrayList<>();
        for (PixelRunPair run : getRuns()) {
            byte[] pixelNumberBytes =
                    PrimitiveConverter.uintToVariableBytesV6(run.getPixelNumber());
            byte[] pixelNumberLengthBytes = BerEncoder.encode(pixelNumberBytes.length);
            byte[] runBytes = BerEncoder.encode(run.getRun());
            int bytesForThisRun =
                    pixelNumberLengthBytes.length + pixelNumberBytes.length + runBytes.length;
            byte[] overallLengthBytes = BerEncoder.encode(bytesForThisRun);
            chunks.add(overallLengthBytes);
            len += overallLengthBytes.length;
            chunks.add(pixelNumberLengthBytes);
            len += pixelNumberLengthBytes.length;
            chunks.add(pixelNumberBytes);
            len += pixelNumberBytes.length;
            chunks.add(runBytes);
            len += runBytes.length;
        }
        return ArrayUtils.arrayFromChunks(chunks, len);
    }

    @Override
    public String getDisplayableValue() {
        return "[Pixel / Run Pairs]";
    }

    @Override
    public String getDisplayName() {
        return "BitMask";
    }

    /**
     * Get the bit mask runs.
     *
     * @return the list of pixel numbers and run pairs.
     */
    public List<PixelRunPair> getRuns() {
        return bitMask;
    }

    private PixelRunPair parsePixelRunPair(byte[] valueBytes) {
        int index = 0;
        BerField lengthField = BerDecoder.decode(valueBytes, 0, false);
        index += lengthField.getLength();
        if (lengthField.getValue() > 6) {
            throw new IllegalArgumentException("Pixel number encoding is up to 6 bytes");
        }
        long pixelNumber = 0;
        for (int i = index; i < (index + lengthField.getValue()); ++i) {
            pixelNumber = pixelNumber << 8;
            pixelNumber += ((int) valueBytes[i] & 0xFF);
        }
        index += lengthField.getValue();
        BerField runField = BerDecoder.decode(valueBytes, index, true);
        int runValue = runField.getValue();
        return new PixelRunPair(pixelNumber, runValue);
    }
}
