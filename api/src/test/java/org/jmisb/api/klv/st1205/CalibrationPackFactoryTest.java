package org.jmisb.api.klv.st1205;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST 1205 Calibration Pack Factory. */
public class CalibrationPackFactoryTest extends LoggerChecks {
    public CalibrationPackFactoryTest() {
        super(CalibrationPackFactory.class);
    }

    @Test
    public void parsePack() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x06,
                    0x00,
                    0x00,
                    0x00,
                    0x1B,
                    0x01,
                    0x08,
                    0x05,
                    (byte) 0xd1,
                    (byte) 0xf5,
                    0x0d,
                    (byte) 0xd4,
                    0x04,
                    0x26,
                    0x01,
                    0x02,
                    0x02,
                    0x08,
                    0x04,
                    0x03,
                    0x08,
                    0x05,
                    (byte) 0xd1,
                    (byte) 0xf5,
                    0x0d,
                    (byte) 0xd3,
                    0x08,
                    0x09,
                    0x01,
                    0x04,
                    0x01,
                    0x03
                };
        CalibrationPackFactory factory = new CalibrationPackFactory();
        CalibrationLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 4);
        assertTrue(
                localSet.getIdentifiers()
                        .contains(CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence));
        assertTrue(localSet.getIdentifiers().contains(CalibrationPackMetadataKey.SequenceDuration));
        assertTrue(
                localSet.getIdentifiers()
                        .contains(CalibrationPackMetadataKey.TimeStampOfCalibrationPackCreation));
        assertTrue(
                localSet.getIdentifiers()
                        .contains(CalibrationPackMetadataKey.CalibrationSequenceIdentifier));
        assertFalse(localSet.getIdentifiers().contains(CalibrationPackMetadataKey.Undefined));
        assertEquals(
                localSet.getField(CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence)
                        .getDisplayableValue(),
                "419385680062588417");
        assertEquals(
                localSet.getField(CalibrationPackMetadataKey.SequenceDuration)
                        .getDisplayableValue(),
                "2052");
        assertEquals(
                localSet.getField(CalibrationPackMetadataKey.TimeStampOfCalibrationPackCreation)
                        .getDisplayableValue(),
                "419385680046065921");
        assertEquals(
                localSet.getField(CalibrationPackMetadataKey.CalibrationSequenceIdentifier)
                        .getDisplayableValue(),
                "003");
        assertEquals(localSet.frameMessage(false), bytes);
    }
}
