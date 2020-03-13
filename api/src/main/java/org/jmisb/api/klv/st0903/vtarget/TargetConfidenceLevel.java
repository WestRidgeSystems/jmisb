package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Confidence Level (ST0903 VTarget Pack Tag 5)
 * <p>
 * From ST0903:
 * <blockquote>
 * Confidence level, expressed as a percentage, based on criteria within the
 * VMTI system. Target(s) with the highest confidence may not have the highest
 * priority value. Potential for use in limited bandwidth scenarios to only send
 * highest confidence targets. Multiple targets may have the same confidence
 * level. Range 0 to 100, where 100 percent is the highest confidence. Although
 * a confidence level of 0 percent indicates no confidence that a detection is a
 * potential target. A target detected with a high confidence may be a low
 * priority target.
 * </blockquote>
 */
public class TargetConfidenceLevel implements IVmtiMetadataValue {

    private final short confidence;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 100;

    /**
     * Create from value.
     *
     * @param confidence the target confidence as a percentage (0 lowest, 100 highest)
     */
    public TargetConfidenceLevel(short confidence)
    {
        if (confidence < MIN_VALUE || confidence > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " value must be in range [0,100]");
        }
        this.confidence = confidence; 
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetConfidenceLevel(byte[] bytes)
    {
        if (bytes.length > 1)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is maximum one byte unsigned integer");
        }
        confidence = (short)PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint8ToBytes(confidence);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + confidence + "%";
    }

    @Override
    public final String getDisplayName()
    {
        return "Target Confidence";
    }
    
    /**
     * Get the target confidence.
     * 
     * @return the target confidence as a percentage (0 lowest, 100 highest).
     */
    public short getTargetConfidence()
    {
        return this.confidence;
    }
}
