package org.jmisb.api.klv.st1205;

import static org.testng.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST 1205 Calibration Pack. */
public class CalibrationLocalSetTest extends LoggerChecks {
    public CalibrationLocalSetTest() {
        super(CalibrationLocalSet.class);
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
        CalibrationLocalSet localSet = new CalibrationLocalSet(bytes);
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

    @Test
    public void parsePackWithUnknownValues() throws KlvParseException {
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
                    0x1F,
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
                    0x70,
                    0x02,
                    0x00,
                    0x00,
                    0x04,
                    0x01,
                    0x03
                };
        verifyNoLoggerMessages();
        CalibrationLocalSet localSet = new CalibrationLocalSet(bytes);
        verifySingleLoggerMessage("Unknown Calibration Pack Metadata tag: 112");
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 4);
    }

    @Test
    public void constructFromMap() {
        Map<CalibrationPackMetadataKey, ICalibrationMetadataValue> values = new HashMap<>();
        values.put(CalibrationPackMetadataKey.SequenceDuration, new SequenceDuration(1350));
        values.put(
                CalibrationPackMetadataKey.CalibrationSequenceIdentifier,
                new CalibrationSequenceIdentifier(8));
        values.put(
                CalibrationPackMetadataKey.TimeStampOfCalibrationPackCreation,
                new TimeStampOfCalibrationPackCreation(
                        LocalDateTime.of(2021, Month.NOVEMBER, 30, 01, 45, 03, 0)));
        values.put(
                CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence,
                new TimeStampOfLastFrameInSequence(
                        LocalDateTime.of(2021, Month.NOVEMBER, 30, 01, 45, 03, 30)));
        CalibrationLocalSet localSet = new CalibrationLocalSet(values);
        assertTrue(localSet instanceof IMisbMessage);
        byte[] messageBytes = localSet.frameMessage(false);
        assertEquals(
                messageBytes,
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
                    0x00,
                    0x05,
                    (byte) 0xd1,
                    (byte) 0xf7,
                    (byte) 0xb4,
                    (byte) 0xbd,
                    (byte) 0x85,
                    (byte) 0xc0,
                    0x02,
                    0x02,
                    0x05,
                    0x46,
                    0x03,
                    0x08,
                    0x00,
                    0x05,
                    (byte) 0xd1,
                    (byte) 0xf7,
                    (byte) 0xb4,
                    (byte) 0xbd,
                    (byte) 0x85,
                    (byte) 0xc0,
                    0x04,
                    0x01,
                    0x08
                });
        assertEquals(localSet.displayHeader(), "ST 1205 Calibration Pack");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.CalibrationPackUl);
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
        assertEquals(
                localSet.getField(CalibrationPackMetadataKey.CalibrationSequenceIdentifier)
                        .getDisplayableValue(),
                "008");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructFromMapNested() {
        Map<CalibrationPackMetadataKey, ICalibrationMetadataValue> values = new HashMap<>();
        values.put(CalibrationPackMetadataKey.SequenceDuration, new SequenceDuration(1350));
        values.put(
                CalibrationPackMetadataKey.CalibrationSequenceIdentifier,
                new CalibrationSequenceIdentifier(8));
        values.put(
                CalibrationPackMetadataKey.TimeStampOfCalibrationPackCreation,
                new TimeStampOfCalibrationPackCreation(
                        LocalDateTime.of(2021, Month.NOVEMBER, 30, 01, 45, 03, 0)));
        values.put(
                CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence,
                new TimeStampOfLastFrameInSequence(
                        LocalDateTime.of(2021, Month.NOVEMBER, 30, 01, 45, 03, 30)));
        CalibrationLocalSet localSet = new CalibrationLocalSet(values);
        assertTrue(localSet instanceof IMisbMessage);
        localSet.frameMessage(true);
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        ICalibrationMetadataValue unknown =
                CalibrationLocalSet.createValue(
                        CalibrationPackMetadataKey.Undefined, new byte[] {0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown Calibration Pack Metadata tag: Undefined");
        assertNull(unknown);
    }
}
