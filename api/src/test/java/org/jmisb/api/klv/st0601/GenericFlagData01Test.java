package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class GenericFlagData01Test
{
    private final byte[] st_example_bytes = new byte[] {0x31};

    @Test
    public void testConstructFromValue()
    {
        List<FlagDataKey> bitsToTurnOn = new ArrayList<>();
        bitsToTurnOn.add(FlagDataKey.LaserRange);
        bitsToTurnOn.add(FlagDataKey.SlantRange);
        bitsToTurnOn.add(FlagDataKey.ImageInvalid);
        GenericFlagData01 flags = new GenericFlagData01(bitsToTurnOn);
        checkFlags(flags);
    }

    @Test
    public void testConstructFromEncoded()
    {
        GenericFlagData01 flags = new GenericFlagData01(st_example_bytes);
        checkFlags(flags);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.GenericFlagData01, st_example_bytes);
        assertTrue(v instanceof GenericFlagData01);
        GenericFlagData01 flags = (GenericFlagData01)v;
        checkFlags(flags);
    }

    private void checkFlags(GenericFlagData01 flags)
    {
        assertEquals(flags.getField(FlagDataKey.LaserRange).getDisplayableValue(), "Laser on");
        assertEquals(flags.getField(FlagDataKey.AutoTrack).getDisplayableValue(), "Auto-Track off");
        assertEquals(flags.getField(FlagDataKey.IR_Polarity).getDisplayableValue(), "White Hot");
        assertEquals(flags.getField(FlagDataKey.IcingStatus).getDisplayableValue(), "No Icing Detected");
        assertEquals(flags.getField(FlagDataKey.SlantRange).getDisplayableValue(), "Measured");
        assertEquals(flags.getField(FlagDataKey.ImageInvalid).getDisplayableValue(), "Image Invalid");
        assertEquals(flags.getBytes(), st_example_bytes);
        assertEquals(flags.getDisplayableValue(), "[Flag data]");
        assertEquals(flags.getDisplayName(), "Generic Flag Data 01");
    }
}
