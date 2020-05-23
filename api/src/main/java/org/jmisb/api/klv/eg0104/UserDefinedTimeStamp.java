package org.jmisb.api.klv.eg0104;

import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * User Defined Time Stamp for EG 0104.
 */
public class UserDefinedTimeStamp implements IPredatorMetadataValue {

    private final ST0603TimeStamp timestamp;

    public UserDefinedTimeStamp(byte[] bytes)
    {
        timestamp = new ST0603TimeStamp(bytes);
    }

    @Override
    public String getDisplayableValue()
    {
        return timestamp.getDisplayableValue();
    }

    @Override
    public String getDisplayName()
    {
        return "User Defined Time Stamp";
    }
}
