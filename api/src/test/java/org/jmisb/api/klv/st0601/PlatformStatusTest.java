package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformStatusTest {

    @Test
    public void testConstructFromValue() {
        // Min
        PlatformStatus operationalMode = new PlatformStatus((byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Active");

        // Max
        operationalMode = new PlatformStatus((byte) 12);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 12});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landed-Parked");

        // Other values
        operationalMode = new PlatformStatus((byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 1});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Pre-flight");
        operationalMode = new PlatformStatus((byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 2});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Pre-flight-taxiing");
        operationalMode = new PlatformStatus((byte) 3);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 3});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Run-up");
        operationalMode = new PlatformStatus((byte) 4);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 4});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Take-off");
        operationalMode = new PlatformStatus((byte) 5);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 5});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Ingress");
        operationalMode = new PlatformStatus((byte) 6);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 6});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Manual operation");
        operationalMode = new PlatformStatus((byte) 7);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 7});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Automated-orbit");
        operationalMode = new PlatformStatus((byte) 8);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 8});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Transitioning");
        operationalMode = new PlatformStatus((byte) 9);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 9});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Egress");
        operationalMode = new PlatformStatus((byte) 10);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x0A});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landing");
        operationalMode = new PlatformStatus((byte) 11);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x0B});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landed-taxiing");
        operationalMode = new PlatformStatus((byte) 12);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x0C});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landed-Parked");

        Assert.assertEquals(operationalMode.getDisplayName(), "Platform Status");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        PlatformStatus operationalMode = new PlatformStatus(new byte[]{(byte) 0});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Active");

        // Max
        operationalMode = new PlatformStatus(new byte[]{(byte) 12});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 12);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x0C});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landed-Parked");

        // Other values
        operationalMode = new PlatformStatus(new byte[]{(byte) 1});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Pre-flight");
        operationalMode = new PlatformStatus(new byte[]{(byte) 2});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Pre-flight-taxiing");
        operationalMode = new PlatformStatus(new byte[]{(byte) 3});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 3);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x03});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Run-up");
        operationalMode = new PlatformStatus(new byte[]{(byte) 4});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 4);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x04});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Take-off");
        operationalMode = new PlatformStatus(new byte[]{(byte) 5});
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 5);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x05});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Ingress");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformStatus, bytes);
        Assert.assertTrue(v instanceof PlatformStatus);
        PlatformStatus operationalMode = (PlatformStatus) v;
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x00});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Active");

        bytes = new byte[]{(byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformStatus, bytes);
        Assert.assertTrue(v instanceof PlatformStatus);
        operationalMode = (PlatformStatus) v;
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Pre-flight");

        bytes = new byte[]{(byte) 0x0C};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformStatus, bytes);
        Assert.assertTrue(v instanceof PlatformStatus);
        operationalMode = (PlatformStatus) v;
        Assert.assertEquals(operationalMode.getPlatformStatus(), (byte) 12);
        Assert.assertEquals(operationalMode.getBytes(), new byte[]{(byte) 0x0C});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Landed-Parked");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformStatus((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformStatus((byte) 13);;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformStatus(new byte[]{0x00, 0x00});
    }
}
