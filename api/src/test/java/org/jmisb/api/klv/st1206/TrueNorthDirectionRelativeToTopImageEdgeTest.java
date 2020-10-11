package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI True North Direction Relative to Top Image Edge (ST1206 Tag 12) */
public class TrueNorthDirectionRelativeToTopImageEdgeTest {
    @Test
    public void testConstructFromValue() {
        TrueNorthDirectionRelativeToTopImageEdge uut =
                new TrueNorthDirectionRelativeToTopImageEdge(25.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "True North Direction Relative to Top Image Edge");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TrueNorthDirectionRelativeToTopImageEdge uut =
                new TrueNorthDirectionRelativeToTopImageEdge(new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "True North Direction Relative to Top Image Edge");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.TrueNorthDirectionRelativeToTopImageEdge,
                        new byte[] {(byte) 0x06, (byte) 0x40});
        assertTrue(value instanceof TrueNorthDirectionRelativeToTopImageEdge);
        TrueNorthDirectionRelativeToTopImageEdge uut =
                (TrueNorthDirectionRelativeToTopImageEdge) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x06, (byte) 0x40});
        assertEquals(uut.getDisplayName(), "True North Direction Relative to Top Image Edge");
        assertEquals(uut.getDisplayableValue(), "25.0\u00B0");
        assertEquals(uut.getAngle(), 25.0, 0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TrueNorthDirectionRelativeToTopImageEdge(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TrueNorthDirectionRelativeToTopImageEdge(360.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TrueNorthDirectionRelativeToTopImageEdge(new byte[] {0x01, 0x02, 0x03});
    }
}
