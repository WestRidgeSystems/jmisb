package org.jmisb.st0903.vtracker;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.IVmtiMetadataValue;
import org.jmisb.st0903.shared.AlgorithmId;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST0903 VTracker LS. */
public class VTrackerLSTest extends LoggerChecks {
    public VTrackerLSTest() {
        super(VTrackerLS.class);
    }

    @Test
    public void createUnknownTag() throws KlvParseException {
        final byte[] bytes = new byte[] {0x6A, 0x70};
        this.verifyNoLoggerMessages();
        IVmtiMetadataValue value =
                VTrackerLS.createValue(VTrackerMetadataKey.Undefined, bytes, EncodingMode.IMAPB);
        this.verifySingleLoggerMessage("Unrecognized VTracker tag: Undefined");
        assertNull(value);
    }

    @Test
    public void createUnknownTagLegacy() throws KlvParseException {
        final byte[] bytes = new byte[] {0x6A, 0x70};
        this.verifyNoLoggerMessages();
        IVmtiMetadataValue value =
                VTrackerLS.createValue(VTrackerMetadataKey.Undefined, bytes, EncodingMode.LEGACY);
        this.verifySingleLoggerMessage("Unrecognized VTracker tag: Undefined");
        assertNull(value);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void parseAlgorithmId() throws KlvParseException {
        final byte[] bytes = new byte[] {0x0C, 0x01, 0x03};
        VTrackerLS localSet = new VTrackerLS(bytes);
        checkLocalSetWithAlgorithmId(localSet);
    }

    private void checkLocalSetWithAlgorithmId(VTrackerLS localSet) {
        assertNotNull(localSet);
        assertEquals(localSet.getTags().size(), 1);
        assertTrue(localSet.getTags().contains(VTrackerMetadataKey.algorithmId));
        IVmtiMetadataValue v = localSet.getField(VTrackerMetadataKey.algorithmId);
        assertEquals(v.getDisplayName(), "Algorithm Id");
        assertEquals(v.getDisplayableValue(), "3");
        assertTrue(v instanceof AlgorithmId);
        AlgorithmId id = (AlgorithmId) localSet.getField(VTrackerMetadataKey.algorithmId);
        assertEquals(id.getValue(), 3);
        assertEquals(id.getBytes(), new byte[] {0x03});
    }

    @Test
    public void parseAlgorithmIdLegacyModeSelected() throws KlvParseException {
        final byte[] bytes = new byte[] {0x0C, 0x01, 0x03};
        VTrackerLS localSet = new VTrackerLS(bytes, EncodingMode.LEGACY);
        checkLocalSetWithAlgorithmId(localSet);
    }

    @Test
    public void parseAlgorithmIdIMAPBModeSelected() throws KlvParseException {
        final byte[] bytes = new byte[] {0x0C, 0x01, 0x03};
        VTrackerLS localSet = new VTrackerLS(bytes, EncodingMode.IMAPB);
        checkLocalSetWithAlgorithmId(localSet);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        VTrackerLS.createValue(
                VTrackerMetadataKey.trackId,
                new byte[] {0x5b, 0x78, (byte) 0x93, 0x45, 0x05, (byte) 0xcc, 0x1d},
                EncodingMode.IMAPB);
    }
}
