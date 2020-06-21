package org.jmisb.api.klv.st0903.vtarget;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiTextString;
import org.jmisb.api.klv.st0903.vtracker.DetectionStatus;
import org.jmisb.api.klv.st0903.vtracker.VTrackerLS;
import org.jmisb.api.klv.st0903.vtracker.VTrackerMetadataKey;
import org.testng.annotations.Test;

/** Tests for VTracker (ST0903 Target Tag 104). */
public class VTrackerTest {
    final byte[] bytes =
            new byte[] {
                0x02,
                0x01,
                0x01, // Tag 2 - detection status
                0x06,
                0x4,
                0x74,
                0x65,
                0x73,
                0x74 // Tag 6 - algorithm
            };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException {
        VTracker tracker = new VTracker(bytes);
        assertEquals(tracker.getBytes(), bytes);
        assertEquals(tracker.getDisplayName(), "VTracker");
        assertEquals(tracker.getDisplayableValue(), "[VTracker]");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VTracker, bytes);
        assertTrue(value instanceof VTracker);
        VTracker tracker = (VTracker) value;
        assertEquals(tracker.getBytes().length, bytes.length);
        assertEquals(tracker.getDisplayName(), "VTracker");
        assertEquals(tracker.getDisplayableValue(), "[VTracker]");
        assertEquals(tracker.getTracker().getTags().size(), 2);
    }

    @Test
    public void testFactoryEncodedBytesWithUnknownTag() throws KlvParseException {
        final byte[] bytesWithUnknownTag =
                new byte[] {
                    0x3F,
                    0x02,
                    0x03,
                    0x04, // No such tag - max is 12.
                    0x02,
                    0x01,
                    0x01, // Tag 2 - detection status
                    0x06,
                    0x4,
                    0x74,
                    0x65,
                    0x73,
                    0x74 // Tag 6 - algorithm
                };
        IVmtiMetadataValue value =
                VTargetPack.createValue(VTargetMetadataKey.VTracker, bytesWithUnknownTag);
        assertTrue(value instanceof VTracker);
        VTracker tracker = (VTracker) value;
        assertEquals(tracker.getBytes().length, bytes.length);
        assertEquals(tracker.getDisplayName(), "VTracker");
        assertEquals(tracker.getDisplayableValue(), "[VTracker]");
        assertEquals(tracker.getTracker().getTags().size(), 2);
    }

    @Test
    public void constructFromValue() throws KlvParseException {
        Map<VTrackerMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue detectionStatus = new DetectionStatus((byte) 1);
        values.put(VTrackerMetadataKey.detectionStatus, detectionStatus);
        IVmtiMetadataValue algo = new VmtiTextString(VmtiTextString.ALGORITHM, "test");
        values.put(VTrackerMetadataKey.algorithm, algo);
        VTrackerLS vTrackerLS = new VTrackerLS(values);
        VTracker vTracker = new VTracker(vTrackerLS);
        assertNotNull(vTracker);
        assertEquals(vTracker.getTracker().getTags().size(), 2);
    }
}
