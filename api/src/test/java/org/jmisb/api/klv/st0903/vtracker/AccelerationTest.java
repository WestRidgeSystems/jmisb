package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Acceleration (Tag 11)
 */
public class AccelerationTest
{
    private final byte[] accelerationBytes = new byte[] {
        (byte)0x4B, (byte)0x00,
        (byte)0x44, (byte)0xC0,
        (byte)0x3E, (byte)0x80
    };

    private final byte[] accelerationPlusSigmaBytes = new byte[] {
        (byte)0x4B, (byte)0x00,
        (byte)0x44, (byte)0xC0,
        (byte)0x3E, (byte)0x80,
        (byte)0x25, (byte)0x80,
        (byte)0x19, (byte)0x00,
        (byte)0x0C, (byte)0x80
    };

    private final byte[] accelerationPlusSigmaAndRhoBytes = new byte[] {
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
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertNull(acceleration.getAcceleration().getSigEast());
        assertNull(acceleration.getAcceleration().getSigNorth());
        assertNull(acceleration.getAcceleration().getSigUp());
        assertNull(acceleration.getAcceleration().getRhoEastNorth());
        assertNull(acceleration.getAcceleration().getRhoEastUp());
        assertNull(acceleration.getAcceleration().getRhoNorthUp());
        assertEquals(acceleration.getBytes(), accelerationBytes);
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test
    public void testConstructFromValueWithStandardDeviation()
    {
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertEquals(acceleration.getAcceleration().getSigEast(), 300.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigNorth(), 200.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigUp(), 100.0, 0.1);
        assertNull(acceleration.getAcceleration().getRhoEastNorth());
        assertNull(acceleration.getAcceleration().getRhoEastUp());
        assertNull(acceleration.getAcceleration().getRhoNorthUp());
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaBytes);
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test
    public void testConstructFromValueWithCorrelation()
    {
        AccelerationPack accelerationPack = new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertEquals(acceleration.getAcceleration().getSigEast(), 300.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigNorth(), 200.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigUp(), 100.0, 0.1);
        assertEquals(acceleration.getAcceleration().getRhoEastNorth(), 0.75, 0.01);
        assertEquals(acceleration.getAcceleration().getRhoEastUp(), 0.50, 0.01);
        assertEquals(acceleration.getAcceleration().getRhoNorthUp(), 0.25, 0.01);
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaAndRhoBytes);
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test
    public void testConstructFromEncodedBytes()
    {
        Acceleration acceleration = new Acceleration(accelerationBytes);
        assertEquals(acceleration.getBytes(), accelerationBytes);
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertNull(acceleration.getAcceleration().getSigEast());
        assertNull(acceleration.getAcceleration().getSigNorth());
        assertNull(acceleration.getAcceleration().getSigUp());
        assertNull(acceleration.getAcceleration().getRhoEastNorth());
        assertNull(acceleration.getAcceleration().getRhoEastUp());
        assertNull(acceleration.getAcceleration().getRhoNorthUp());
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.acceleration, accelerationBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration)value;
        assertEquals(acceleration.getBytes(), accelerationBytes);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertNull(acceleration.getAcceleration().getSigEast());
        assertNull(acceleration.getAcceleration().getSigNorth());
        assertNull(acceleration.getAcceleration().getSigUp());
        assertNull(acceleration.getAcceleration().getRhoEastNorth());
        assertNull(acceleration.getAcceleration().getRhoEastUp());
        assertNull(acceleration.getAcceleration().getRhoNorthUp());
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test
    public void testFactoryWithSigma() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.acceleration, accelerationPlusSigmaBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration)value;
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaBytes);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertEquals(acceleration.getAcceleration().getSigEast(), 300.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigNorth(), 200.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigUp(), 100.0, 0.1);
        assertNull(acceleration.getAcceleration().getRhoEastNorth());
        assertNull(acceleration.getAcceleration().getRhoEastUp());
        assertNull(acceleration.getAcceleration().getRhoNorthUp());
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test
    public void testFactoryWithSigmaAndRho() throws KlvParseException
    {
        IVmtiMetadataValue value = VTrackerLS.createValue(VTrackerMetadataKey.acceleration, accelerationPlusSigmaAndRhoBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration)value;
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaAndRhoBytes);
        assertEquals(acceleration.getAcceleration().getEast(), 300, 0.1);
        assertEquals(acceleration.getAcceleration().getNorth(), 200, 0.1);
        assertEquals(acceleration.getAcceleration().getUp(), 100, 0.1);
        assertEquals(acceleration.getAcceleration().getSigEast(), 300.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigNorth(), 200.0, 0.1);
        assertEquals(acceleration.getAcceleration().getSigUp(), 100.0, 0.1);
        assertEquals(acceleration.getAcceleration().getRhoEastNorth(), 0.75, 0.01);
        assertEquals(acceleration.getAcceleration().getRhoEastUp(), 0.50, 0.01);
        assertEquals(acceleration.getAcceleration().getRhoNorthUp(), 0.25, 0.01);
        assertEquals(acceleration.getDisplayName(), "Acceleration");
        assertEquals(acceleration.getDisplayableValue(), "[Acceleration]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new Acceleration(new byte[]{0x01, 0x02, 0x03});
    }

    @Test
    public void testConstructFromValueNullEast()
    {
        AccelerationPack accelerationPack = new AccelerationPack(null, 29.157890122923, 32.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullNorth()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, null, 32.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueNullUp()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull1()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, null, 3.0, 649.9);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull2()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, null, 649.9);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull3()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull1()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, null, 0.0, 0.5);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull2()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, null, 0.5);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull3()
    {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, 0.0, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
    }
}
