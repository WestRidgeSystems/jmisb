package org.jmisb.api.klv.st0903.vobject;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;

/**
 * Classification Confidence (VObject LS Tag 4).
 *
 * <p>The Confidence value is the measure of “trust” in the classification of this VObject, ranging
 * from 0.0% to 100.0%. For example, an object classifier will analyze a blob of pixels and “guess”
 * the blob represents a vehicle in the image; if the blob is well defined and matches the
 * classifiers criteria for a vehicle very closely the confidence in the classification is high
 * (towards 100.0%), alternatively if the classifier is less sure of its classification the
 * confidence is low (towards 0%). The confidence value is IMAPB(0.0, 100.0, length) with the length
 * defined by the tag’s length value. Increasing the length provides more accuracy, the minimum
 * length is 1.
 */
public class Confidence2 implements IVmtiMetadataValue {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;
    private final double confidence;

    /**
     * Create from value.
     *
     * @param confidence the confidence as a percentage (0 lowest, 100 highest)
     */
    public Confidence2(double confidence) {
        if (confidence < MIN_VALUE || confidence > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,100]");
        }
        this.confidence = confidence;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public Confidence2(byte[] bytes) {
        FpEncoder decoder = new FpEncoder(0, 100.0, bytes.length, OutOfRangeBehaviour.Default);
        confidence = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(0, 100.0, 2, OutOfRangeBehaviour.Default);
        return encoder.encode(confidence);
    }

    @Override
    public String getDisplayableValue() {
        return "" + confidence + "%";
    }

    /**
     * Get the confidence level.
     *
     * @return the confidence as a percentage (0 lowest, 100 highest).
     */
    public double getConfidence() {
        return this.confidence;
    }

    @Override
    public String getDisplayName() {
        return "Confidence";
    }
}
