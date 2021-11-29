package org.jmisb.api.klv.st1205;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class CalibrationSequenceIdentifierTest {
    @Test
    public void testConstructFromValue() {
        // Min
        CalibrationSequenceIdentifier identifier = new CalibrationSequenceIdentifier(0);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x00});

        // Max
        identifier = new CalibrationSequenceIdentifier(255);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0xff});

        identifier = new CalibrationSequenceIdentifier(159);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x9f});

        assertEquals(identifier.getDisplayName(), "Calibration Sequence Identifier");
        assertEquals(identifier.getSequenceIdentifier(), 159);
    }

    @Test
    public void testConstructFromEncoded() throws KlvParseException {
        // Min
        CalibrationSequenceIdentifier identifier =
                new CalibrationSequenceIdentifier(new byte[] {(byte) 0x00});
        assertEquals(identifier.getSequenceIdentifier(), 0);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(identifier.getDisplayableValue(), "000");

        // Max
        identifier = new CalibrationSequenceIdentifier(new byte[] {(byte) 0xff});
        assertEquals(identifier.getSequenceIdentifier(), 255);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0xff});
        assertEquals(identifier.getDisplayableValue(), "255");

        identifier = new CalibrationSequenceIdentifier(new byte[] {(byte) 0x9f});
        assertEquals(identifier.getSequenceIdentifier(), 159);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x9f});
        assertEquals(identifier.getDisplayableValue(), "159");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01};
        ICalibrationMetadataValue v =
                CalibrationLocalSet.createValue(
                        CalibrationPackMetadataKey.CalibrationSequenceIdentifier, bytes);
        assertTrue(v instanceof CalibrationSequenceIdentifier);
        CalibrationSequenceIdentifier identifier = (CalibrationSequenceIdentifier) v;
        assertEquals(identifier.getSequenceIdentifier(), 1);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x01});
        assertEquals(identifier.getDisplayableValue(), "001");

        bytes = new byte[] {(byte) 0x9f};
        v =
                CalibrationLocalSet.createValue(
                        CalibrationPackMetadataKey.CalibrationSequenceIdentifier, bytes);
        assertTrue(v instanceof CalibrationSequenceIdentifier);
        identifier = (CalibrationSequenceIdentifier) v;
        assertEquals(identifier.getSequenceIdentifier(), 159);
        assertEquals(identifier.getBytes(), new byte[] {(byte) 0x9f});
        assertEquals(identifier.getDisplayableValue(), "159");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new CalibrationSequenceIdentifier(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new CalibrationSequenceIdentifier(256);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badArrayLength() throws KlvParseException {
        new CalibrationSequenceIdentifier(new byte[] {0x01, 0x02});
    }
}
