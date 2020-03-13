package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Percentage of Target Pixels (ST0903 VTarget Pack Tag 7).
 * <p>
 * From ST0903:
 * <blockquote>
 * The percentage of pixels within the bounding box detected to be target pixels
 * rather than background pixels. Range 1 to 100, where 100 signifies that the
 * target completely fills the bounding box. Use of the VMask, VChip, VObject,
 * or VFeature Local Sets recommended where more detail about a target is
 * necessary. If a detection has occurred, the size of the bounding box should
 * be such that a non-zero percentage of pixels overlaps the target.
 * </blockquote>
 */
public class PercentageOfTargetPixels implements IVmtiMetadataValue {

    private final short percentage;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 100;

    /**
     * Create from value.
     *
     * @param percentage the target percentage as a percentage (1 lowest, 100 highest)
     */
    public PercentageOfTargetPixels(short percentage)
    {
        if (percentage < MIN_VALUE || percentage > MAX_VALUE)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " value must be in range [1,100]");
        }
        this.percentage = percentage; 
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, length 1 byte
     */
    public PercentageOfTargetPixels(byte[] bytes)
    {
        if (bytes.length > 1)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is maximum one byte unsigned integer");
        }
        percentage = (short)PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        return PrimitiveConverter.uint8ToBytes(percentage);
    }

    @Override
    public String getDisplayableValue()
    {
        return "" + percentage + "%";
    }

    @Override
    public final String getDisplayName()
    {
        return "Percentage Target Pixels";
    }
    
    /**
     * Get the percentage of target pixels.
     * 
     * @return the percentage of target pixels (1 lowest, 100 highest).
     */
    public short getPercentageOfTargetPixels()
    {
        return this.percentage;
    }
}
