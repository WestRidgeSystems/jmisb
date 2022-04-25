package org.jmisb.st0903.vtarget;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.AlgorithmId;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for VTarget Pack */
public class VTargetPackTest extends LoggerChecks {

    private static final byte[] TEST_BYTES =
            new byte[] {
                0x01, // Target Id
                0x16,
                0x03,
                0x01,
                0x03,
                (byte) 0x98 // Tag 22 - Algorithm Id.
            };

    public VTargetPackTest() {
        super(VTargetPack.class);
    }

    @Test
    public void testFactoryUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IVmtiMetadataValue value =
                VTargetPack.createValue(
                        VTargetMetadataKey.Undefined,
                        new byte[] {(byte) 0x2a, (byte) 0x94},
                        EncodingMode.IMAPB);
        verifySingleLoggerMessage("Unrecognized VTarget tag: Undefined");
        assertNull(value);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void parseAlgorithmId() throws KlvParseException {
        VTargetPack localSet = new VTargetPack(TEST_BYTES, 0, TEST_BYTES.length);
        checkVTargetPackWithAlgorithmId(localSet);
    }

    @Test
    public void parseAlgorithmIdExplicitIMAPB() throws KlvParseException {
        VTargetPack localSet =
                new VTargetPack(TEST_BYTES, 0, TEST_BYTES.length, EncodingMode.IMAPB);
        checkVTargetPackWithAlgorithmId(localSet);
    }

    @Test
    public void parseAlgorithmIdExplicitLegacy() throws KlvParseException {
        VTargetPack localSet =
                new VTargetPack(TEST_BYTES, 0, TEST_BYTES.length, EncodingMode.LEGACY);
        checkVTargetPackWithAlgorithmId(localSet);
    }

    private void checkVTargetPackWithAlgorithmId(VTargetPack localSet) {
        assertNotNull(localSet);
        assertEquals(localSet.getDisplayName(), "VTarget");
        assertEquals(localSet.getDisplayableValue(), "Target 1");
        assertEquals(localSet.getTags().size(), 1);
        assertTrue(localSet.getTags().contains(VTargetMetadataKey.AlgorithmId));
        assertEquals(localSet.getIdentifiers().size(), 1);
        assertTrue(localSet.getIdentifiers().contains(VTargetMetadataKey.AlgorithmId));
        IVmtiMetadataValue v = localSet.getField(VTargetMetadataKey.AlgorithmId);
        assertEquals(v.getDisplayName(), "Algorithm Id");
        assertEquals(v.getDisplayableValue(), "66456");
        assertTrue(v instanceof AlgorithmId);
        AlgorithmId id = (AlgorithmId) localSet.getField(VTargetMetadataKey.AlgorithmId);
        assertEquals(id.getValue(), 66456);
        assertEquals(id.getBytes(), new byte[] {0x01, 0x03, (byte) 0x98});
        AlgorithmId idFromIKlvKey =
                (AlgorithmId) localSet.getField((IKlvKey) VTargetMetadataKey.AlgorithmId);
        assertEquals(idFromIKlvKey.getValue(), 66456);
        assertEquals(idFromIKlvKey.getBytes(), new byte[] {0x01, 0x03, (byte) 0x98});
    }

    @Test
    public void parseUnknown() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x01, // Target Id
                    0x37,
                    0x02,
                    0x01,
                    0x03, // No such tag
                    0x16,
                    0x03,
                    0x01,
                    0x03,
                    (byte) 0x98 // Tag 22 - Algorithm Id.
                };
        verifyNoLoggerMessages();
        VTargetPack localSet = new VTargetPack(bytes, 0, bytes.length, EncodingMode.IMAPB);
        verifySingleLoggerMessage("Unknown VMTI VTarget Metadata tag: 55");
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        final byte[] bytes = new byte[] {0x01, 0x00};
        VTargetPack.createValue(VTargetMetadataKey.VTracker, bytes, EncodingMode.IMAPB);
    }
}
