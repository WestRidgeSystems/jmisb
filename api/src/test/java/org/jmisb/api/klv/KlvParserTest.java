package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.*;
import org.jmisb.core.klv.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class KlvParserTest
{
    @Test
    public void testUasDatalink()
    {
        byte[] bytes = new byte[]{
                (byte)0x06, (byte)0x0e, (byte)0x2b, (byte)0x34, (byte)0x02, (byte)0x0b, (byte)0x01, (byte)0x01,
                (byte)0x0e, (byte)0x01, (byte)0x03, (byte)0x01, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x14, (byte)0x0d, (byte)0x04, (byte)0x3c, (byte)0x4e, (byte)0xad, (byte)0xfa, (byte)0x0e,
                (byte)0x04, (byte)0xcd, (byte)0x6b, (byte)0x78, (byte)0x4e, (byte)0x0f, (byte)0x02, (byte)0x1b,
                (byte)0xc4, (byte)0x01, (byte)0x02, (byte)0x2b, (byte)0xc6,
        };

        try
        {
            List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
            Assert.assertEquals(messages.size(), 1);

            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof UasDatalinkMessage);

            UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) message;

            SensorLatitude lat = (SensorLatitude) datalinkMessage.getField(UasDatalinkTag.SensorLatitude);
            SensorLongitude lon = (SensorLongitude) datalinkMessage.getField(UasDatalinkTag.SensorLongitude);
            SensorTrueAltitude alt = (SensorTrueAltitude) datalinkMessage.getField(UasDatalinkTag.SensorTrueAltitude);

            Assert.assertNotNull(lat);
            Assert.assertNotNull(lon);
            Assert.assertNotNull(alt);

            Assert.assertEquals(lat.getDegrees(), 42.4036, SensorLatitude.DELTA);
            Assert.assertEquals(lon.getDegrees(), -71.1284, SensorLongitude.DELTA);
            Assert.assertEquals(alt.getMeters(), 1258.3, SensorTrueAltitude.DELTA);

        } catch (KlvParseException e)
        {
            Assert.fail("Parse exception");
        }
    }

    /**
     * Create test data
     */
    @SuppressWarnings("unused")
    private void createUasDatalink()
    {
        // Create a message with only three fields (sensor lat/lon/alt)
        final double lat = 42.4036;
        final double lon = -71.1284;
        final double alt = 1258.3;
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(lat));
        values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(lon));
        values.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(alt));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        byte[] bytes = message.frameMessage(false);
        System.out.println(ArrayUtils.toHexString(bytes, 8, true));
    }
}