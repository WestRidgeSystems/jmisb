package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SensorFrameRateTest {
    @Test
    public void stExample() {
        double value = 59.94;
        int numerator = 60000;
        int denominator = 1001;
        byte[] origBytes =
                new byte[] {(byte) 0x83, (byte) 0xD4, (byte) 0x60, (byte) 0x87, (byte) 0x69};

        SensorFrameRate sensorFrameRate = new SensorFrameRate(origBytes);
        assertEquals(sensorFrameRate.getDisplayName(), "Sensor Frame Rate");
        assertEquals(sensorFrameRate.getFrameRate(), value, 0.001);
        assertEquals(sensorFrameRate.getBytes(), origBytes);
        assertEquals(sensorFrameRate.getDisplayableValue(), "59.94 fps");
        assertEquals(sensorFrameRate.getNumerator(), 60000);
        assertEquals(sensorFrameRate.getDenominator(), 1001);

        sensorFrameRate = new SensorFrameRate(numerator, denominator);
        assertEquals(sensorFrameRate.getDisplayName(), "Sensor Frame Rate");
        assertEquals(sensorFrameRate.getFrameRate(), value, 0.001);
        assertEquals(sensorFrameRate.getBytes(), origBytes);
        assertEquals(sensorFrameRate.getNumerator(), 60000);
        assertEquals(sensorFrameRate.getDenominator(), 1001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x83, (byte) 0xD4, (byte) 0x60, (byte) 0x87, (byte) 0x69};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.SensorFrameRatePack, bytes);
        assertTrue(v instanceof SensorFrameRate);
        assertEquals(v.getDisplayName(), "Sensor Frame Rate");
        SensorFrameRate sensorFrameRate = (SensorFrameRate) v;
        assertEquals(sensorFrameRate.getFrameRate(), 59.94, 0.001);
        assertEquals(sensorFrameRate.getNumerator(), 60000);
        assertEquals(sensorFrameRate.getDenominator(), 1001);
        assertEquals(
                sensorFrameRate.getBytes(),
                new byte[] {(byte) 0x83, (byte) 0xD4, (byte) 0x60, (byte) 0x87, (byte) 0x69});
        Assert.assertEquals(sensorFrameRate.getDisplayableValue(), "59.94 fps");
    }

    @Test
    public void testFromNoDenominator() throws KlvParseException {
        SensorFrameRate sensorFrameRate = new SensorFrameRate(30);
        assertEquals(sensorFrameRate.getDisplayName(), "Sensor Frame Rate");
        assertEquals(sensorFrameRate.getFrameRate(), 30.0);
        assertEquals(sensorFrameRate.getNumerator(), 30);
        assertEquals(sensorFrameRate.getDenominator(), 1);
        assertEquals(sensorFrameRate.getBytes(), new byte[] {0x1E});
        Assert.assertEquals(sensorFrameRate.getDisplayableValue(), "30.00 fps");
    }

    @Test
    public void testFromDenominator1() throws KlvParseException {
        SensorFrameRate sensorFrameRate = new SensorFrameRate(30, 1);
        assertEquals(sensorFrameRate.getDisplayName(), "Sensor Frame Rate");
        assertEquals(sensorFrameRate.getFrameRate(), 30.0);
        assertEquals(sensorFrameRate.getNumerator(), 30);
        assertEquals(sensorFrameRate.getDenominator(), 1);
        assertEquals(sensorFrameRate.getBytes(), new byte[] {0x1E});
        Assert.assertEquals(sensorFrameRate.getDisplayableValue(), "30.00 fps");
    }

    @Test
    public void testFromBytesNoDenominator() throws KlvParseException {
        SensorFrameRate sensorFrameRate = new SensorFrameRate(new byte[] {0x1E});
        assertEquals(sensorFrameRate.getDisplayName(), "Sensor Frame Rate");
        assertEquals(sensorFrameRate.getFrameRate(), 30.0);
        assertEquals(sensorFrameRate.getNumerator(), 30);
        assertEquals(sensorFrameRate.getDenominator(), 1);
        assertEquals(sensorFrameRate.getBytes(), new byte[] {0x1E});
        Assert.assertEquals(sensorFrameRate.getDisplayableValue(), "30.00 fps");
    }
}
