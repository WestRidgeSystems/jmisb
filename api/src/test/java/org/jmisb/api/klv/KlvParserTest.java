package org.jmisb.api.klv;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0601.*;
import org.jmisb.core.klv.ArrayUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

public class KlvParserTest
{
    TestLogger LOGGER = TestLoggerFactory.getTestLogger(KlvParser.class);
    TestLogger UAS_DATALINK_MESSAGE_LOGGER = TestLoggerFactory.getTestLogger(UasDatalinkMessage.class);

    private final byte CHECKSUM_LEN = (byte)0x02;
    private final byte[] UNKNOWN_TAG_KEY = new byte[]{(byte)0x90, (byte)0x00};
    private final byte UNKNOWN_TAG_LEN = (byte)0x02;
    private final byte[] UNKNOWN_TAG_VALUE = new byte[]{(byte)0x0a, (byte)0x0b};
    private final byte SENSOR_LAT_LEN = (byte)0x04;
    private final byte[] SENSOR_LAT_VALUE = new byte[]{(byte)0x3c, (byte)0x4e, (byte)0xad, (byte)0xfa};
    private final byte SENSOR_LON_LEN = (byte)0x04;
    private final byte[] SENSOR_LON_VALUE = new byte[]{(byte)0xcd, (byte)0x6b, (byte)0x78, (byte)0x4e};
    private final byte SENSOR_ALT_LEN = (byte)0x02;
    private final byte[] SENSOR_ALT_VALUE = new byte[]{(byte)0x1b, (byte)0xc4};

    @BeforeTest
    public void resetLoggers()
    {
        LOGGER.clear();
        LOGGER.setEnabledLevels(Level.ERROR, Level.WARN, Level.INFO);
        UAS_DATALINK_MESSAGE_LOGGER.clear();
        UAS_DATALINK_MESSAGE_LOGGER.setEnabledLevels(Level.ERROR, Level.WARN, Level.INFO);
    }

    @AfterMethod
    public void checkLoggers()
    {
        Assert.assertEquals(LOGGER.getLoggingEvents().size(), 0);
        List<LoggingEvent> events = UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents();
        Assert.assertEquals(UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().size(), 0);
    }

    @Test
    public void testUasDatalink()
    {
        byte[] bytes = new byte[]{
                (byte)0x06, (byte)0x0e, (byte)0x2b, (byte)0x34, (byte)0x02, (byte)0x0b, (byte)0x01, (byte)0x01,
                (byte)0x0e, (byte)0x01, (byte)0x03, (byte)0x01, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x14, (byte)0x0d, (byte)0x04, (byte)0x3c, (byte)0x4e, (byte)0xad, (byte)0xfa, (byte)0x0e,
                (byte)0x04, (byte)0xcd, (byte)0x6b, (byte)0x78, (byte)0x4e, (byte)0x0f, (byte)0x02, (byte)0x1b,
                (byte)0xc4, (byte)0x01, (byte)0x02, (byte)0x2d, (byte)0xc4,
        };

        try
        {
            List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
            Assert.assertEquals(messages.size(), 1);

            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof UasDatalinkMessage);

            UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) message;
            Assert.assertEquals(datalinkMessage.getTags().size(), 3);

            SensorLatitude lat = (SensorLatitude) datalinkMessage.getField(UasDatalinkTag.SensorLatitude);
            SensorLongitude lon = (SensorLongitude) datalinkMessage.getField(UasDatalinkTag.SensorLongitude);
            SensorTrueAltitude alt = (SensorTrueAltitude) datalinkMessage.getField(UasDatalinkTag.SensorTrueAltitude);

            Assert.assertNotNull(lat);
            Assert.assertNotNull(lon);
            Assert.assertNotNull(alt);

            Assert.assertEquals(lat.getDegrees(), 42.4036, SensorLatitude.DELTA);
            Assert.assertEquals(lat.getBytes(), SENSOR_LAT_VALUE);
            Assert.assertEquals(lon.getDegrees(), -71.1284, SensorLongitude.DELTA);
            Assert.assertEquals(lon.getBytes(), SENSOR_LON_VALUE);
            Assert.assertEquals(alt.getMeters(), 1258.3, SensorTrueAltitude.DELTA);
            Assert.assertEquals(alt.getBytes(), SENSOR_ALT_VALUE);

        } catch (KlvParseException e)
        {
            Assert.fail("Parse exception");
        }
    }

    @Test
    public void testUnknownLabel()
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.put(KlvConstants.GeneralizedTransformationUl.getBytes());
        byteBuffer.put((byte)0x03);
        byteBuffer.put(new byte[]{0x00, 0x00, 0x00});

        try
        {
            List<IMisbMessage> messages = KlvParser.parseBytes(byteBuffer.array());
            Assert.assertEquals(messages.size(), 1);
            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof RawMisbMessage);
            RawMisbMessage rawMisbMessage = (RawMisbMessage) message;
            Assert.assertEquals(rawMisbMessage.getUniversalLabel(), KlvConstants.GeneralizedTransformationUl);
            Assert.assertEquals(rawMisbMessage.getBytes().length, 20);
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

    @Test
    public void testChecksumOnlyParse() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(21);
        byteBuffer.put(KlvConstants.UasDatalinkLocalUl.getBytes());
        byteBuffer.put((byte) 0x04);
        byteBuffer.put((byte) UasDatalinkTag.Checksum.getCode());
        byteBuffer.put(CHECKSUM_LEN);
        byteBuffer.put((byte) 0x4c);
        byteBuffer.put((byte) 0x51);
        byte[] bytes = byteBuffer.array();
        try {
            List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
            Assert.assertEquals(messages.size(), 1);

            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof UasDatalinkMessage);

            UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) message;
            Collection<UasDatalinkTag> tags = datalinkMessage.getTags();
            Assert.assertEquals(tags.size(), 0);
        } catch (KlvParseException e) {
            Assert.fail("Parse exception");
        }
    }

    @Test
    public void testUnknownTagParse() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(26);
        byteBuffer.put(KlvConstants.UasDatalinkLocalUl.getBytes());
        byteBuffer.put((byte) 0x09);
        byteBuffer.put(UNKNOWN_TAG_KEY);
        byteBuffer.put(UNKNOWN_TAG_LEN);
        byteBuffer.put(UNKNOWN_TAG_VALUE);
        byteBuffer.put((byte) UasDatalinkTag.Checksum.getCode());
        byteBuffer.put(CHECKSUM_LEN);
        byteBuffer.put((byte) 0x5a);
        byteBuffer.put((byte) 0xef);
        byte[] bytes = byteBuffer.array();
        try
        {
            Assert.assertEquals(LOGGER.getLoggingEvents().size(), 0);
            Assert.assertEquals(UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().size(), 0);
            List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
            Assert.assertEquals(LOGGER.getLoggingEvents().size(), 0);
            Assert.assertEquals(UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().size(), 1);
            LoggingEvent messageEvent = UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().get(0);
            Assert.assertEquals(messageEvent.getMessage(), "Unknown UAS Datalink tag: 2048");
            UAS_DATALINK_MESSAGE_LOGGER.clear();

            Assert.assertEquals(messages.size(), 1);

            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof UasDatalinkMessage);

            UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) message;
            Collection<UasDatalinkTag> tags = datalinkMessage.getTags();
            Assert.assertEquals(tags.size(), 0);
        } catch (KlvParseException e) {
            Assert.fail("Parse exception");
        }
    }

    @Test
    public void testMixedKnownAndUnknownTagParse() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(42);
        byteBuffer.put(KlvConstants.UasDatalinkLocalUl.getBytes());
        byteBuffer.put((byte) 0x19);
        byteBuffer.put((byte) UasDatalinkTag.SensorLatitude.getCode());
        byteBuffer.put(SENSOR_LAT_LEN);
        byteBuffer.put(SENSOR_LAT_VALUE);
        byteBuffer.put(UNKNOWN_TAG_KEY);
        byteBuffer.put(UNKNOWN_TAG_LEN);
        byteBuffer.put(UNKNOWN_TAG_VALUE);
        byteBuffer.put((byte) UasDatalinkTag.SensorLongitude.getCode());
        byteBuffer.put(SENSOR_LON_LEN);
        byteBuffer.put(SENSOR_LON_VALUE);
        byteBuffer.put((byte) UasDatalinkTag.SensorTrueAltitude.getCode());
        byteBuffer.put(SENSOR_ALT_LEN);
        byteBuffer.put(SENSOR_ALT_VALUE);
        byteBuffer.put((byte) UasDatalinkTag.Checksum.getCode());
        byteBuffer.put(CHECKSUM_LEN);
        byteBuffer.put((byte) 0x36);
        byteBuffer.put((byte) 0x68);
        byte[] bytes = byteBuffer.array();
        try {
            Assert.assertEquals(UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().size(), 0);
            List<IMisbMessage> messages = KlvParser.parseBytes(bytes);
            Assert.assertEquals(messages.size(), 1);
            Assert.assertEquals(UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().size(), 1);
            LoggingEvent messageEvent = UAS_DATALINK_MESSAGE_LOGGER.getLoggingEvents().get(0);
            Assert.assertEquals(messageEvent.getMessage(), "Unknown UAS Datalink tag: 2048");
            UAS_DATALINK_MESSAGE_LOGGER.clear();

            IMisbMessage message = messages.get(0);
            Assert.assertTrue(message instanceof UasDatalinkMessage);

            UasDatalinkMessage datalinkMessage = (UasDatalinkMessage) message;
            Collection<UasDatalinkTag> tags = datalinkMessage.getTags();
            Assert.assertEquals(tags.size(), 3);

            SensorLatitude lat = (SensorLatitude) datalinkMessage.getField(UasDatalinkTag.SensorLatitude);
            SensorLongitude lon = (SensorLongitude) datalinkMessage.getField(UasDatalinkTag.SensorLongitude);
            SensorTrueAltitude alt = (SensorTrueAltitude) datalinkMessage.getField(UasDatalinkTag.SensorTrueAltitude);

            Assert.assertNotNull(lat);
            Assert.assertNotNull(lon);
            Assert.assertNotNull(alt);

            Assert.assertEquals(lat.getDegrees(), 42.4036, SensorLatitude.DELTA);
            Assert.assertEquals(lat.getBytes(), SENSOR_LAT_VALUE);
            Assert.assertEquals(lon.getDegrees(), -71.1284, SensorLongitude.DELTA);
            Assert.assertEquals(lon.getBytes(), SENSOR_LON_VALUE);
            Assert.assertEquals(alt.getMeters(), 1258.3, SensorTrueAltitude.DELTA);
            Assert.assertEquals(alt.getBytes(), SENSOR_ALT_VALUE);
        } catch (KlvParseException e) {
            Assert.fail("Parse exception");
        }
    }
}
