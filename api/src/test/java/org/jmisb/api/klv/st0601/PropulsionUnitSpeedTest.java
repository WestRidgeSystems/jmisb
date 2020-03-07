package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PropulsionUnitSpeedTest
{
    @Test
    public void testMinMax()
    {
        PropulsionUnitSpeed speed = new PropulsionUnitSpeed(0);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        byte[] bytes = speed.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getSpeed(), 0);
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        speed = new PropulsionUnitSpeed(4294967295L);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        bytes = speed.getBytes();
        Assert.assertEquals(bytes, new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(speed.getSpeed(), 4294967295L);
        Assert.assertEquals(speed.getDisplayableValue(), "4294967295rpm");

        bytes = new byte[]{(byte)0x00};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 255);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "255rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 65535);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "65535rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 16777215L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "16777215rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        speed = new PropulsionUnitSpeed(bytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), 4294967295L);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "4294967295rpm");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMin()
    {
        new PropulsionUnitSpeed(-1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testOutOfBoundsMax()
    {
        new PropulsionUnitSpeed(4294967296L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLength()
    {
        byte[] fiveByteArray = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        new PropulsionUnitSpeed(fiveByteArray);
    }

    @Test
    public void stExample()
    {
        long value = 3000L;
        byte[] origBytes = new byte[]{(byte)0x0B, (byte)0xB8};

        PropulsionUnitSpeed speed = new PropulsionUnitSpeed(origBytes);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), value);
        Assert.assertEquals(speed.getBytes(), origBytes);
        Assert.assertEquals(speed.getDisplayableValue(), "3000rpm");

        speed = new PropulsionUnitSpeed(value);
        Assert.assertEquals(speed.getDisplayName(), "Propulsion Unit Speed");
        Assert.assertEquals(speed.getSpeed(), value);
        Assert.assertEquals(speed.getBytes(), origBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte)0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        PropulsionUnitSpeed speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 255);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "255rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 65535);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "65535rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 16777215L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00, (byte)0xff, (byte)0xff, (byte)0xff});
        Assert.assertEquals(speed.getDisplayableValue(), "16777215rpm");

        bytes = new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 0L);
        Assert.assertEquals(speed.getBytes(), new byte[]{(byte)0x00});
        Assert.assertEquals(speed.getDisplayableValue(), "0rpm");

        bytes = new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PropulsionUnitSpeed, bytes);
        Assert.assertTrue(v instanceof PropulsionUnitSpeed);
        Assert.assertEquals(v.getDisplayName(), "Propulsion Unit Speed");
        speed = (PropulsionUnitSpeed)v;
        Assert.assertEquals(speed.getSpeed(), 4294967295L);
        Assert.assertEquals(speed.getBytes(), bytes);
        Assert.assertEquals(speed.getDisplayableValue(), "4294967295rpm");
    }
}
