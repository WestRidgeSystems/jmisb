package org.jmisb.st0903.vtrack;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.st0903.MiisCoreIdentifier;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.st0903.shared.LoggerChecks;
import org.jmisb.st0903.vtarget.TargetPriority;
import org.testng.annotations.Test;

/** Unit tests for VTrackItem. */
public class VTrackItemTest extends LoggerChecks {

    public VTrackItemTest() {
        super(VTrackItem.class);
    }

    @Test
    public void checkVTrackItemFromBytes() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x07, 0x01, 0x03};
        VTrackItem uut = new VTrackItem(bytes, 0, bytes.length, EncodingMode.IMAPB);
        assertEquals(uut.getDisplayName(), "VTrackItem");
        assertEquals(uut.getDisplayableValue(), "Track Item 1");
        assertEquals(uut.getTargetIdentifier(), 1);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
        TargetPriority targetPriorityFromIKlvKey =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriorityFromIKlvKey.getTargetPriority(), 3);
        TargetPriority targetPriority =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriority.getTargetPriority(), 3);
        assertEquals(uut.getBytes(), bytes);
    }

    @Test
    public void checkVTrackItemFromMap() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x07, 0x01, 0x03};
        Map<VTrackItemMetadataKey, IVTrackItemMetadataValue> map = new HashMap<>();
        map.put(VTrackItemMetadataKey.TargetPriority, new TargetPriority((short) 3));
        VTrackItem uut = new VTrackItem(0x01, map);
        assertEquals(uut.getDisplayName(), "VTrackItem");
        assertEquals(uut.getDisplayableValue(), "Track Item 1");
        assertEquals(uut.getTargetIdentifier(), 1);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
        TargetPriority targetPriorityFromIKlvKey =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriorityFromIKlvKey.getTargetPriority(), 3);
        TargetPriority targetPriority =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriority.getTargetPriority(), 3);
        assertEquals(uut.getBytes(), bytes);
    }

    @Test
    public void checkVTrackItemFromBytesWithUnknownKey() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x7F, 0x01, 0x06, 0x07, 0x01, 0x03};
        this.verifyNoLoggerMessages();
        VTrackItem uut = new VTrackItem(bytes, 0, bytes.length, EncodingMode.IMAPB);
        this.verifySingleLoggerMessage("Unknown VTrackItem Metadata tag: 127");
        assertEquals(uut.getDisplayName(), "VTrackItem");
        assertEquals(uut.getDisplayableValue(), "Track Item 1");
        assertEquals(uut.getTargetIdentifier(), 1);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
        TargetPriority targetPriorityFromIKlvKey =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriorityFromIKlvKey.getTargetPriority(), 3);
        TargetPriority targetPriority =
                (TargetPriority) uut.getField((IKlvKey) VTrackItemMetadataKey.TargetPriority);
        assertEquals(targetPriority.getTargetPriority(), 3);
        assertEquals(uut.getBytes(), new byte[] {0x01, 0x07, 0x01, 0x03});
    }

    @Test
    public void checkVTrackItemWithUnknownTag() throws KlvParseException {
        byte[] bytes = new byte[] {0x08, 0x7f, 0x01, 0x00, 0x07, 0x01, 0x03};
        this.verifyNoLoggerMessages();
        VTrackItem uut = new VTrackItem(bytes, 0, bytes.length, EncodingMode.IMAPB);
        this.verifySingleLoggerMessage("Unknown VTrackItem Metadata tag: 127");
        assertEquals(uut.getDisplayName(), "VTrackItem");
        assertEquals(uut.getDisplayableValue(), "Track Item 8");
        assertEquals(uut.getTargetIdentifier(), 8);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(VTrackItemMetadataKey.TargetPriority));
    }

    @Test
    public void checkUnknown() throws KlvParseException {
        IVTrackItemMetadataValue v =
                VTrackItem.createValue(
                        VTrackItemMetadataKey.Undefined, new byte[] {0x00}, EncodingMode.IMAPB);
        assertNull(v);
        this.verifySingleLoggerMessage("Unrecognized VTrackItem tag: Undefined");
    }

    @Test
    public void checkMIISId() throws KlvParseException {
        IVTrackItemMetadataValue v =
                VTrackItem.createValue(
                        VTrackItemMetadataKey.MiisId,
                        new byte[] {
                            (byte) 0x01,
                            (byte) 0x70,
                            (byte) 0xF5,
                            (byte) 0x92,
                            (byte) 0xF0,
                            (byte) 0x23,
                            (byte) 0x73,
                            (byte) 0x36,
                            (byte) 0x4A,
                            (byte) 0xF8,
                            (byte) 0xAA,
                            (byte) 0x91,
                            (byte) 0x62,
                            (byte) 0xC0,
                            (byte) 0x0F,
                            (byte) 0x2E,
                            (byte) 0xB2,
                            (byte) 0xDA,
                            (byte) 0x16,
                            (byte) 0xB7,
                            (byte) 0x43,
                            (byte) 0x41,
                            (byte) 0x00,
                            (byte) 0x08,
                            (byte) 0x41,
                            (byte) 0xA0,
                            (byte) 0xBE,
                            (byte) 0x36,
                            (byte) 0x5B,
                            (byte) 0x5A,
                            (byte) 0xB9,
                            (byte) 0x6A,
                            (byte) 0x36,
                            (byte) 0x45
                        },
                        EncodingMode.IMAPB);
        assertTrue(v instanceof MiisCoreIdentifier);
        MiisCoreIdentifier miisid = (MiisCoreIdentifier) v;
        assertEquals(
                miisid.getDisplayableValue(),
                "0170:F592-F023-7336-4AF8-AA91-62C0-0F2E-B2DA/16B7-4341-0008-41A0-BE36-5B5A-B96A-3645:D3");
    }
}
