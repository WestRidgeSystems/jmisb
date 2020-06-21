package org.jmisb.api.klv.st1201;

import org.testng.Assert;
import org.testng.annotations.*;

public class ST1201AnnexATest {
    @Test
    public void testSmallRange() {
        // IMAPB(0.1, 0.9, 2)
        FpEncoder fpEncoder = new FpEncoder(0.1, 0.9, 2);
        byte[] encoded = fpEncoder.encode(0.1);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x00, (byte) 0x00});

        encoded = fpEncoder.encode(0.5);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x33, (byte) 0x33});

        encoded = fpEncoder.encode(0.9);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x66, (byte) 0x66});

        encoded = fpEncoder.encode(Double.NEGATIVE_INFINITY);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xe8, (byte) 0x00});
    }
}
