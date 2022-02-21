package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Unit tests for Time Transfer Local Set implementation. */
public class TimeTransferLocalSetTest extends LoggerChecks {

    public TimeTransferLocalSetTest() {
        super(TimeTransferLocalSet.class);
    }

    @Test
    public void checkFromBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                };
        TimeTransferLocalSet uut = new TimeTransferLocalSet(bytes);
        verifyNoLoggerMessages();
        assertEquals(uut.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(
                uut.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(), "ST 1603.2");
        assertEquals(uut.frameMessage(false), bytes);
        assertEquals(uut.frameMessage(true), new byte[] {0x01, 0x01, 0x02});
    }

    @Test
    public void checkFromBytesWithUnknown() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x07, 0x70, 0x02, 0x01, 0x02, 0x01, 0x01, 0x02
                };
        verifyNoLoggerMessages();
        TimeTransferLocalSet uut = new TimeTransferLocalSet(bytes);
        this.verifySingleLoggerMessage("Unknown Time Transfer tag: Undefined");
        assertEquals(uut.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(
                uut.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(), "ST 1603.2");
        assertEquals(
                uut.frameMessage(false),
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                });
        assertEquals(uut.frameMessage(true), new byte[] {0x01, 0x01, 0x02});
    }

    @Test
    public void checkFromValues() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                };
        Map<TimeTransferKey, ITimeTransferValue> map = new HashMap<>();
        map.put(TimeTransferKey.DocumentVersion, new ST1603DocumentVersion(2));
        TimeTransferLocalSet uut = new TimeTransferLocalSet(map);
        verifyNoLoggerMessages();
        assertEquals(uut.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(
                uut.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(), "ST 1603.2");
        assertEquals(uut.frameMessage(false), bytes);
        verifyNoLoggerMessages();
        assertEquals(uut.frameMessage(true), new byte[] {0x01, 0x01, 0x02});
        verifyNoLoggerMessages();
    }

    @Test
    public void checkFrameMessageWithUndefined() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x03, 0x01, 0x01, 0x02
                };
        Map<TimeTransferKey, ITimeTransferValue> map = new HashMap<>();
        map.put(
                TimeTransferKey.Undefined,
                new ITimeTransferValue() {
                    @Override
                    public String getDisplayName() {
                        return "Faked display name";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "Fake displayable value";
                    }

                    @Override
                    public byte[] getBytes() {
                        return new byte[] {0x01};
                    }
                });
        map.put(TimeTransferKey.DocumentVersion, new ST1603DocumentVersion(2));
        TimeTransferLocalSet uut = new TimeTransferLocalSet(map);
        verifyNoLoggerMessages();
        assertEquals(uut.frameMessage(false), bytes);
        this.verifySingleLoggerMessage("Skipping undefined Time Transfer tag: 0");
        assertEquals(uut.frameMessage(true), new byte[] {0x01, 0x01, 0x02});
        this.verifySingleLoggerMessage("Skipping undefined Time Transfer tag: 0");
    }

    @Test
    public void checkFromBytesRequired() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0xB, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x09, 0x01, 0x01, 0x02, 0x02, 0x01, 0x25, 0x03, 0x01, 0x52,
                };
        verifyNoLoggerMessages();
        TimeTransferLocalSet uut = new TimeTransferLocalSet(bytes);
        verifyNoLoggerMessages();
        assertEquals(uut.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.UTCLeapSecondOffset));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.TimeTransferParameters));
        assertEquals(
                uut.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(), "ST 1603.2");
        assertEquals(
                uut.getField(TimeTransferKey.UTCLeapSecondOffset).getDisplayableValue(),
                "37 seconds");
        assertEquals(
                uut.getField(TimeTransferKey.TimeTransferParameters).getDisplayableValue(),
                "Parameters");
        TimeTransferParameters parameters =
                (TimeTransferParameters) uut.getField(TimeTransferKey.TimeTransferParameters);
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.ReferenceSource)
                        .getDisplayableValue(),
                "Reference Source is synchronized to an atomic source");
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.CorrectionMethod)
                        .getDisplayableValue(),
                "Unknown or No Correction");
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.TimeTransferMethod)
                        .getDisplayableValue(),
                "Network Time Protocol (NTP) Version 4");
        assertEquals(uut.frameMessage(false), bytes);
        assertEquals(
                uut.frameMessage(true),
                new byte[] {0x01, 0x01, 0x02, 0x02, 0x01, 0x25, 0x03, 0x01, 0x52});
    }

    @Test
    public void checkFromBytesAll() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0xB,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x02,
                    0x02,
                    0x00,
                    0x00,
                    0x00,
                    0x31,
                    0x01,
                    0x01,
                    0x02,
                    0x02,
                    0x01,
                    0x25,
                    0x03,
                    0x01,
                    0x39,
                    0x04,
                    0x08,
                    0x3F,
                    (byte) 0xC0,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x05,
                    0x05,
                    0x02,
                    (byte) 0xcb,
                    0x41,
                    0x78,
                    0x00,
                    0x06,
                    0x03,
                    0x08,
                    (byte) 0xF1,
                    (byte) 0x10,
                    0x07,
                    0x08,
                    0x40,
                    0x32,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x08,
                    0x02,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    0x09,
                    0x02,
                    0x03,
                    (byte) 0xe8
                };
        verifyNoLoggerMessages();
        TimeTransferLocalSet uut = new TimeTransferLocalSet(bytes);
        verifyNoLoggerMessages();
        assertEquals(uut.getUniversalLabel(), TimeTransferLocalSet.TimeTransferLocalSetUl);
        assertEquals(uut.displayHeader(), "ST 1603 Time Transfer");
        assertEquals(uut.getIdentifiers().size(), 9);
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.UTCLeapSecondOffset));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.TimeTransferParameters));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.SynchronizationPulseFrequency));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.UnlockTime));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.LastSynchronizationDifference));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.DriftRate));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.SignalSourceDelay));
        assertTrue(uut.getIdentifiers().contains(TimeTransferKey.ReceptorClockUncertainty));
        assertEquals(
                uut.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(), "ST 1603.2");
        assertEquals(
                uut.getField(TimeTransferKey.UTCLeapSecondOffset).getDisplayableValue(),
                "37 seconds");
        assertEquals(
                uut.getField(TimeTransferKey.TimeTransferParameters).getDisplayableValue(),
                "Parameters");
        TimeTransferParameters parameters =
                (TimeTransferParameters) uut.getField(TimeTransferKey.TimeTransferParameters);
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.ReferenceSource)
                        .getDisplayableValue(),
                "Reference Source is not synchronized to an atomic source");
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.CorrectionMethod)
                        .getDisplayableValue(),
                "Slew Correction");
        assertEquals(
                parameters
                        .getField(TimeTransferParametersKey.TimeTransferMethod)
                        .getDisplayableValue(),
                "Precision Time Protocol (PTP) Version 2");
        assertEquals(
                uut.getField(TimeTransferKey.SynchronizationPulseFrequency).getDisplayableValue(),
                "0.1250 Hz");
        assertEquals(uut.getField(TimeTransferKey.UnlockTime).getDisplayableValue(), "12000000000");
        assertEquals(
                uut.getField(TimeTransferKey.LastSynchronizationDifference).getDisplayableValue(),
                "586000");
        assertEquals(uut.getField(TimeTransferKey.DriftRate).getDisplayableValue(), "18.2000 µs/s");
        assertEquals(
                uut.getField(TimeTransferKey.SignalSourceDelay).getDisplayableValue(), "65535 ns");
        assertEquals(
                uut.getField(TimeTransferKey.ReceptorClockUncertainty).getDisplayableValue(),
                "1000");
        assertEquals(uut.frameMessage(false), bytes);
        assertEquals(
                uut.frameMessage(true),
                new byte[] {
                    0x01,
                    0x01,
                    0x02,
                    0x02,
                    0x01,
                    0x25,
                    0x03,
                    0x01,
                    0x39,
                    0x04,
                    0x08,
                    0x3F,
                    (byte) 0xC0,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x05,
                    0x05,
                    0x02,
                    (byte) 0xcb,
                    0x41,
                    0x78,
                    0x00,
                    0x06,
                    0x03,
                    0x08,
                    (byte) 0xF1,
                    (byte) 0x10,
                    0x07,
                    0x08,
                    0x40,
                    0x32,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x33,
                    0x08,
                    0x02,
                    (byte) 0xFF,
                    (byte) 0xFF,
                    0x09,
                    0x02,
                    0x03,
                    (byte) 0xe8
                });
    }
}
