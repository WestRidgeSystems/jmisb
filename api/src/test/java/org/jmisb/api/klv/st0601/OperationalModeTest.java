package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OperationalModeTest {

    @Test
    public void testConstructFromValue() {
        // Min
        OperationalMode operationalMode = new OperationalMode((byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Other");

        // Max
        operationalMode = new OperationalMode((byte) 5);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 5});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Test");

        // Other values
        operationalMode = new OperationalMode((byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Operational");
        operationalMode = new OperationalMode((byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 2});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Training");
        operationalMode = new OperationalMode((byte) 3);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 3});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Exercise");
        operationalMode = new OperationalMode((byte) 4);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 4});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Maintenance");

        Assert.assertEquals(operationalMode.getDisplayName(), "Operational Mode");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        OperationalMode operationalMode = new OperationalMode(new byte[] {(byte) 0});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Other");

        // Max
        operationalMode = new OperationalMode(new byte[] {(byte) 5});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 5);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x05});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Test");

        // Other values
        operationalMode = new OperationalMode(new byte[] {(byte) 1});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Operational");
        operationalMode = new OperationalMode(new byte[] {(byte) 2});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Training");
        operationalMode = new OperationalMode(new byte[] {(byte) 3});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 3);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x03});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Exercise");
        operationalMode = new OperationalMode(new byte[] {(byte) 4});
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 4);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x04});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Maintenance");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OperationalMode, bytes);
        Assert.assertTrue(v instanceof OperationalMode);
        OperationalMode operationalMode = (OperationalMode) v;
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 0);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Other");

        bytes = new byte[] {(byte) 0x01};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OperationalMode, bytes);
        Assert.assertTrue(v instanceof OperationalMode);
        operationalMode = (OperationalMode) v;
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 1);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Operational");

        bytes = new byte[] {(byte) 0x02};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.OperationalMode, bytes);
        Assert.assertTrue(v instanceof OperationalMode);
        operationalMode = (OperationalMode) v;
        Assert.assertEquals(operationalMode.getOperationalMode(), (byte) 2);
        Assert.assertEquals(operationalMode.getBytes(), new byte[] {(byte) 0x02});
        Assert.assertEquals(operationalMode.getDisplayableValue(), "Training");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new OperationalMode((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new OperationalMode((byte) 6);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new OperationalMode(new byte[] {0x00, 0x00});
    }
}
