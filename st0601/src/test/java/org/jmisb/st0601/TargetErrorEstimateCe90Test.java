package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetErrorEstimateCe90Test {

    @Test
    public void testMinMax() {
        TargetErrorEstimateCe90 errorEstimate = new TargetErrorEstimateCe90(0.0);
        byte[] bytes = errorEstimate.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals("0.0000m", errorEstimate.getDisplayableValue());

        errorEstimate = new TargetErrorEstimateCe90(4095.0);
        bytes = errorEstimate.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals("4095.0000m", errorEstimate.getDisplayableValue());

        bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        errorEstimate = new TargetErrorEstimateCe90(bytes);
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        errorEstimate = new TargetErrorEstimateCe90(bytes);
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        Assert.assertEquals(errorEstimate.getDisplayName(), "Target Error CE90");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorCe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateCe90);
        Assert.assertEquals(v.getDisplayName(), "Target Error CE90");
        TargetErrorEstimateCe90 errorEstimate = (TargetErrorEstimateCe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorCe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateCe90);
        errorEstimate = (TargetErrorEstimateCe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x1A, (byte) 0x95};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorCe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateCe90);
        errorEstimate = (TargetErrorEstimateCe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 425.215152, 0.00001);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);
        Assert.assertEquals(errorEstimate.getDisplayableValue(), "425.2152m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TargetErrorEstimateCe90(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TargetErrorEstimateCe90(4095.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TargetErrorEstimateCe90(new byte[] {0x00, 0x00, 0x00});
    }
}
