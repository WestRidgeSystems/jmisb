package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkFactoryTest {
    public void goodTag() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.getTag(5), bytes);
        Assert.assertTrue(v instanceof PlatformHeadingAngle);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void noSuchTag() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.getTag(255), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void undefinedTag() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.getTag(0), bytes);
    }
}
