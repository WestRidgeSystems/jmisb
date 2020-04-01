package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.klv.st0601.*;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DetectionStatusTest {

    @Test
    public void testConstructFromValue() {
        // Min
        DetectionStatus detectionStatus = new DetectionStatus((byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Max
        detectionStatus = new DetectionStatus((byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 3});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Other value...
        detectionStatus = new DetectionStatus((byte) 1);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 1});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Active");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        DetectionStatus detectionStatus = new DetectionStatus(new byte[]{(byte) 0});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Max
        detectionStatus = new DetectionStatus(new byte[]{(byte) 3});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x03});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");

        // Other value...
        detectionStatus = new DetectionStatus(new byte[]{(byte) 2});
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 2);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Dropped");
        Assert.assertEquals(detectionStatus.getDisplayName(), "Detection Status");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte) 0x00};
        IVmtiMetadataValue v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        Assert.assertTrue(v instanceof DetectionStatus);
        DetectionStatus detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 0);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Inactive");

        bytes = new byte[]{(byte) 0x01};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        Assert.assertTrue(v instanceof DetectionStatus);
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 1);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Active");

        bytes = new byte[]{(byte) 0x02};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertTrue(v instanceof DetectionStatus);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 2);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Dropped");

        bytes = new byte[]{(byte) 0x03};
        v = VTrackerLS.createValue(VTrackerMetadataKey.detectionStatus, bytes);
        Assert.assertTrue(v instanceof DetectionStatus);
        Assert.assertEquals(v.getDisplayName(), "Detection Status");
        detectionStatus = (DetectionStatus) v;
        Assert.assertEquals(detectionStatus.getDetectionStatus(), (byte) 3);
        Assert.assertEquals(detectionStatus.getBytes(), new byte[]{(byte) 0x03});
        Assert.assertEquals(detectionStatus.getDisplayableValue(), "Stopped");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DetectionStatus((byte)-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DetectionStatus((byte)4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DetectionStatus(new byte[]{0x00, 0x00});
    }
}
