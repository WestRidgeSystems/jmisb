package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TargetErrorEstimateLe90Test {

    @Test
    public void testMinMax()
    {
        TargetErrorEstimateLe90 errorEstimate = new TargetErrorEstimateLe90(0.0);
        byte[] bytes = errorEstimate.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0x00, (byte) 0x00});
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals("0.0000m", errorEstimate.getDisplayableValue());

        errorEstimate = new TargetErrorEstimateLe90(4095.0);
        bytes = errorEstimate.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals("4095.0000m", errorEstimate.getDisplayableValue());

        bytes = new byte[]{(byte) 0x00, (byte) 0x00};
        errorEstimate = new TargetErrorEstimateLe90(bytes);
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[]{(byte) 0xff, (byte) 0xff};
        errorEstimate = new TargetErrorEstimateLe90(bytes);
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        Assert.assertEquals(errorEstimate.getDisplayName(), "Target Error LE90");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorLe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateLe90);
        Assert.assertEquals(v.getDisplayName(), "Target Error LE90");
        TargetErrorEstimateLe90 errorEstimate = (TargetErrorEstimateLe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 0.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[]{(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorLe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateLe90);
        errorEstimate = (TargetErrorEstimateLe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 4095.0);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);

        bytes = new byte[]{(byte) 0x26, (byte) 0x11};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetErrorLe90, bytes);
        Assert.assertTrue(v instanceof TargetErrorEstimateLe90);
        errorEstimate = (TargetErrorEstimateLe90) v;
        Assert.assertEquals(errorEstimate.getMetres(), 608.9231, 0.0001);
        Assert.assertEquals(errorEstimate.getBytes(), bytes);
        Assert.assertEquals(errorEstimate.getDisplayableValue(), "608.9231m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TargetErrorEstimateLe90(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new TargetErrorEstimateLe90(4095.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TargetErrorEstimateLe90(new byte[]{0x00, 0x00, 0x00});
    }
}
