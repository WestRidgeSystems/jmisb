package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

import static org.jmisb.api.klv.KlvConstants.UasDatalinkLocalUl;
import org.jmisb.api.klv.LoggerChecks;

public class UasDatalinkMessageTest extends LoggerChecks
{
    private UasDatalinkMessage message;
    private final double lat = 42.4036;
    private final double lon = -71.1284;
    private final double alt = 1258.3;

    public UasDatalinkMessageTest()
    {
        super(UasDatalinkMessage.class);
    }

    @BeforeTest
    public void createSample()
    {
        // Create a message with only three fields (sensor lat/lon/alt)
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(lat));
        values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(lon));
        values.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(alt));
        message = new UasDatalinkMessage(values);
    }

    @Test
    public void testUniversalLabel()
    {
        // Check that the correct universal label is applied
        Assert.assertEquals(message.getUniversalLabel(), UasDatalinkLocalUl);
    }

    @Test
    public void testDisplayHeader()
    {
        Assert.assertEquals(message.displayHeader(), "ST 0601");
    }
    
    @Test
    public void testFrameSimple()
    {
        // Frame a full packet
        byte[] bytes = message.frameMessage(false);
        //System.out.println(ArrayUtils.toHexString(bytes));

        // Check that the bytes begin with the correct 16-byte UL
        Assert.assertEquals(Arrays.copyOfRange(bytes, 0, 16), UasDatalinkLocalUl.getBytes());

        // Get the length
        // In this case since the total length is < 128, length should be encoded in short form

        // bytes  field
        //----------------
        // 6      latitude
        // 6      longitude
        // 4      true altitude
        // 4      checksum
        byte valueLength = bytes[16];
        Assert.assertEquals(valueLength, 20);
        Assert.assertEquals(bytes.length, 16 + 1 + 20);

        // Check latitude
        Assert.assertEquals(bytes[17], UasDatalinkTag.SensorLatitude.getCode());
        Assert.assertEquals(bytes[18], 4);
        byte[] encodedLat = Arrays.copyOfRange(bytes, 19, 23);
        SensorLatitude sensorLat = new SensorLatitude(encodedLat);
        Assert.assertEquals(sensorLat.getDegrees(), lat, SensorLatitude.DELTA);

        // Check longitude
        Assert.assertEquals(bytes[23], UasDatalinkTag.SensorLongitude.getCode());
        Assert.assertEquals(bytes[24], 4);
        byte[] encodedLon = Arrays.copyOfRange(bytes, 25, 29);
        SensorLongitude sensorLon = new SensorLongitude(encodedLon);
        Assert.assertEquals(sensorLon.getDegrees(), lon, SensorLongitude.DELTA);

        // Check altitude
        Assert.assertEquals(bytes[29], UasDatalinkTag.SensorTrueAltitude.getCode());
        Assert.assertEquals(bytes[30], 2);
        byte[] encodedAlt = Arrays.copyOfRange(bytes, 31, 33);
        SensorTrueAltitude sensorAlt = new SensorTrueAltitude(encodedAlt);
        Assert.assertEquals(sensorAlt.getMeters(), alt, SensorTrueAltitude.DELTA);

        // Check checksum
        Assert.assertEquals(bytes[33], UasDatalinkTag.Checksum.getCode());
        Assert.assertEquals(bytes[34], 2);
        byte[] expected = Checksum.compute(bytes, false);
        byte[] actual = Arrays.copyOfRange(bytes, 35, 37);
        Assert.assertEquals(actual, expected);

        // For debugging
        // System.out.println(ArrayUtils.toHexString(bytes));
    }

    @Test
    public void testReparse()
    {
        // Frame the message
        byte[] bytes = message.frameMessage(false);

        // ... and re-parse it
        try
        {
            UasDatalinkMessage msg = new UasDatalinkMessage(bytes);

            // Verify the messages are the same
            SensorLatitude sensorLatitude = (SensorLatitude) msg.getField(UasDatalinkTag.SensorLatitude);
            SensorLongitude sensorLongitude = (SensorLongitude) msg.getField(UasDatalinkTag.SensorLongitude);
            SensorTrueAltitude sensorTrueAltitude = (SensorTrueAltitude) msg.getField(UasDatalinkTag.SensorTrueAltitude);

            Assert.assertEquals(sensorLatitude.getDegrees(), lat, SensorLatitude.DELTA);
            Assert.assertEquals(sensorLongitude.getDegrees(), lon, SensorLongitude.DELTA);
            Assert.assertEquals(sensorTrueAltitude.getMeters(), alt, SensorTrueAltitude.DELTA);

        } catch (KlvParseException e)
        {
            Assert.fail("Could not parse UAS datalink message");
        }
    }

    /**
     * Test that missing and bad checksums cause KlvParseExceptions
     */
    @Test
    public void testChecksum()
    {
        // Frame the message
        byte[] bytes = message.frameMessage(false);

        // Introduce error - flip bits of byte 20
        bytes[20] = (byte)(~bytes[20] & 0xff);

        // Parse
        try
        {
            new UasDatalinkMessage(bytes);
            Assert.fail("Parsing should have failed due to bad checksum");
        } catch (KlvParseException e)
        {
            Assert.assertEquals(e.getMessage(), "Bad checksum");
        }

        // Chop off the checksum and verify we get another exception
        bytes = message.frameMessage(false);
        byte[] missingChecksum = Arrays.copyOfRange(bytes, 0, bytes.length - 4);
        missingChecksum[16] -= 4;
        try
        {
            new UasDatalinkMessage(missingChecksum);
            Assert.fail("Parsing should have failed due to missing checksum");
        } catch (KlvParseException e)
        {
            Assert.assertEquals(e.getMessage(), "Missing checksum");
        }
    }

    /**
     * Test we get the expected tags
     */
    @Test
    public void testTags()
    {
        // Frame the message
        Collection<UasDatalinkTag> tags = message.getTags();
        Assert.assertEquals(tags.size(), 3);
        Assert.assertTrue(tags.contains(UasDatalinkTag.SensorLatitude));
        Assert.assertTrue(tags.contains(UasDatalinkTag.SensorLongitude));
        Assert.assertTrue(tags.contains(UasDatalinkTag.SensorTrueAltitude));
    }
}
