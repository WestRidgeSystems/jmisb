package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ActivePayloadsTest
{
    private final byte[] ST_EXAMPLE_BYTES = new byte[]{(byte)0x0b};

    @Test
    public void testConstructFromValue()
    {
        List<Integer> values = new ArrayList<>();
        values.add(0);
        values.add(1);
        values.add(3);
        ActivePayloads activePayloads = new ActivePayloads(values);
        checkValuesForExample(activePayloads);
    }

    @Test
    public void testConstructFromEncoded()
    {
        ActivePayloads activePayloads = new ActivePayloads(ST_EXAMPLE_BYTES);
        checkValuesForExample(activePayloads);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.ActivePayloads, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof ActivePayloads);
        ActivePayloads activePayloads = (ActivePayloads)v;
        checkValuesForExample(activePayloads);
    }

    private void checkValuesForExample(ActivePayloads activePayloads)
    {
        Assert.assertEquals(activePayloads.getBytes(), ST_EXAMPLE_BYTES);
        Assert.assertEquals(activePayloads.getDisplayableValue(), "0,1,3");
        Assert.assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncodedHighBit()
    {
        ActivePayloads activePayloads = new ActivePayloads(new byte[]{(byte)0x80});
        Assert.assertEquals(activePayloads.getBytes(), new byte[]{(byte)0x80});
        Assert.assertEquals(activePayloads.getDisplayableValue(), "7");
        Assert.assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncoded0()
    {
        ActivePayloads activePayloads = new ActivePayloads(new byte[]{(byte)0x00});
        Assert.assertEquals(activePayloads.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(activePayloads.getDisplayableValue(), "");
        Assert.assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }

    @Test
    public void testConstructFromEncodedTwoBytes()
    {
        ActivePayloads activePayloads = new ActivePayloads(new byte[]{(byte)0x80, (byte)0x0b});
        Assert.assertEquals(activePayloads.getBytes(), new byte[]{(byte)0x80, (byte)0x0b});
        Assert.assertEquals(activePayloads.getDisplayableValue(), "0,1,3,15");
        Assert.assertEquals(activePayloads.getDisplayName(), "Active Payloads");
    }
}
