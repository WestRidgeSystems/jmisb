package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Shared superclass for TargetLocationOffsetLatitude (Tag 10) and
 * TargetLocationOffsetLongitude (Tag 11).
 */
public abstract class AbstractTargetLocationOffset implements IVmtiMetadataValue
{
    
    protected static double MIN_VAL = -19.2;
    protected static double MAX_VAL = 19.2;
    protected static int NUM_BYTES = 3;
    protected double value;

    /**
     * Create from value
     *
     * @param offset location offset in degrees. Valid range is [-19.2, 19.2]
     */
    public AbstractTargetLocationOffset(double offset)
    {
        if (offset < MIN_VAL || offset > MAX_VAL)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [-19.2,19.2]");
        }
        this.value = offset;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public AbstractTargetLocationOffset(byte[] bytes)
    {
        if (bytes.length != 3)
        {
            throw new IllegalArgumentException(this.getDisplayName() + " encoding is three byte IMAPB");
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes()
    {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
        return encoder.encode(this.value);
    }

    @Override
    public String getDisplayableValue()
    {
        return String.format("%.5f\u00B0", value);
    }

    /**
     * Get the value.
     *
     * @return the value in offset degrees.
     */
    public double getValue()
    {
        return this.value;
    }
    
}
