package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UasDatalinkStringTest {
    @Test
    public void testMissionId() {
        // Example from Tag 3 Mission ID
        final String stringVal = "MISSION01";
        final byte[] bytes = new byte[] {0x4d, 0x49, 0x53, 0x53, 0x49, 0x4f, 0x4e, 0x30, 0x31};

        UasDatalinkString idFromString =
                new UasDatalinkString(UasDatalinkString.MISSION_ID, stringVal);
        Assert.assertEquals(idFromString.getDisplayName(), UasDatalinkString.MISSION_ID);
        UasDatalinkString idFromBytes = new UasDatalinkString(UasDatalinkString.MISSION_ID, bytes);
        Assert.assertEquals(idFromBytes.getDisplayName(), UasDatalinkString.MISSION_ID);

        Assert.assertEquals(idFromString.getBytes(), bytes);
        Assert.assertEquals(idFromString.getDisplayableValue(), "MISSION01");
        Assert.assertEquals(idFromBytes.getValue(), stringVal);
        Assert.assertEquals(idFromBytes.getDisplayableValue(), "MISSION01");
    }

    @Test
    public void testFactoryMissionId() throws KlvParseException {
        byte[] bytes = new byte[] {0x4d, 0x49, 0x53, 0x53, 0x49, 0x4f, 0x4e, 0x30, 0x31};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.MissionId, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString id = (UasDatalinkString) v;
        Assert.assertEquals(id.getDisplayName(), UasDatalinkString.MISSION_ID);
        Assert.assertEquals(id.getBytes(), bytes);
        Assert.assertEquals(id.getDisplayableValue(), "MISSION01");
    }

    @Test
    public void testFactoryPlatformTailNumber() throws KlvParseException {
        byte[] bytes = new byte[] {0x41, 0x46, 0x2D, 0x31, 0x30, 0x31};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformTailNumber, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.PLATFORM_TAIL_NUMBER);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "AF-101");
    }

    @Test
    public void testFactoryAlternatePlatformName() throws KlvParseException {
        byte[] bytes = new byte[] {0x41, 0x50, 0x41, 0x43, 0x48, 0x45};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.AlternatePlatformName, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.ALTERNATE_PLATFORM_NAME);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "APACHE");
    }

    @Test
    public void testFactoryStreamDesignator() throws KlvParseException {
        byte[] bytes = new byte[] {0x42, 0x4C, 0x55, 0x45};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.StreamDesignator, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.STREAM_DESIGNATOR);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "BLUE");
    }

    @Test
    public void testFactoryOperationalBase() throws KlvParseException {
        byte[] bytes = new byte[] {0x42, 0x41, 0x53, 0x45, 0x30, 0x31};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.OperationalBase, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.OPERATIONAL_BASE);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "BASE01");
    }

    @Test
    public void testFactoryBroadcastSource() throws KlvParseException {
        byte[] bytes = new byte[] {0x48, 0x4F, 0x4D, 0x45};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.BroadcastSource, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.BROADCAST_SOURCE);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "HOME");
    }

    @Test
    public void testFactoryPlatformDesignation() throws KlvParseException {
        byte[] bytes = new byte[] {0x4D, 0x51, 0x31, 0x2D, 0x42};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformDesignation, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.PLATFORM_DESIGNATION);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "MQ1-B");
    }

    @Test
    public void testFactoryImageSourceSensor() throws KlvParseException {
        byte[] bytes = new byte[] {0x45, 0x4F};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageSourceSensor, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.IMAGE_SOURCE_SENSOR);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "EO");
    }

    @Test
    public void testFactoryImageCoordinateSystem() throws KlvParseException {
        byte[] bytes = new byte[] {0x57, 0x47, 0x53, 0x2D, 0x38, 0x34};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageCoordinateSystem, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.IMAGE_COORDINATE_SYSTEM);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "WGS-84");
    }

    @Test
    public void testFactoryPlatformCallsign() throws KlvParseException {
        byte[] bytes = new byte[] {0x54, 0x4F, 0x50, 0x20, 0x47, 0x55, 0x4E};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformCallSign, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.PLATFORM_CALL_SIGN);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "TOP GUN");
    }

    @Test
    public void testFactoryTargetId() throws KlvParseException {
        byte[] bytes = new byte[] {0x41, 0x31, 0x32, 0x33};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TargetId, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.TARGET_ID);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "A123");
    }

    @Test
    public void testFactoryCommunicationsMethod() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x46, 0x72, 0x65, 0x71, 0x75, 0x65, 0x6E, 0x63, 0x79, 0x20, 0x4D, 0x6F, 0x64,
                    0x75, 0x6C, 0x61, 0x74, 0x69, 0x6F, 0x6E
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.CommunicationsMethod, bytes);
        Assert.assertTrue(v instanceof UasDatalinkString);
        UasDatalinkString str = (UasDatalinkString) v;
        Assert.assertEquals(str.getDisplayName(), UasDatalinkString.COMMUNICATIONS_METHOD);
        Assert.assertEquals(str.getBytes(), bytes);
        Assert.assertEquals(str.getDisplayableValue(), "Frequency Modulation");
    }
}
