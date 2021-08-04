package org.jmisb.api.klv.st1201;

import org.testng.Assert;
import org.testng.annotations.*;

public class ST1201AnnexATest {
    @Test
    public void testSmallRange() {
        // IMAPB(0.1, 0.9, 2)
        FpEncoder fpEncoder = new FpEncoder(0.1, 0.9, 2);
        byte[] encoded = fpEncoder.encode(0.1, OutOfRangeBehaviour.Clamp);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x00, (byte) 0x00});

        encoded = fpEncoder.encode(0.5, OutOfRangeBehaviour.Clamp);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x33, (byte) 0x33});

        encoded = fpEncoder.encode(0.9, OutOfRangeBehaviour.Clamp);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0x66, (byte) 0x66});

        encoded = fpEncoder.encode(Double.NEGATIVE_INFINITY, OutOfRangeBehaviour.Clamp);
        Assert.assertEquals(encoded.length, 2);
        Assert.assertEquals(encoded, new byte[] {(byte) 0xe8, (byte) 0x00});
    }

    @DataProvider(name = "Test1")
    public Object[][] test1data() {
        return new Object[][] {
            {0.0, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}, 0.0},
            {10.1, new byte[] {(byte) 0x0A, (byte) 0x19, (byte) 0x99, (byte) 0x99}, 10.09999996},
            {
                20.20000000,
                new byte[] {(byte) 0x14, (byte) 0x33, (byte) 0x33, (byte) 0x33},
                20.19999999
            },
            {
                30.30000000,
                new byte[] {(byte) 0x1E, (byte) 0x4C, (byte) 0xCC, (byte) 0xCC},
                30.29999995
            },
            {
                40.40000000,
                new byte[] {(byte) 0x28, (byte) 0x66, (byte) 0x66, (byte) 0x66},
                40.39999998
            },
            {
                50.50000000,
                new byte[] {(byte) 0x32, (byte) 0x80, (byte) 0x00, (byte) 0x00},
                50.50000000
            },
            {
                60.60000000,
                new byte[] {(byte) 0x3C, (byte) 0x99, (byte) 0x99, (byte) 0x99},
                60.59999996
            },
            {
                70.70000000,
                new byte[] {(byte) 0x46, (byte) 0xB3, (byte) 0x33, (byte) 0x33},
                70.69999999
            },
            {
                80.80000000,
                new byte[] {(byte) 0x50, (byte) 0xCC, (byte) 0xCC, (byte) 0xCC},
                80.79999995
            },
            {
                90.90000000,
                new byte[] {(byte) 0x5A, (byte) 0xE6, (byte) 0x66, (byte) 0x66},
                90.89999998
            },
            {
                100.00000000,
                new byte[] {(byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x00},
                100.00000000
            },
            {
                Double.NaN,
                new byte[] {(byte) 0xD0, (byte) 0x00, (byte) 0x00, (byte) 0x00},
                Double.NaN
            },
            {
                Double.POSITIVE_INFINITY,
                new byte[] {(byte) 0xC8, (byte) 0x00, (byte) 0x00, (byte) 0x00},
                Double.POSITIVE_INFINITY
            },
            {
                Double.NEGATIVE_INFINITY,
                new byte[] {(byte) 0xE8, (byte) 0x00, (byte) 0x00, (byte) 0x00},
                Double.NEGATIVE_INFINITY
            },
            {-1.0, new byte[] {(byte) 0xE0, (byte) 0x00, (byte) 0x00, (byte) 0x00}, 0.0},
            {
                101.00000000,
                new byte[] {(byte) 0xE1, (byte) 0x00, (byte) 0x00, (byte) 0x00},
                100.00000000
            },
        };
    }

    @Test(dataProvider = "Test1")
    public void Test1(Double imapInput, byte[] imapOutput, Double rimapOutput) {
        // IMAPA(0.0, 100.0, 1E-5)
        FpEncoder fpEncoder = new FpEncoder(0.0, 100.0, 1E-5);
        Assert.assertEquals(fpEncoder.getFieldLength(), 4);
        byte[] encoded = fpEncoder.encode(imapInput, OutOfRangeBehaviour.Flag);
        Assert.assertEquals(encoded.length, 4);
        Assert.assertEquals(encoded, imapOutput);
        double decoded = fpEncoder.decode(encoded);
        Assert.assertEquals(decoded, rimapOutput, 0.00000001);
    }

    @DataProvider(name = "Test2")
    public Object[][] test2data() {
        return new Object[][] {
            {0.0, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00}, 0.0},
            {10.10000, new byte[] {(byte) 0x0A, (byte) 0x19, (byte) 0x99}, 10.09999},
            {20.20000, new byte[] {(byte) 0x14, (byte) 0x33, (byte) 0x33}, 20.20000},
            {30.30000, new byte[] {(byte) 0x1E, (byte) 0x4C, (byte) 0xCC}, 30.29999},
            {40.40000, new byte[] {(byte) 0x28, (byte) 0x66, (byte) 0x66}, 40.39999},
            {50.50000, new byte[] {(byte) 0x32, (byte) 0x80, (byte) 0x00}, 50.50000},
            {60.60000, new byte[] {(byte) 0x3C, (byte) 0x99, (byte) 0x99}, 60.59999},
            {70.70000, new byte[] {(byte) 0x46, (byte) 0xB3, (byte) 0x33}, 70.70000},
            {80.80000, new byte[] {(byte) 0x50, (byte) 0xCC, (byte) 0xCC}, 80.79999},
            {90.90000, new byte[] {(byte) 0x5A, (byte) 0xE6, (byte) 0x66}, 90.89999},
            {100.00000, new byte[] {(byte) 0x64, (byte) 0x00, (byte) 0x00}, 100.00000},
            {Double.NaN, new byte[] {(byte) 0xD0, (byte) 0x00, (byte) 0x00}, Double.NaN},
            {
                Double.POSITIVE_INFINITY,
                new byte[] {(byte) 0xC8, (byte) 0x00, (byte) 0x00},
                Double.POSITIVE_INFINITY
            },
            {
                Double.NEGATIVE_INFINITY,
                new byte[] {(byte) 0xE8, (byte) 0x00, (byte) 0x00},
                Double.NEGATIVE_INFINITY
            },
            {-1.0, new byte[] {(byte) 0xE0, (byte) 0x00, (byte) 0x00}, 0.0},
            {101.0, new byte[] {(byte) 0xE1, (byte) 0x00, (byte) 0x00}, 100.0},
        };
    }

    @Test(dataProvider = "Test2")
    public void Test2(Double imapInput, byte[] imapOutput, Double rimapOutput) {
        // IMAPB(0.0, 100.0, 3)
        FpEncoder fpEncoder = new FpEncoder(0.0, 100.0, 3);
        Assert.assertEquals(fpEncoder.getFieldLength(), 3);
        byte[] encoded = fpEncoder.encode(imapInput, OutOfRangeBehaviour.Flag);
        Assert.assertEquals(encoded.length, 3);
        Assert.assertEquals(encoded, imapOutput);
        double decoded = fpEncoder.decode(encoded);
        Assert.assertEquals(decoded, rimapOutput, 0.00001);
    }

    @DataProvider(name = "Test3")
    public Object[][] test3data() {
        return new Object[][] {
            {-9.9, new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00}, -9.9},
            {0.22500, new byte[] {(byte) 0x0A, (byte) 0x20, (byte) 0x00}, 0.22499},
            {10.35000, new byte[] {(byte) 0x14, (byte) 0x40, (byte) 0x00}, 10.34999},
            {20.47500, new byte[] {(byte) 0x1E, (byte) 0x60, (byte) 0x00}, 20.47499},
            {30.60000, new byte[] {(byte) 0x28, (byte) 0x80, (byte) 0x00}, 30.59999},
            {40.72500, new byte[] {(byte) 0x32, (byte) 0xA0, (byte) 0x00}, 40.72499},
            {50.85000, new byte[] {(byte) 0x3C, (byte) 0xC0, (byte) 0x00}, 50.84999},
            {60.97500, new byte[] {(byte) 0x46, (byte) 0xE0, (byte) 0x00}, 60.97499},
            {71.10000, new byte[] {(byte) 0x51, (byte) 0x00, (byte) 0x00}, 71.09999},
            {81.22500, new byte[] {(byte) 0x5B, (byte) 0x20, (byte) 0x00}, 81.22499},
            {91.35000, new byte[] {(byte) 0x65, (byte) 0x40, (byte) 0x00}, 91.34999},
            {101.47500, new byte[] {(byte) 0x6F, (byte) 0x60, (byte) 0x00}, 101.47499},
            {110.00000, new byte[] {(byte) 0x77, (byte) 0xE6, (byte) 0x67}, 110.00000},
            {0.00000, new byte[] {(byte) 0x09, (byte) 0xE6, (byte) 0x67}, 0.00000},
            {Double.NaN, new byte[] {(byte) 0xD0, (byte) 0x00, (byte) 0x00}, Double.NaN},
            {
                Double.POSITIVE_INFINITY,
                new byte[] {(byte) 0xC8, (byte) 0x00, (byte) 0x00},
                Double.POSITIVE_INFINITY
            },
            {
                Double.NEGATIVE_INFINITY,
                new byte[] {(byte) 0xE8, (byte) 0x00, (byte) 0x00},
                Double.NEGATIVE_INFINITY
            },
            {-100.0, new byte[] {(byte) 0xE0, (byte) 0x00, (byte) 0x00}, -9.9},
            {121.0, new byte[] {(byte) 0xE1, (byte) 0x00, (byte) 0x00}, 110.0},
        };
    }

    @Test(dataProvider = "Test3")
    public void Test3(Double imapInput, byte[] imapOutput, Double rimapOutput) {
        // IMAPB(-9.9, 110.0, 3)
        FpEncoder fpEncoder = new FpEncoder(-9.9, 110.0, 3);
        Assert.assertEquals(fpEncoder.getFieldLength(), 3);
        byte[] encoded = fpEncoder.encode(imapInput, OutOfRangeBehaviour.Flag);
        Assert.assertEquals(encoded.length, 3);
        Assert.assertEquals(encoded, imapOutput);
        double decoded = fpEncoder.decode(encoded);
        Assert.assertEquals(decoded, rimapOutput, 0.00001);
    }
}
