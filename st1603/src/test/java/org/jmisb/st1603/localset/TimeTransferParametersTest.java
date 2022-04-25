package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.TimeTransferParameters;
import org.jmisb.st1603.localset.TimeTransferMethod;
import org.jmisb.st1603.localset.ReferenceSource;
import org.jmisb.st1603.localset.TimeTransferParametersKey;
import org.jmisb.st1603.localset.CorrectionMethod;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.testng.annotations.Test;

/** Tests for Time Transfer Parameters. */
public class TimeTransferParametersTest {
    @Test
    public void testConstructFromValue() {
        TimeTransferParameters uut =
                new TimeTransferParameters(
                        ReferenceSource.Unknown,
                        CorrectionMethod.Unknown,
                        TimeTransferMethod.Unknown);
        assertEquals(uut.getReferenceSource(), ReferenceSource.Unknown);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.Unknown);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.Unknown);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource), ReferenceSource.Unknown);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod), CorrectionMethod.Unknown);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.Unknown);
    }

    @Test
    public void testConstructFromValueSyncJamNTP() {
        TimeTransferParameters uut =
                new TimeTransferParameters(
                        ReferenceSource.Synchronized,
                        CorrectionMethod.JamCorrection,
                        TimeTransferMethod.NetworkTimeProtocolV3);
        assertEquals(uut.getReferenceSource(), ReferenceSource.Synchronized);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.JamCorrection);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.NetworkTimeProtocolV3);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01000110});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.Synchronized);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.JamCorrection);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.NetworkTimeProtocolV3);
    }

    @Test
    public void testConstructFromValueNotSyncSlewReserved15() {
        TimeTransferParameters uut =
                new TimeTransferParameters(
                        ReferenceSource.NotSynchronized,
                        CorrectionMethod.SlewCorrection,
                        TimeTransferMethod.Reserved15);
        assertEquals(uut.getReferenceSource(), ReferenceSource.NotSynchronized);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.SlewCorrection);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.Reserved15);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b11111001});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.NotSynchronized);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.SlewCorrection);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.Reserved15);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.NotSynchronized);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.SlewCorrection);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.Reserved15);
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        TimeTransferParameters uut = new TimeTransferParameters(new byte[] {0x00});
        assertEquals(uut.getReferenceSource(), ReferenceSource.Unknown);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.Unknown);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.Unknown);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource), ReferenceSource.Unknown);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod), CorrectionMethod.Unknown);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.Unknown);
    }

    @Test
    public void testConstructFromBytesSyncJamNTP() throws KlvParseException {
        TimeTransferParameters uut = new TimeTransferParameters(new byte[] {(byte) 0x46});
        assertEquals(uut.getReferenceSource(), ReferenceSource.Synchronized);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.JamCorrection);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.NetworkTimeProtocolV3);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01000110});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.Synchronized);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.JamCorrection);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.NetworkTimeProtocolV3);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.Synchronized);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.JamCorrection);
        assertEquals(
                uut.getField((IKlvKey) TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.NetworkTimeProtocolV3);
    }

    @Test
    public void testConstructFromBytesOffsetSyncJamNTP() throws KlvParseException {
        TimeTransferParameters uut =
                new TimeTransferParameters(new byte[] {(byte) 0xFF, (byte) 0x46});
        assertEquals(uut.getReferenceSource(), ReferenceSource.Synchronized);
        assertEquals(uut.getCorrectionMethod(), CorrectionMethod.JamCorrection);
        assertEquals(uut.getTimeTransferMethod(), TimeTransferMethod.NetworkTimeProtocolV3);
        assertEquals(uut.getDisplayName(), "Time Transfer Parameters");
        assertEquals(uut.getDisplayableValue(), "Parameters");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0b01000110});
        assertEquals(uut.getIdentifiers().size(), 3);
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.ReferenceSource));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.CorrectionMethod));
        assertTrue(uut.getIdentifiers().contains(TimeTransferParametersKey.TimeTransferMethod));
        assertEquals(
                uut.getField(TimeTransferParametersKey.ReferenceSource),
                ReferenceSource.Synchronized);
        assertEquals(
                uut.getField(TimeTransferParametersKey.CorrectionMethod),
                CorrectionMethod.JamCorrection);
        assertEquals(
                uut.getField(TimeTransferParametersKey.TimeTransferMethod),
                TimeTransferMethod.NetworkTimeProtocolV3);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadArrayLength() throws KlvParseException {
        new TimeTransferParameters(new byte[0]);
    }
}
