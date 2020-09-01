package org.jmisb.api.klv.st0903.vtracker;

import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrackIdTest {
    private final byte[] bytes =
            new byte[] {
                (byte) 0xF8,
                (byte) 0x1D,
                (byte) 0x4F,
                (byte) 0xAE,
                (byte) 0x7D,
                (byte) 0xEC,
                (byte) 0x11,
                (byte) 0xD0,
                (byte) 0xA7,
                (byte) 0x65,
                (byte) 0x00,
                (byte) 0xA0,
                (byte) 0xC9,
                (byte) 0x1E,
                (byte) 0x6B,
                (byte) 0xF6
            };

    @Test
    public void testTrackId() {
        // Example from VTracker Tag 1.
        TrackId id = new TrackId(UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
        Assert.assertEquals(id.getDisplayName(), "Track ID");
        Assert.assertEquals(id.getBytes(), bytes);
        Assert.assertEquals(id.getDisplayableValue(), "F81D-4FAE-7DEC-11D0-A765-00A0-C91E-6BF6");
        Assert.assertEquals(id.getUUID(), UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
    }

    @Test
    public void testFactoryAlgorithm() throws KlvParseException {
        IVmtiMetadataValue v =
                VTrackerLS.createValue(VTrackerMetadataKey.trackId, bytes, EncodingMode.IMAPB);
        Assert.assertTrue(v instanceof TrackId);
        TrackId id = (TrackId) v;
        Assert.assertEquals(id.getDisplayName(), "Track ID");
        Assert.assertEquals(id.getBytes(), bytes);
        Assert.assertEquals(id.getDisplayableValue(), "F81D-4FAE-7DEC-11D0-A765-00A0-C91E-6BF6");
        Assert.assertEquals(id.getUUID(), UUID.fromString("F81D4FAE-7DEC-11D0-A765-00A0C91E6BF6"));
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new TrackId(
                new byte[] {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d,
                    0x0e
                });
    }
}
