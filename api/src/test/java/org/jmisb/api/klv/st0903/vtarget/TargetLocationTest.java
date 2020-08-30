package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.LocVelAccPackKey;
import org.jmisb.api.klv.st0903.shared.LocationPack;
import org.testng.annotations.Test;

/** Tests for Target Location (Tag 17) */
public class TargetLocationTest {
    private final byte[] coordinateBytes =
            new byte[] {
                (byte) 0x27, (byte) 0xba, (byte) 0x93, (byte) 0x02,
                (byte) 0x34, (byte) 0x4a, (byte) 0x1a, (byte) 0xdf,
                (byte) 0x10, (byte) 0x14
            };

    private final byte[] coordinateBytesLegacyForm =
            new byte[] {
                // ST0903.3 B.2.1
                (byte) 0xBD, (byte) 0x27, (byte) 0xD2, (byte) 0x7C,
                // ST0903.3 B.2.2
                (byte) 0xCE, (byte) 0x38, (byte) 0xE3, (byte) 0x8D,
                // ST0903.3 B.2.3
                (byte) 0x8C, (byte) 0x38
            };

    private final byte[] coordinatePlusSigmaBytes =
            new byte[] {
                (byte) 0x27,
                (byte) 0xba,
                (byte) 0x93,
                (byte) 0x02,
                (byte) 0x34,
                (byte) 0x4a,
                (byte) 0x1a,
                (byte) 0xdf,
                (byte) 0x10,
                (byte) 0x14,
                (byte) 0x00,
                (byte) 0x03,
                (byte) 0x00,
                (byte) 0x60,
                (byte) 0x51,
                (byte) 0x3C
            };

    private final byte[] coordinatePlusSigmaBytesLegacyForm =
            new byte[] {
                // ST0903.3 B.2.1
                (byte) 0xBD,
                (byte) 0x27,
                (byte) 0xD2,
                (byte) 0x7C,
                // ST0903.3 B.2.2
                (byte) 0xCE,
                (byte) 0x38,
                (byte) 0xE3,
                (byte) 0x8D,
                // ST0903.3 B.2.3
                (byte) 0x8C,
                (byte) 0x38,
                // ST0903.3 B.2.4
                (byte) 0x76,
                (byte) 0x27,
                (byte) 0x4E,
                (byte) 0xC5,
                (byte) 0x27,
                (byte) 0x62, // Note: 0x67 in the summary appears to be an error
            };

    private final byte[] coordinatePlusSigmaAndRhoBytes =
            new byte[] {
                (byte) 0x27,
                (byte) 0xba,
                (byte) 0x93,
                (byte) 0x02,
                (byte) 0x34,
                (byte) 0x4a,
                (byte) 0x1a,
                (byte) 0xdf,
                (byte) 0x10,
                (byte) 0x14,
                (byte) 0x00,
                (byte) 0x03,
                (byte) 0x00,
                (byte) 0x60,
                (byte) 0x51,
                (byte) 0x3C,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x60,
                (byte) 0x00
            };

    private final byte[] coordinatePlusSigmaAndRhoBytesLegacyForm =
            new byte[] {
                // ST0903.3 B.2.1
                (byte) 0xBD,
                (byte) 0x27,
                (byte) 0xD2,
                (byte) 0x7C,
                // ST0903.3 B.2.2
                (byte) 0xCE,
                (byte) 0x38,
                (byte) 0xE3,
                (byte) 0x8D,
                // ST0903.3 B.2.3
                (byte) 0x8C,
                (byte) 0x38,
                // ST0903.3 B.2.4
                (byte) 0x76,
                (byte) 0x27,
                (byte) 0x4E,
                (byte) 0xC5,
                (byte) 0x27,
                (byte) 0x62, // Also shown as 0x67
                // ST0903.3 B.2.5
                (byte) 0xDF,
                (byte) 0xFF,
                (byte) 0xBF,
                (byte) 0xFF,
                (byte) 0x9F,
                (byte) 0xFF
            };

    @Test
    public void testConstructFromValue() {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, 3216.0);
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
        assertEquals(location.getIdentifiers().size(), 3);
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.up));
    }

    @Test
    public void testConstructFromValueWithStandardDeviation() {
        LocationPack locPack =
                new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, 649.9);
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
        assertEquals(location.getIdentifiers().size(), 6);
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.up));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigEast));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigNorth));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigUp));
    }

    @Test
    public void testConstructFromValueWithCorrelation() {
        LocationPack locPack =
                new LocationPack(
                        -10.5423886331461,
                        29.157890122923,
                        3216.0,
                        0.1,
                        3.0,
                        649.9,
                        -1.0,
                        0.0,
                        0.5);
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
        assertEquals(location.getIdentifiers().size(), 9);
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.east));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.north));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.up));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigEast));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigNorth));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.sigUp));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.rhoEastNorth));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.rhoEastUp));
        assertTrue(location.getIdentifiers().contains(LocVelAccPackKey.rhoNorthUp));
        assertEquals(location.getField(LocVelAccPackKey.east).getDisplayableValue(), "-10.5424°");
        assertEquals(location.getField(LocVelAccPackKey.north).getDisplayableValue(), "29.1579°");
        assertEquals(location.getField(LocVelAccPackKey.up).getDisplayableValue(), "3216.0m");
        assertEquals(location.getField(LocVelAccPackKey.sigEast).getDisplayableValue(), "0.1m");
        assertEquals(location.getField(LocVelAccPackKey.sigNorth).getDisplayableValue(), "3.0m");
        assertEquals(location.getField(LocVelAccPackKey.sigUp).getDisplayableValue(), "649.9m");
        assertEquals(
                location.getField(LocVelAccPackKey.rhoEastNorth).getDisplayableValue(), "-1.00");
        assertEquals(location.getField(LocVelAccPackKey.rhoEastUp).getDisplayableValue(), "0.00");
        assertEquals(location.getField(LocVelAccPackKey.rhoNorthUp).getDisplayableValue(), "0.50");
        assertNull(location.getField(LocVelAccPackKey.unknown));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testConstructFromEncodedBytes() {
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
    @SuppressWarnings("deprecation")
    public void testLocationPackParseFromEncodedBytes() {
        LocationPack location = TargetLocation.targetLocationPackFromBytes(coordinateBytes);
        assertEquals(location.getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getHae(), 3216.0, 0.02);
        assertNull(location.getSigEast());
        assertNull(location.getSigNorth());
        assertNull(location.getSigUp());
        assertNull(location.getRhoEastNorth());
        assertNull(location.getRhoEastUp());
        assertNull(location.getRhoNorthUp());
    }

    @Test
    public void testLocationPackParseFromEncodedBytesExplicitEncodingIMAP() {
        LocationPack location =
                TargetLocation.targetLocationPackFromBytes(coordinateBytes, EncodingMode.IMAPB);
        assertEquals(location.getLat(), -10.5423886331461, 0.0001);
        assertEquals(location.getLon(), 29.157890122923, 0.0001);
        assertEquals(location.getHae(), 3216.0, 0.02);
        assertNull(location.getSigEast());
        assertNull(location.getSigNorth());
        assertNull(location.getSigUp());
        assertNull(location.getRhoEastNorth());
        assertNull(location.getRhoEastUp());
        assertNull(location.getRhoNorthUp());
    }

    @Test
    public void testLocationPackParseFromEncodedBytesExplicitEncodingLegacy() {
        LocationPack location =
                TargetLocation.targetLocationPackFromBytes(
                        coordinateBytesLegacyForm, EncodingMode.LEGACY);
        assertEquals(location.getLat(), 43.0, 0.00001);
        assertEquals(location.getLon(), 110.0, 0.00001);
        assertEquals(location.getHae(), 10000.0, 0.4);
        assertNull(location.getSigEast());
        assertNull(location.getSigNorth());
        assertNull(location.getSigUp());
        assertNull(location.getRhoEastNorth());
        assertNull(location.getRhoEastUp());
        assertNull(location.getRhoNorthUp());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactory() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(VTargetMetadataKey.TargetLocation, coordinateBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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
    public void testFactoryWithExplicitEncodingIMAPB() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation, coordinateBytes, EncodingMode.IMAPB);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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
    public void testFactoryWithExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation,
                        coordinateBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
        assertEquals(location.getTargetLocation().getLat(), 43.0, 0.00001);
        assertEquals(location.getTargetLocation().getLon(), 110.0, 0.00001);
        assertEquals(location.getTargetLocation().getHae(), 10000.0, 0.4);
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
    @SuppressWarnings("deprecation")
    public void testFactoryWithSigma() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation, coordinatePlusSigmaBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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
    public void testFactoryWithSigmaWithExpicitEncodingIMAPB() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation,
                        coordinatePlusSigmaBytes,
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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
    public void testFactoryWithSigmaWithExpicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation,
                        coordinatePlusSigmaBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
        assertEquals(location.getTargetLocation().getLat(), 43.0, 0.00001);
        assertEquals(location.getTargetLocation().getLon(), 110.0, 0.00001);
        assertEquals(location.getTargetLocation().getHae(), 10000.0, 0.4);
        assertEquals(location.getTargetLocation().getSigEast(), 300, 0.01);
        assertEquals(location.getTargetLocation().getSigNorth(), 200, 0.01);
        assertEquals(location.getTargetLocation().getSigUp(), 100, 0.01);
        assertNull(location.getTargetLocation().getRhoEastNorth());
        assertNull(location.getTargetLocation().getRhoEastUp());
        assertNull(location.getTargetLocation().getRhoNorthUp());
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFactoryWithSigmaAndRho() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation, coordinatePlusSigmaAndRhoBytes);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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

    @Test
    public void testFactoryWithSigmaAndRhoWithExplicitEncodingIMAPB() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation,
                        coordinatePlusSigmaAndRhoBytes,
                        EncodingMode.IMAPB);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
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

    @Test
    public void testFactoryWithSigmaAndRhoWithExplicitEncodingLegacy() throws KlvParseException {
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.TargetLocation,
                        coordinatePlusSigmaAndRhoBytesLegacyForm,
                        EncodingMode.LEGACY);
        assertTrue(value instanceof TargetLocation);
        TargetLocation location = (TargetLocation) value;
        assertEquals(location.getTargetLocation().getLat(), 43.0, 0.00001);
        assertEquals(location.getTargetLocation().getLon(), 110.0, 0.00001);
        assertEquals(location.getTargetLocation().getHae(), 10000.0, 0.4);
        assertEquals(location.getTargetLocation().getSigEast(), 300, 0.01);
        assertEquals(location.getTargetLocation().getSigNorth(), 200, 0.01);
        assertEquals(location.getTargetLocation().getSigUp(), 100, 0.01);
        assertEquals(location.getTargetLocation().getRhoEastNorth(), 0.75, 0.001);
        assertEquals(location.getTargetLocation().getRhoEastUp(), 0.50, 0.001);
        assertEquals(location.getTargetLocation().getRhoNorthUp(), 0.25, 0.001);
        assertEquals(location.getDisplayName(), "Target Location");
        assertEquals(location.getDisplayableValue(), "[Location]");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthIMAPB() {
        new TargetLocation(new byte[] {0x01, 0x02, 0x03}, EncodingMode.IMAPB);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLegacy() {
        new TargetLocation(new byte[] {0x01, 0x02, 0x03}, EncodingMode.LEGACY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLegacyStaticMethod() {
        TargetLocation.targetLocationPackFromBytes(
                new byte[] {0x01, 0x02, 0x03}, EncodingMode.LEGACY);
    }

    @Test
    public void testConstructFromValueNullLat() {
        LocationPack locPack = new LocationPack(null, 29.157890122923, 3216.0);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
        assertEquals(location.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueNullLon() {
        LocationPack locPack = new LocationPack(-10.5423886331461, null, 3216.0);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
        assertEquals(location.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueNullAlt() {
        LocationPack locPack = new LocationPack(-10.5423886331461, 29.157890122923, null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 0);
        assertEquals(location.getIdentifiers().size(), 0);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull1() {
        LocationPack locPack =
                new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, null, 3.0, 649.9);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
        assertEquals(location.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull2() {
        LocationPack locPack =
                new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, null, 649.9);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
        assertEquals(location.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithStandardDeviationNull3() {
        LocationPack locPack =
                new LocationPack(-10.5423886331461, 29.157890122923, 3216.0, 0.1, 3.0, null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 10);
        assertEquals(location.getIdentifiers().size(), 3);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull1() {
        LocationPack locPack =
                new LocationPack(
                        -10.5423886331461,
                        29.157890122923,
                        3216.0,
                        0.1,
                        3.0,
                        649.9,
                        null,
                        0.0,
                        0.5);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
        assertEquals(location.getIdentifiers().size(), 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull2() {
        LocationPack locPack =
                new LocationPack(
                        -10.5423886331461,
                        29.157890122923,
                        3216.0,
                        0.1,
                        3.0,
                        649.9,
                        -1.0,
                        null,
                        0.5);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
        assertEquals(location.getIdentifiers().size(), 6);
    }

    @Test
    public void testConstructFromValueWithCorrelationNull3() {
        LocationPack locPack =
                new LocationPack(
                        -10.5423886331461,
                        29.157890122923,
                        3216.0,
                        0.1,
                        3.0,
                        649.9,
                        -1.0,
                        0.0,
                        null);
        TargetLocation location = new TargetLocation(locPack);
        assertEquals(location.getBytes().length, 16);
        assertEquals(location.getIdentifiers().size(), 6);
    }
}
