package org.jmisb.api.klv.st0903.vtracker;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DetectionStatusTest {

    @Test
    public void testConstructFromValue() {
        // Min
        DetectionStatus detectionStatus = new DetectionStatus((byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Max
        detectionStatus = new DetectionStatus((byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 3});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Other value...
        detectionStatus = new DetectionStatus((byte) 1);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Active");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
    }

    @Test
    public void testStaticValues() {
        // Min
        DetectionStatus detectionStatus = DetectionStatus.INACTIVE;
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Max
        detectionStatus = DetectionStatus.STOPPED;
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 3});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Other values
        detectionStatus = DetectionStatus.ACTIVE;
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Active");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
        detectionStatus = DetectionStatus.DROPPED;
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 2});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Dropped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        DetectionStatus detectionStatus = new DetectionStatus(new byte[] {(byte) 0});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Max
        detectionStatus = new DetectionStatus(new byte[] {(byte) 3});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Other value...
        detectionStatus = new DetectionStatus(new byte[] {(byte) 2});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 2);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Dropped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IVmtiMetadataValue v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        Assert.assertTrue(v instanceof DetectionStatus);
        DetectionStatus detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus, DetectionStatus.INACTIVE);

        bytes = new byte[] {(byte) 0x01};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        Assert.assertTrue(v instanceof DetectionStatus);
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 1);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Active");
        Assert.assertEquals(detectionStatus, DetectionStatus.ACTIVE);

        bytes = new byte[] {(byte) 0x02};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertTrue(v instanceof DetectionStatus);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 2);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Dropped");
        Assert.assertEquals(detectionStatus, DetectionStatus.DROPPED);

        bytes = new byte[] {(byte) 0x03};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertTrue(v instanceof DetectionStatus);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus, DetectionStatus.STOPPED);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DetectionStatus((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DetectionStatus((byte) 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DetectionStatus(new byte[] {0x00, 0x00});
    }

    @Test
    public void hashTest() {
        DetectionStatus detectionStatus = DetectionStatus.ACTIVE;
        assertEquals(detectionStatus.hashCode(), 0x70);
        detectionStatus = DetectionStatus.STOPPED;
        assertEquals(detectionStatus.hashCode(), 0x72);
    }

    @Test
    public void equalsSameObject() {
        DetectionStatus detectionStatus = DetectionStatus.ACTIVE;
        assertTrue(detectionStatus.equals(detectionStatus));
    }

    @Test
    public void equalsSameValues() {
        DetectionStatus detectionStatus1 = new DetectionStatus((byte) 0x02);
        DetectionStatus detectionStatus2 = new DetectionStatus((byte) 0x02);
        assertTrue(detectionStatus1.equals(detectionStatus2));
        assertTrue(detectionStatus2.equals(detectionStatus1));
        assertTrue(detectionStatus1 != detectionStatus2);
    }

    @Test
    public void equalsDifferentValues() {
        DetectionStatus detectionStatus1 = new DetectionStatus((byte) 0x01);
        DetectionStatus detectionStatus2 = new DetectionStatus((byte) 0x02);
        assertFalse(detectionStatus1.equals(detectionStatus2));
        assertFalse(detectionStatus2.equals(detectionStatus1));
        assertTrue(detectionStatus1 != detectionStatus2);
    }

    @Test
    public void equalsNull() {
        DetectionStatus detectionStatus = new DetectionStatus((byte) 0x03);
        assertFalse(detectionStatus.equals(null));
    }

    @Test
    public void equalsDifferentClass() {
        DetectionStatus detectionStatus = new DetectionStatus((byte) 0x01);
        assertFalse(detectionStatus.equals(new String("blah")));
    }
}
