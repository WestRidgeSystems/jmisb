package org.jmisb.st1603.nanopack;

import org.jmisb.st1603.nanopack.NanoTimeTransferPackKey;
import org.jmisb.st1603.nanopack.NanoTimeTransferPack;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st0603.NanoPrecisionTimeStamp;
import org.jmisb.st1603.localset.ITimeTransferValue;
import org.jmisb.st1603.localset.ST1603DocumentVersion;
import org.jmisb.st1603.localset.TimeTransferKey;
import org.jmisb.st1603.localset.TimeTransferLocalSet;
import org.testng.annotations.Test;

/** Unit tests for NanoTimeTransferPack. */
public class NanoTimeTransferPackTest {

    @Test
    public void fromValues() {
        NanoPrecisionTimeStamp timestamp = new NanoPrecisionTimeStamp(1641720845123456789l);
        Map<TimeTransferKey, ITimeTransferValue> localSetValues = new HashMap<>();
        localSetValues.put(TimeTransferKey.DocumentVersion, new ST1603DocumentVersion(2));
        TimeTransferLocalSet localSet = new TimeTransferLocalSet(localSetValues);
        NanoTimeTransferPack uut = new NanoTimeTransferPack(timestamp, localSet);
        assertNotNull(uut);
        assertEquals(uut.displayHeader(), "Nano Time Transfer Pack");
        assertEquals(uut.getIdentifiers().size(), 2);
        assertTrue(uut.getIdentifiers().contains(NanoTimeTransferPackKey.NanoPrecisionTimeStamp));
        assertTrue(
                uut.getIdentifiers().contains(NanoTimeTransferPackKey.TimeTransferLocalSetValue));
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp)
                        instanceof NanoPrecisionTimeStamp);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp).getDisplayableValue(),
                "1641720845123456789 ns");
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        instanceof TimeTransferLocalSet);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        .getDisplayableValue(),
                "Time Transfer");
        INestedKlvValue localSetAsNested =
                (INestedKlvValue) uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue);
        assertEquals(localSetAsNested.getIdentifiers().size(), 1);
        assertTrue(localSetAsNested.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(
                localSetAsNested.getField(TimeTransferKey.DocumentVersion).getDisplayableValue(),
                "ST 1603.2");
        assertEquals(uut.getTimeStamp().getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getTimeTransferLocalSet().getDisplayableValue(), "Time Transfer");
        assertEquals(
                uut.getUniversalLabel().getBytes(),
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x02, 0x09,
                    0x00, 0x00, 0x00
                });
    }

    @Test
    public void fromBytesNoLocalSet() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x02,
                    0x09,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F,
                    0x15
                };
        NanoTimeTransferPack uut = new NanoTimeTransferPack(bytes);
        assertNotNull(uut);
        assertEquals(uut.displayHeader(), "Nano Time Transfer Pack");
        assertEquals(uut.getIdentifiers().size(), 2);
        assertTrue(uut.getIdentifiers().contains(NanoTimeTransferPackKey.NanoPrecisionTimeStamp));
        assertTrue(
                uut.getIdentifiers().contains(NanoTimeTransferPackKey.TimeTransferLocalSetValue));
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp)
                        instanceof NanoPrecisionTimeStamp);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp).getDisplayableValue(),
                "1641720845123456789 ns");
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        instanceof TimeTransferLocalSet);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        .getDisplayableValue(),
                "Time Transfer");
        INestedKlvValue localSetAsNested =
                (INestedKlvValue) uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue);
        assertEquals(localSetAsNested.getIdentifiers().size(), 0);
        assertEquals(uut.getTimeStamp().getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getTimeTransferLocalSet().getDisplayableValue(), "Time Transfer");
        assertEquals(uut.frameMessage(false), bytes);
        assertEquals(
                uut.frameMessage(true),
                new byte[] {0x16, (byte) 0xC8, (byte) 0x90, 0x69, 0x11, (byte) 0xF0, 0x0F, 0x15});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesTooShort() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x02,
                    0x09,
                    0x00,
                    0x00,
                    0x00,
                    0x07,
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F
                };
        new NanoTimeTransferPack(bytes);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesTooFewBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x02,
                    0x09,
                    0x00,
                    0x00,
                    0x00,
                    0x08,
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F
                };
        new NanoTimeTransferPack(bytes);
    }

    @Test
    public void fromBytesWithLocalSet() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x03,
                    0x02,
                    0x09,
                    0x00,
                    0x00,
                    0x00,
                    0x0B,
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F,
                    0x15,
                    0x01,
                    0x01,
                    0x02
                };
        NanoTimeTransferPack uut = new NanoTimeTransferPack(bytes);
        assertNotNull(uut);
        assertEquals(uut.displayHeader(), "Nano Time Transfer Pack");
        assertEquals(uut.getIdentifiers().size(), 2);
        assertTrue(uut.getIdentifiers().contains(NanoTimeTransferPackKey.NanoPrecisionTimeStamp));
        assertTrue(
                uut.getIdentifiers().contains(NanoTimeTransferPackKey.TimeTransferLocalSetValue));
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp)
                        instanceof NanoPrecisionTimeStamp);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.NanoPrecisionTimeStamp).getDisplayableValue(),
                "1641720845123456789 ns");
        assertTrue(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        instanceof TimeTransferLocalSet);
        assertEquals(
                uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue)
                        .getDisplayableValue(),
                "Time Transfer");
        INestedKlvValue localSetAsNested =
                (INestedKlvValue) uut.getField(NanoTimeTransferPackKey.TimeTransferLocalSetValue);
        assertEquals(localSetAsNested.getIdentifiers().size(), 1);
        assertTrue(localSetAsNested.getIdentifiers().contains(TimeTransferKey.DocumentVersion));
        assertEquals(uut.getTimeStamp().getDisplayableValue(), "1641720845123456789 ns");
        assertEquals(uut.getTimeTransferLocalSet().getDisplayableValue(), "Time Transfer");
        assertEquals(uut.frameMessage(false), bytes);
        assertEquals(
                uut.frameMessage(true),
                new byte[] {
                    0x16,
                    (byte) 0xC8,
                    (byte) 0x90,
                    0x69,
                    0x11,
                    (byte) 0xF0,
                    0x0F,
                    0x15,
                    0x01,
                    0x01,
                    0x02
                });
    }
}
