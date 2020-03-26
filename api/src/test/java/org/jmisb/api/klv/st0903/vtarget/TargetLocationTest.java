package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vtarget.dto.TargetLocationPack;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Target Location (Tag 17)
 */
public class TargetLocationTest
{
    private final byte[] coordinateBytes = new byte[] {
        (byte)0x27, (byte)0xba, (byte)0x93, (byte)0x02,
        (byte)0x34, (byte)0x4a, (byte)0x1a, (byte)0xdf,
        (byte)0x10, (byte)0x14
    };

    private final byte[] coordinatePlusSigmaBytes = new byte[] {
        (byte)0x27, (byte)0xba, (byte)0x93, (byte)0x02,
        (byte)0x34, (byte)0x4a, (byte)0x1a, (byte)0xdf,
        (byte)0x10, (byte)0x14,
        (byte)0x00, (byte)0x03,
        (byte)0x00, (byte)0x60,
        (byte)0x51, (byte)0x3C
    };

    private final byte[] coordinatePlusSigmaAndRhoBytes = new byte[] {
        (byte)0x27, (byte)0xba, (byte)0x93, (byte)0x02,
        (byte)0x34, (byte)0x4a, (byte)0x1a, (byte)0xdf,
        (byte)0x10, (byte)0x14,
        (byte)0x00, (byte)0x03,
        (byte)0x00, (byte)0x60,
        (byte)0x51, (byte)0x3C,
        (byte)0x00, (byte)0x00,
        (byte)0x40, (byte)0x00,
        (byte)0x60, (byte)0x00
    };

    @Test
    public void testConstructFromValue()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.02);
        assertNull(location.getTargetLocation().getSigEast());
        assertNull(location.getTargetLocation().getSigNorth());
        assertNull(location.getTargetLocation().getSigUp());
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
        assertEquals(location.getBytes(), coordinateBytes);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    public void testConstructFromValueWithStandardDeviation()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.2);
        assertEquals(location.getTargetLocation().getSigEast(), 0.1, 0.03);
        assertEquals(location.getTargetLocation().getSigNorth(), 3.0, 0.03);
        assertEquals(location.getTargetLocation().getSigUp(), 649.9, 0.03);
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
        assertEquals(location.getBytes(), coordinatePlusSigmaBytes);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    public void testConstructFromValueWithCorrelation()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, 0.5);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.2);
        assertEquals(location.getTargetLocation().getSigEast(), 0.1, 0.03);
        assertEquals(location.getTargetLocation().getSigNorth(), 3.0, 0.03);
        assertEquals(location.getTargetLocation().getSigUp(), 649.9, 0.03);
        assertEquals(location.getTargetLocation().getRhoEastNorth(), -1.0, 0.001);
        assertEquals(location.getTargetLocation().getRhoEastUp(), 0.0, 0.001);
        assertEquals(location.getTargetLocation().getRhoNorthUp(), 0.5, 0.001);
        assertEquals(location.getBytes(), coordinatePlusSigmaAndRhoBytes);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        TargetLocation location = new TargetLocation(coordinateBytes);
        assertEquals(location.getBytes(), coordinateBytes);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.02);
        assertNull(location.getTargetLocation().getSigEast());
        assertNull(location.getTargetLocation().getSigNorth());
        assertNull(location.getTargetLocation().getSigUp());
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.TargetLocation, coordinateBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation)value;
        assertEquals(location.getBytes(), coordinateBytes);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.02);
        assertNull(location.getTargetLocation().getSigEast());
        assertNull(location.getTargetLocation().getSigNorth());
        assertNull(location.getTargetLocation().getSigUp());
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    public void testFactoryWithSigma() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.TargetLocation, coordinatePlusSigmaBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation)value;
        assertEquals(location.getBytes(), coordinatePlusSigmaBytes);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.02);
        assertEquals(location.getTargetLocation().getSigEast(), 0.1, 0.03);
        assertEquals(location.getTargetLocation().getSigNorth(), 3.0, 0.03);
        assertEquals(location.getTargetLocation().getSigUp(), 649.9, 0.03);
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    public void testFactoryWithSigmaAndRho() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.TargetLocation, coordinatePlusSigmaAndRhoBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation)value;
        assertEquals(location.getBytes(), coordinatePlusSigmaAndRhoBytes);
        assertEquals(location.getTargetLocation().getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getTargetLocation().getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getTargetLocation().getHae(), 3216.0, 0.02);
        assertEquals(location.getTargetLocation().getSigEast(), 0.1, 0.03);
        assertEquals(location.getTargetLocation().getSigNorth(), 3.0, 0.03);
        assertEquals(location.getTargetLocation().getSigUp(), 649.9, 0.03);
        assertEquals(location.getTargetLocation().getRhoEastNorth(), -1.0, 0.001);
        assertEquals(location.getTargetLocation().getRhoEastUp(), 0.0, 0.001);
        assertEquals(location.getTargetLocation().getRhoNorthUp(), 0.5, 0.001);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TargetLocation(new byte[]{0x01, 0x02, 0x03});
    }

    @Test
    public void testConstructFromValueNullLat()
    {
        TargetLocationPack locPack = new TargetLocationPack(null, 29.157890122923, 3216.0);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullLon()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, null, 3216.0);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullAlt()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull1()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, null, 3.0, 649.9);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull2()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, null, 649.9);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull3()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull1()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, null, 0.0, 0.5);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull2()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, null, 0.5);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull3()
    {
        TargetLocationPack locPack = new TargetLocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9, -1.0, 0.0, null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
    }
}
