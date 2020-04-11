package org.jmisb.api.klv.st0903.vtracker;

import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrackIdTest
{
    private final byte[] bytes = new byte[]{(byte)0xF8, (byte)0x1D, (byte)0x4F, (byte)0xAE, (byte)0x7D, (byte)0xEC, (byte)0x11, (byte)0xD0, (byte)0xA7, (byte)0x65, (byte)0x00, (byte)0xA0, (byte)0xC9, (byte)0x1E, (byte)0x6B, (byte)0xF6};

    @Test
    public void testTrackId()
    {
        // Example from VTracker Tag 1.
        TrackId id = new TrackId(UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
        Assert.assertEquals(id.getDisplayName(), "Track ID");
        Assert.assertEquals(id.getBytes(), bytes);
        Assert.assertEquals(id.getDisplayableValue(), "F81D-4FAE-7DEC-11D0-A765-00A0-C91E-6BF6");
        Assert.assertEquals(id.getUUID(), UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
    }

    @Test
    public void testFactoryAlgorithm() throws KlvParseException
    {
        IVmtiMetadataValue v = VTrackerLS.createValue(VTrackerMetadataKey.trackId, bytes);
        Assert.assertTrue(v instanceof TrackId);
        TrackId id = (TrackId)v;
        Assert.assertEquals(id.getDisplayName(), "Track ID");
        Assert.assertEquals(id.getBytes(), bytes);
        Assert.assertEquals(id.getDisplayableValue(), "F81D-4FAE-7DEC-11D0-A765-00A0-C91E-6BF6");
        Assert.assertEquals(id.getUUID(), UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
    }
}
