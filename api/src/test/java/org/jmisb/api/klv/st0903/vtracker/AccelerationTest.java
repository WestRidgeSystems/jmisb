package org.jmisb.api.klv.st0903.vtracker;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.LocVelAccPackKey;
import org.testng.annotations.Test;

/** Tests for Acceleration (Tag 11) */
public class AccelerationTest {
    private final byte[] accelerationBytes =
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xC0,
                (byte) 0x3E, (byte) 0x80
            };

    private final byte[] accelerationBytesLegacyForm =
            // From ST0903.3 B.4.1
            new byte[] {
                (byte) 0xAA, (byte) 0xAA,
                (byte) 0x9C, (byte) 0x71,
                (byte) 0x8E, (byte) 0x38
            };

    private final byte[] accelerationBytesFromLegacyBytes =
            // Some rounding vs truncation differences
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xBF,
                (byte) 0x3E, (byte) 0x7F
            };

    private final byte[] accelerationPlusSigmaBytes =
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xC0,
                (byte) 0x3E, (byte) 0x80,
                (byte) 0x25, (byte) 0x80,
                (byte) 0x19, (byte) 0x00,
                (byte) 0x0C, (byte) 0x80
            };

    private final byte[] accelerationPlusSigmaBytesLegacyForm =
            // From ST0903.3 B.4.1 and B.4.2
            new byte[] {
                (byte) 0xAA, (byte) 0xAA,
                (byte) 0x9C, (byte) 0x71,
                (byte) 0x8E, (byte) 0x38,
                (byte) 0x76, (byte) 0x27,
                (byte) 0x4E, (byte) 0xC5,
                (byte) 0x27, (byte) 0x67
            };

    private final byte[] accelerationPlusSigmaBytesFromLegacyBytes =
            // Some rounding vs truncation differences
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xBF,
                (byte) 0x3E, (byte) 0x7F,
                (byte) 0x25, (byte) 0x80,
                (byte) 0x19, (byte) 0x00,
                (byte) 0x0C, (byte) 0x81
            };

    private final byte[] accelerationPlusSigmaAndRhoBytes =
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xC0,
                (byte) 0x3E, (byte) 0x80,
                (byte) 0x25, (byte) 0x80,
                (byte) 0x19, (byte) 0x00,
                (byte) 0x0C, (byte) 0x80,
                (byte) 0x70, (byte) 0x00,
                (byte) 0x60, (byte) 0x00,
                (byte) 0x50, (byte) 0x00
            };

    private final byte[] accelerationPlusSigmaAndRhoBytesLegacyForm =
            // From ST0903.3 B.4.1, B.4.2 and B.4.3
            new byte[] {
                (byte) 0xAA, (byte) 0xAA,
                (byte) 0x9C, (byte) 0x71,
                (byte) 0x8E, (byte) 0x38,
                (byte) 0x76, (byte) 0x27,
                (byte) 0x4E, (byte) 0xC5,
                (byte) 0x27, (byte) 0x67,
                (byte) 0xDF, (byte) 0xFF,
                (byte) 0xBF, (byte) 0xFF,
                (byte) 0x9F, (byte) 0xFF
            };

    private final byte[] accelerationPlusSigmaAndRhoBytesFromLegacyBytes =
            // Some rounding vs truncation differences
            new byte[] {
                (byte) 0x4B, (byte) 0x00,
                (byte) 0x44, (byte) 0xBF,
                (byte) 0x3E, (byte) 0x7F,
                (byte) 0x25, (byte) 0x80,
                (byte) 0x19, (byte) 0x00,
                (byte) 0x0C, (byte) 0x81,
                (byte) 0x6F, (byte) 0xFF,
                (byte) 0x5F, (byte) 0xFF,
                (byte) 0x4F, (byte) 0xFF
            };

    @Test
    public void testConstructFromValue() {
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
        assertEquals(acceleration.getIdentifiers().size(), 3);
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.up));
    }

    @Test
    public void testConstructFromValueWithStandardDeviation() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0);
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
        assertEquals(acceleration.getIdentifiers().size(), 6);
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.up));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigEast));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigNorth));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigUp));
    }

    @Test
    public void testConstructFromValueWithCorrelation() {
        AccelerationPack accelerationPack =
                new AccelerationPack(300.0, 200.0, 100.0, 300.0, 200.0, 100.0, 0.75, 0.5, 0.25);
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
        assertEquals(acceleration.getIdentifiers().size(), 9);
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.up));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigEast));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigNorth));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.sigUp));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.rhoEastNorth));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.rhoEastUp));
        assertTrue(acceleration.getIdentifiers().contains(LocVelAccPackKey.rhoNorthUp));
        assertEquals(
                acceleration.getField(LocVelAccPackKey.east).getDisplayableValue(), "300.0m/s²");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.north).getDisplayableValue(), "200.0m/s²");
        assertEquals(acceleration.getField(LocVelAccPackKey.up).getDisplayableValue(), "100.0m/s²");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.sigEast).getDisplayableValue(), "300.0m/s²");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.sigNorth).getDisplayableValue(),
                "200.0m/s²");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.sigUp).getDisplayableValue(), "100.0m/s²");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.rhoEastNorth).getDisplayableValue(), "0.75");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.rhoEastUp).getDisplayableValue(), "0.50");
        assertEquals(
                acceleration.getField(LocVelAccPackKey.rhoNorthUp).getDisplayableValue(), "0.25");
        assertNull(acceleration.getField(LocVelAccPackKey.unknown));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() {
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
    public void testConstructFromEncodedBytesExplicitEncodingIMAP() {
        Acceleration acceleration = new Acceleration(accelerationBytes, EncodingMode.IMAPB);
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
    public void testConstructFromEncodedBytesExplicitEncodingLegacy() {
        Acceleration acceleration =
                new Acceleration(accelerationBytesLegacyForm, EncodingMode.LEGACY);
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
        assertEquals(acceleration.getBytes(), accelerationBytesFromLegacyBytes);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(VTrackerMetadataKey.acceleration, accelerationBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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
    public void testFactoryWithExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration, accelerationBytes, EncodingMode.IMAPB);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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
    public void testFactoryWithExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration,
                        accelerationBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
        assertEquals(acceleration.getBytes(), accelerationBytesFromLegacyBytes);
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
    @SuppressWarnings("deprecation")
    public void testFactoryWithSigma() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration, accelerationPlusSigmaBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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
    public void testFactoryWithSigmaWithExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration,
                        accelerationPlusSigmaBytes,
                        EncodingMode.IMAPB);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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
    public void testFactoryWithSigmaWithExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration,
                        accelerationPlusSigmaBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaBytesFromLegacyBytes);
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
    @SuppressWarnings("deprecation")
    public void testFactoryWithSigmaAndRho() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration, accelerationPlusSigmaAndRhoBytes);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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

    @Test
    public void testFactoryWithSigmaAndRhoWithExplicitEncodingIMAP() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration,
                        accelerationPlusSigmaAndRhoBytes,
                        EncodingMode.IMAPB);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
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

    @Test
    public void testFactoryWithSigmaAndRhoWithExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTrackerLS.createValue(
                        VTrackerMetadataKey.acceleration,
                        accelerationPlusSigmaAndRhoBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof Acceleration);
        Acceleration acceleration = (Acceleration) value;
        assertEquals(acceleration.getBytes(), accelerationPlusSigmaAndRhoBytesFromLegacyBytes);
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
    public void badArrayLength() {
        new Acceleration(new byte[] {0x01, 0x02, 0x03}, EncodingMode.IMAPB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLegacy() {
        new Acceleration(new byte[] {0x01, 0x02, 0x03}, EncodingMode.LEGACY);
    }

    @Test
    public void testConstructFromValueNullEast() {
        AccelerationPack accelerationPack = new AccelerationPack(null, 29.157890122923, 32.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
        assertEquals(acceleration.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueNullNorth() {
        AccelerationPack accelerationPack = new AccelerationPack(-10.5423886331461, null, 32.0);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
        assertEquals(acceleration.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueNullUp() {
        AccelerationPack accelerationPack =
                new AccelerationPack(-10.5423886331461, 29.157890122923, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 0);
        assertEquals(acceleration.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull1() {
        AccelerationPack accelerationPack =
                new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, null, 3.0, 649.9);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
        assertEquals(acceleration.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull2() {
        AccelerationPack accelerationPack =
                new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, null, 649.9);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
        assertEquals(acceleration.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull3() {
        AccelerationPack accelerationPack =
                new AccelerationPack(-10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 6);
        assertEquals(acceleration.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull1() {
        AccelerationPack accelerationPack =
                new AccelerationPack(
                        -10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, null, 0.0, 0.5);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
        assertEquals(acceleration.getIdentifiers().size(), 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull2() {
        AccelerationPack accelerationPack =
                new AccelerationPack(
                        -10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, null, 0.5);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
        assertEquals(acceleration.getIdentifiers().size(), 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull3() {
        AccelerationPack accelerationPack =
                new AccelerationPack(
                        -10.5423886331461, 29.157890122923, 32.0, 0.1, 3.0, 649.9, -1.0, 0.0, null);
        Acceleration acceleration = new Acceleration(accelerationPack);
        assertEquals(acceleration.getBytes().length, 12);
        assertEquals(acceleration.getIdentifiers().size(), 6);
    }
}
