package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FogPresenceTest {
    @Test
    public void testConstructFromValueTrue() {
        FogPresence uut = new FogPresence(true);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertTrue(uut.getValue());
        assertEquals(uut.getDisplayName(), "Fog Presence");
        assertEquals(uut.getDisplayableValue(), "Present");
    }

    @Test
    public void testConstructFromValueFalse() {
        FogPresence uut = new FogPresence(false);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        assertFalse(uut.getValue());
        assertEquals(uut.getDisplayName(), "Fog Presence");
        assertEquals(uut.getDisplayableValue(), "Not Present");
    }

    @Test
    public void testConstructFromEncodedBytesTrue() throws KlvParseException {
        FogPresence uut = new FogPresence(new byte[] {(byte) 0x01});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertTrue(uut.getValue());
        assertEquals(uut.getDisplayName(), "Fog Presence");
        assertEquals(uut.getDisplayableValue(), "Present");
    }

    @Test
    public void testConstructFromEncodedValueFalse() throws KlvParseException {
        FogPresence uut = new FogPresence(new byte[] {(byte) 0x00});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        assertFalse(uut.getValue());
        assertEquals(uut.getDisplayName(), "Fog Presence");
        assertEquals(uut.getDisplayableValue(), "Not Present");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01};
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.FogPresence, bytes);
        Assert.assertTrue(v instanceof FogPresence);
        FogPresence uut = (FogPresence) v;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        assertTrue(uut.getValue());
        assertEquals(uut.getDisplayName(), "Fog Presence");
        assertEquals(uut.getDisplayableValue(), "Present");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthLong() throws KlvParseException {
        new FogPresence(new byte[] {0x01, 0x02});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLengthShort() throws KlvParseException {
        new FogPresence(new byte[] {});
    }
}
