package org.jmisb.api.klv.st0601;

public class GroundRange extends UasRange
{

    public GroundRange(double meters)
    {
        super(meters);
    }

    public GroundRange(byte[] bytes)
    {
        super(bytes);
    }

    @Override
    public String getDisplayName()
    {
        return "Ground Range";
    }
}
