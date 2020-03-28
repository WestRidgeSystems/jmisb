package org.jmisb.api.klv.st0903.vmask;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for PixelPolygon.
 */
public class PixelPolygonTest
{
    final byte[] stBytes = new byte[]
    {
        0x02, 0x39, (byte)0xAA,
        0x02, 0x39, (byte)0xBF,
        0x02, 0x3B, 0x0B
    };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException
    {
        PixelPolygon boundarySeries = new PixelPolygon(stBytes);
        verifyStExample(boundarySeries);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VMaskLS.createValue(VMaskMetadataKey.polygon , stBytes);
        assertTrue(value instanceof PixelPolygon );
        PixelPolygon polygon = (PixelPolygon)value;
        verifyStExample(polygon);
    }

    private void verifyStExample(PixelPolygon boundarySeries)
    {
        assertEquals(boundarySeries.getBytes(), stBytes);
        assertEquals(boundarySeries.getDisplayName(), "Polygon");
        assertEquals(boundarySeries.getDisplayableValue(), "[Pixel Numbers]");
        assertEquals(boundarySeries.getPolygon().size(), 3);
        assertEquals((long)boundarySeries.getPolygon().get(0), 14762L);
        assertEquals((long)boundarySeries.getPolygon().get(1), 14783L);
        assertEquals((long)boundarySeries.getPolygon().get(2), 15115L);
    }

    @Test
    public void constructFromValue() throws KlvParseException
    {
        List<Long> points = new ArrayList<>();
        points.add(14762L);
        points.add(14783L);
        points.add(15115L);
        PixelPolygon polygon = new PixelPolygon(points);
        verifyStExample(polygon);
    }

    @Test
    public void constructFromValueMultipleLengths() throws KlvParseException
    {
        List<Long> points = new ArrayList<>();
        points.add(1L);
        points.add(255L);
        points.add(256L);
        points.add(65535L);
        points.add(65536L);
        points.add(16777215L);
        points.add(16777216L);
        points.add(4294967295L);
        points.add(4294967296L);
        points.add(1099511627775L);
        points.add(1099511627776L);
        points.add(281474976710655L);
        PixelPolygon polygon = new PixelPolygon(points);
        assertEquals(polygon.getBytes(), new byte[] {
            (byte)0x01, (byte)0x01,
            (byte)0x01, (byte)0xFF,
            (byte)0x02, (byte)0x01, (byte)0x00,
            (byte)0x02, (byte)0xFF, (byte)0xFF,
            (byte)0x03, (byte)0x01, (byte)0x00, (byte)0x00,
            (byte)0x03, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0x04, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x04, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0x05, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x05, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0x06, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
            (byte)0x06, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF
        });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructFromEncodedBytesTooLong() throws KlvParseException
    {
        final byte[] bytes = new byte[]
        {
            0x02, 0x39, (byte) 0xAA,
            0x07, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x02, 0x3B, 0x0B
        };
        new PixelPolygon(bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructFromValueTooFewPoints() throws KlvParseException
    {
        List<Long> points = new ArrayList<>();
        points.add(14762L);
        points.add(14783L);
        new PixelPolygon(points);
    }
}
