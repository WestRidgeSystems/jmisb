package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Velocity (Tag 10)
 */
public class VelocityTest
{
    private final byte[] velocityBytes = new byte[] {
        (byte)0x4B, (byte)0x00,
        (byte)0x44, (byte)0xC0,
        (byte)0x3E, (byte)0x80
    };

    private final byte[] velocityPlusSigmaBytes = new byte[] {
        (byte)0x4B, (byte)0x00,
        (byte)0x44, (byte)0xC0,
        (byte)0x3E, (byte)0x80,
        (byte)0x25, (byte)0x80,
        (byte)0x19, (byte)0x00,
        (byte)0x0C, (byte)0x80
    };

    private final byte[] velocityPlusSigmaAndRhoBytes = new byte[] {
        (byte)0x4B, (byte)0x00,
        (byte)0x44, (byte)0xC0,
        (byte)0x3E, (byte)0x80,
        (byte)0x25, (byte)0x80,
        (byte)0x19, (byte)0x00,
        (byte)0x0C, (byte)0x80,
        (byte)0x70, (byte)0x00,
        (byte)0x60, (byte)0x00,
        (byte)0x50, (byte)0x00
    };

    @Test
    public void testConstructFromValue()
    {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertNull(velocity.getVelocity().getSigEast());
        assertNull(velocity.getVelocity().getSigNorth());
        assertNull(velocity.getVelocity().getSigUp());
        assertNull(velocity.getVelocity().getRhoEastNorth());
        assertNull(velocity.getVelocity().getRhoEastUp());
        assertNull(velocity.getVelocity().getRhoNorthUp());
        assertEquals(velocity.getBytes(), velocityBytes);
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test
    public void testConstructFromValueWithStandardDeviation()
    {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertEquals(velocity.getVelocity().getSigEast(), 300.0, 0.1);
        assertEquals(velocity.getVelocity().getSigNorth(), 200.0, 0.1);
        assertEquals(velocity.getVelocity().getSigUp(), 100.0, 0.1);
        assertNull(velocity.getVelocity().getRhoEastNorth());
        assertNull(velocity.getVelocity().getRhoEastUp());
        assertNull(velocity.getVelocity().getRhoNorthUp());
        assertEquals(velocity.getBytes(), velocityPlusSigmaBytes);
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test
    public void testConstructFromValueWithCorrelation()
    {
        VelocityPack velPack = new VelocityPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertEquals(velocity.getVelocity().getSigEast(), 300.0, 0.1);
        assertEquals(velocity.getVelocity().getSigNorth(), 200.0, 0.1);
        assertEquals(velocity.getVelocity().getSigUp(), 100.0, 0.1);
        assertEquals(velocity.getVelocity().getRhoEastNorth(), 0.75, 0.01);
        assertEquals(velocity.getVelocity().getRhoEastUp(), 0.50, 0.01);
        assertEquals(velocity.getVelocity().getRhoNorthUp(), 0.25, 0.01);
        assertEquals(velocity.getBytes(), velocityPlusSigmaAndRhoBytes);
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        Velocity velocity = new Velocity(velocityBytes);
        assertEquals(velocity.getBytes(), velocityBytes);
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertNull(velocity.getVelocity().getSigEast());
        assertNull(velocity.getVelocity().getSigNorth());
        assertNull(velocity.getVelocity().getSigUp());
        assertNull(velocity.getVelocity().getRhoEastNorth());
        assertNull(velocity.getVelocity().getRhoEastUp());
        assertNull(velocity.getVelocity().getRhoNorthUp());
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.velocity, velocityBytes);
        assertTrue(value instanceof Velocity);
        Velocity velocity = (Velocity)value;
        assertEquals(velocity.getBytes(), velocityBytes);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertNull(velocity.getVelocity().getSigEast());
        assertNull(velocity.getVelocity().getSigNorth());
        assertNull(velocity.getVelocity().getSigUp());
        assertNull(velocity.getVelocity().getRhoEastNorth());
        assertNull(velocity.getVelocity().getRhoEastUp());
        assertNull(velocity.getVelocity().getRhoNorthUp());
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test
    public void testFactoryWithSigma() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.velocity, velocityPlusSigmaBytes);
        assertTrue(value instanceof Velocity);
        Velocity velocity = (Velocity)value;
        assertEquals(velocity.getBytes(), velocityPlusSigmaBytes);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertEquals(velocity.getVelocity().getSigEast(), 300.0, 0.1);
        assertEquals(velocity.getVelocity().getSigNorth(), 200.0, 0.1);
        assertEquals(velocity.getVelocity().getSigUp(), 100.0, 0.1);
        assertNull(velocity.getVelocity().getRhoEastNorth());
        assertNull(velocity.getVelocity().getRhoEastUp());
        assertNull(velocity.getVelocity().getRhoNorthUp());
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test
    public void testFactoryWithSigmaAndRho() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.velocity, velocityPlusSigmaAndRhoBytes);
        assertTrue(value instanceof Velocity);
        Velocity velocity = (Velocity)value;
        assertEquals(velocity.getBytes(), velocityPlusSigmaAndRhoBytes);
        assertEquals(velocity.getVelocity().getEast(), 300, 0.1);
        assertEquals(velocity.getVelocity().getNorth(), 200, 0.1);
        assertEquals(velocity.getVelocity().getUp(), 100, 0.1);
        assertEquals(velocity.getVelocity().getSigEast(), 300.0, 0.1);
        assertEquals(velocity.getVelocity().getSigNorth(), 200.0, 0.1);
        assertEquals(velocity.getVelocity().getSigUp(), 100.0, 0.1);
        assertEquals(velocity.getVelocity().getRhoEastNorth(), 0.75, 0.01);
        assertEquals(velocity.getVelocity().getRhoEastUp(), 0.50, 0.01);
        assertEquals(velocity.getVelocity().getRhoNorthUp(), 0.25, 0.01);
        assertEquals(velocity.getDisplayName(), "Velocity");
        assertEquals(velocity.getDisplayableValue(), "[Velocity]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new Velocity(new byte[]{0x01, 0x02, 0x03});
    }

    @Test
    public void testConstructFromValueNullEast()
    {
        VelocityPack velPack = new VelocityPack(null, 29.157890122923, 32.0);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullNorth()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, null, 32.0);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullUp()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, null);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull1()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, null, 3.0, 649.9);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull2()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, null, 649.9);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull3()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, null);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull1()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, null, 0.0, 0.5);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 12);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull2()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, null, 0.5);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 12);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull3()
    {
        VelocityPack velPack = new VelocityPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, 0.0, null);
        Velocity velocity = new Velocity(velPack);
        assertEquals(velocity.getBytes().length, 12);
    }
}
