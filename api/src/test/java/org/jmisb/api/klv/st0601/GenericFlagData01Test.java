package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
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
        Set<FlagDataKey> expectedKeys = new HashSet<>();
        expectedKeys.add(FlagDataKey.LaserRange);
        expectedKeys.add(FlagDataKey.AutoTrack);
        expectedKeys.add(FlagDataKey.IR_Polarity);
        expectedKeys.add(FlagDataKey.IcingStatus);
        expectedKeys.add(FlagDataKey.SlantRange);
        expectedKeys.add(FlagDataKey.ImageInvalid);
        assertEquals(flags.getTags(), expectedKeys);
    }

    @Test
    public void testConstructFromValueAllOn()
    {
        List<FlagDataKey> bitsToTurnOn = new ArrayList<>();
        bitsToTurnOn.add(FlagDataKey.LaserRange);
        bitsToTurnOn.add(FlagDataKey.AutoTrack);
        bitsToTurnOn.add(FlagDataKey.IR_Polarity);
        bitsToTurnOn.add(FlagDataKey.IcingStatus);
        bitsToTurnOn.add(FlagDataKey.SlantRange);
        bitsToTurnOn.add(FlagDataKey.ImageInvalid);
        GenericFlagData01 flags = new GenericFlagData01(bitsToTurnOn);
        checkFlagsAllOn(flags);
    }

    @Test
    public void testConstructFromEncodedAllOn()
    {
        GenericFlagData01 flags = new GenericFlagData01(new byte[]{0x3f});
        checkFlagsAllOn(flags);
    }

    protected void checkFlagsAllOn(GenericFlagData01 flags) {
        assertEquals(flags.getField(FlagDataKey.LaserRange).getDisplayableValue(), "Laser on");
        assertEquals(flags.getField(FlagDataKey.AutoTrack).getDisplayableValue(), "Auto-Track on");
        assertEquals(flags.getField(FlagDataKey.IR_Polarity).getDisplayableValue(), "Black Hot");
        assertEquals(flags.getField(FlagDataKey.IcingStatus).getDisplayableValue(), "Icing Detected");
        assertEquals(flags.getField(FlagDataKey.SlantRange).getDisplayableValue(), "Measured");
        assertEquals(flags.getField(FlagDataKey.ImageInvalid).getDisplayableValue(), "Image Invalid");
        assertEquals(flags.getBytes(), new byte[]{0x3f});
        assertEquals(flags.getDisplayableValue(), "[Flag data]");
        assertEquals(flags.getDisplayName(), "Generic Flag Data 01");
        Set<FlagDataKey> expectedKeys = new HashSet<>();
        expectedKeys.add(FlagDataKey.LaserRange);
        expectedKeys.add(FlagDataKey.AutoTrack);
        expectedKeys.add(FlagDataKey.IR_Polarity);
        expectedKeys.add(FlagDataKey.IcingStatus);
        expectedKeys.add(FlagDataKey.SlantRange);
        expectedKeys.add(FlagDataKey.ImageInvalid);
        assertEquals(flags.getTags(), expectedKeys);
    }

    @Test
    public void testConstructFromValueAllOff()
    {
        List<FlagDataKey> bitsToTurnOn = new ArrayList<>();
        GenericFlagData01 flags = new GenericFlagData01(bitsToTurnOn);
        checkFlagsAllOff(flags);
    }

    @Test
    public void testConstructFromEncodedAllOff()
    {
        GenericFlagData01 flags = new GenericFlagData01(new byte[]{0x00});
        checkFlagsAllOff(flags);
    }

    protected void checkFlagsAllOff(GenericFlagData01 flags) {
        assertEquals(flags.getField(FlagDataKey.LaserRange).getDisplayableValue(), "Laser off");
        assertEquals(flags.getField(FlagDataKey.AutoTrack).getDisplayableValue(), "Auto-Track off");
        assertEquals(flags.getField(FlagDataKey.IR_Polarity).getDisplayableValue(), "White Hot");
        assertEquals(flags.getField(FlagDataKey.IcingStatus).getDisplayableValue(), "No Icing Detected");
        assertEquals(flags.getField(FlagDataKey.SlantRange).getDisplayableValue(), "Calculated");
        assertEquals(flags.getField(FlagDataKey.ImageInvalid).getDisplayableValue(), "Image Valid");
        assertEquals(flags.getBytes(), new byte[]{0x00});
        assertEquals(flags.getDisplayableValue(), "[Flag data]");
        assertEquals(flags.getDisplayName(), "Generic Flag Data 01");
        Set<FlagDataKey> expectedKeys = new HashSet<>();
        expectedKeys.add(FlagDataKey.LaserRange);
        expectedKeys.add(FlagDataKey.AutoTrack);
        expectedKeys.add(FlagDataKey.IR_Polarity);
        expectedKeys.add(FlagDataKey.IcingStatus);
        expectedKeys.add(FlagDataKey.SlantRange);
        expectedKeys.add(FlagDataKey.ImageInvalid);
        assertEquals(flags.getTags(), expectedKeys);
    }

    @Test
    public void testNoKey()
    {
        GenericFlagData01 flags = new GenericFlagData01(new byte[]{0x00});
        IKlvKey noSuchKey = () -> 616;
        assertNull(flags.getField(noSuchKey));
    }
}
