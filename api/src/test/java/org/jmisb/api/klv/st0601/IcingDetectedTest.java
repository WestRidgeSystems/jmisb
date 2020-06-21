package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IcingDetectedTest {

    @Test
    public void testConstructFromValue() {
        // Min
        IcingDetected icingDetected = new IcingDetected((byte) 0);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Detector off");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");

        // Max
        icingDetected = new IcingDetected((byte) 2);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 2});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Icing detected");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");

        // Other value...
        icingDetected = new IcingDetected((byte) 1);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "No icing detected");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        IcingDetected icingDetected = new IcingDetected(new byte[] {(byte) 0});
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 0);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Detector off");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");

        // Max
        icingDetected = new IcingDetected(new byte[] {(byte) 2});
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 2);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Icing detected");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");

        // Other value...
        icingDetected = new IcingDetected(new byte[] {(byte) 1});
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 1);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "No icing detected");
        Assert.assertEquals(icingDetected.getDisplayName(), "Icing Detected");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.IcingDetected, bytes);
        Assert.assertEquals(v.getDisplayName(), "Icing Detected");
        Assert.assertTrue(v instanceof IcingDetected);
        IcingDetected icingDetected = (IcingDetected) v;
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 0);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Detector off");

        bytes = new byte[] {(byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.IcingDetected, bytes);
        Assert.assertEquals(v.getDisplayName(), "Icing Detected");
        Assert.assertTrue(v instanceof IcingDetected);
        icingDetected = (IcingDetected) v;
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 1);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "No icing detected");

        bytes = new byte[] {(byte) 0x02};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.IcingDetected, bytes);
        Assert.assertTrue(v instanceof IcingDetected);
        Assert.assertEquals(v.getDisplayName(), "Icing Detected");
        icingDetected = (IcingDetected) v;
        Assert.assertEquals(icingDetected.getIcingDetected(), (byte) 2);
        Assert.assertEquals(icingDetected.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(icingDetected.getDisplayableValue(), "Icing detected");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new IcingDetected((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new IcingDetected((byte) 3);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new IcingDetected(new byte[] {0x00, 0x00});
    }
}
