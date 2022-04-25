package org.jmisb.eg0104;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.testng.annotations.Test;

public class PredatorUavMessageFactoryTest {

    public PredatorUavMessageFactoryTest() {}

    @Test
    public void checkBaseParse() throws KlvParseException {
        byte[] bytes = {
            (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x02, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x0e, (byte) 0x01, (byte) 0x01, (byte) 0x02,
                    (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00,
            (byte) 0x82, (byte) 0x02, (byte) 0xd0, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
                    (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x07,
                    (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x05,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x41, (byte) 0xfd,
                    (byte) 0x5c, (byte) 0x29, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x0a,
            (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0x16,
                    (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x44, (byte) 0x7a, (byte) 0x6a,
                    (byte) 0xe1, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
            (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x04,
                    (byte) 0x20, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x0a, (byte) 0x01,
                    (byte) 0x00, (byte) 0x04, (byte) 0x42, (byte) 0x35,
            (byte) 0x99, (byte) 0x9a, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x05,
                    (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x31, (byte) 0x30, (byte) 0x30,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x20, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x02, (byte) 0x31, (byte) 0x30, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
                    (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x07, (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x41, (byte) 0xb0,
                    (byte) 0x7a, (byte) 0xe1, (byte) 0x06, (byte) 0x0e,
            (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x07, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x32,
            (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x07, (byte) 0x07, (byte) 0x01, (byte) 0x10, (byte) 0x01,
                    (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x04, (byte) 0x41, (byte) 0xa0, (byte) 0x14, (byte) 0x7b, (byte) 0x06,
                    (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x03, (byte) 0x07, (byte) 0x02, (byte) 0x01,
            (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x08,
                    (byte) 0x00, (byte) 0x03, (byte) 0xf3, (byte) 0xb3, (byte) 0x5e, (byte) 0xde,
                    (byte) 0x0a, (byte) 0x7c, (byte) 0x06, (byte) 0x0e,
            (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x04, (byte) 0x20, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x01,
                    (byte) 0x00, (byte) 0x00, (byte) 0x07, (byte) 0x45,
            (byte) 0x4f, (byte) 0x20, (byte) 0x4e, (byte) 0x6f, (byte) 0x73, (byte) 0x65,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x02,
            (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                    (byte) 0x0e, (byte) 0x32, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x30,
                    (byte) 0x33, (byte) 0x33, (byte) 0x31, (byte) 0x30,
            (byte) 0x38, (byte) 0x30, (byte) 0x34, (byte) 0x35, (byte) 0x32, (byte) 0x06,
                    (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x07, (byte) 0x02, (byte) 0x01,
            (byte) 0x02, (byte) 0x07, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x0e,
                    (byte) 0x32, (byte) 0x30, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x33,
                    (byte) 0x33, (byte) 0x31, (byte) 0x30, (byte) 0x37,
            (byte) 0x35, (byte) 0x30, (byte) 0x35, (byte) 0x31, (byte) 0x06, (byte) 0x0e,
                    (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01,
            (byte) 0x03, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x08, (byte) 0xc0,
                    (byte) 0x41, (byte) 0x65, (byte) 0x13, (byte) 0x4a, (byte) 0x87, (byte) 0x63,
                    (byte) 0x29, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
            (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07,
                    (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0x04, (byte) 0x00,
                    (byte) 0x00, (byte) 0x08, (byte) 0x40, (byte) 0x61,
            (byte) 0x54, (byte) 0x5f, (byte) 0x71, (byte) 0x31, (byte) 0x41, (byte) 0x0d,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x01,
            (byte) 0x09, (byte) 0x02, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x04, (byte) 0x45, (byte) 0xe1, (byte) 0xcb, (byte) 0x90, (byte) 0x06,
                    (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x01, (byte) 0x02,
                    (byte) 0x01, (byte) 0x02, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x04,
                    (byte) 0x45, (byte) 0x4a, (byte) 0x18, (byte) 0xa9,
            (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                    (byte) 0x02, (byte) 0x04, (byte) 0x02, (byte) 0x00,
            (byte) 0x08, (byte) 0xc0, (byte) 0x41, (byte) 0x6b, (byte) 0x46, (byte) 0x46,
                    (byte) 0x5e, (byte) 0xab, (byte) 0xfe, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
                    (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01,
            (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x02,
                    (byte) 0x06, (byte) 0x02, (byte) 0x00, (byte) 0x08, (byte) 0x40, (byte) 0x61,
                    (byte) 0x52, (byte) 0x2d, (byte) 0x32, (byte) 0x0e,
            (byte) 0xb7, (byte) 0x4f, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x01,
                    (byte) 0x08, (byte) 0x01, (byte) 0x01, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x46, (byte) 0x0e, (byte) 0xd8,
                    (byte) 0x00, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07,
            (byte) 0x01, (byte) 0x10, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x04, (byte) 0xbf, (byte) 0xb9, (byte) 0x04, (byte) 0x1d,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
            (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x01,
                    (byte) 0x10, (byte) 0x01, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x04, (byte) 0x42, (byte) 0x45, (byte) 0xa7,
            (byte) 0x67, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x07, (byte) 0x01, (byte) 0x10,
                    (byte) 0x01, (byte) 0x03, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x04, (byte) 0x43, (byte) 0x49, (byte) 0x54, (byte) 0x7b,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x20,
            (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                    (byte) 0x04, (byte) 0x3f, (byte) 0x40, (byte) 0xb0, (byte) 0x0b, (byte) 0x06,
                    (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
            (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02,
                    (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x00, (byte) 0x08,
                    (byte) 0xc0, (byte) 0x41, (byte) 0x64, (byte) 0xec,
            (byte) 0x27, (byte) 0xbe, (byte) 0xea, (byte) 0xe8, (byte) 0x06, (byte) 0x0e,
                    (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03,
                    (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01,
            (byte) 0x03, (byte) 0x0b, (byte) 0x01, (byte) 0x00, (byte) 0x08, (byte) 0x40,
                    (byte) 0x61, (byte) 0x54, (byte) 0x64, (byte) 0xfa, (byte) 0x37, (byte) 0x49,
                    (byte) 0x24, (byte) 0x06, (byte) 0x0e, (byte) 0x2b,
            (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x07,
                    (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0x08, (byte) 0x01,
                    (byte) 0x00, (byte) 0x08, (byte) 0xc0, (byte) 0x41,
            (byte) 0x65, (byte) 0x07, (byte) 0x63, (byte) 0x00, (byte) 0x5d, (byte) 0xb2,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01,
            (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0x0c, (byte) 0x01, (byte) 0x00,
                    (byte) 0x08, (byte) 0x40, (byte) 0x61, (byte) 0x54, (byte) 0x6c, (byte) 0x1a,
                    (byte) 0x26, (byte) 0x02, (byte) 0xdb, (byte) 0x06,
            (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                    (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
                    (byte) 0x09, (byte) 0x01, (byte) 0x00, (byte) 0x08,
            (byte) 0xc0, (byte) 0x41, (byte) 0x65, (byte) 0x39, (byte) 0x64, (byte) 0xa0,
                    (byte) 0x9b, (byte) 0x3d, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03,
            (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03, (byte) 0x0d,
                    (byte) 0x01, (byte) 0x00, (byte) 0x08, (byte) 0x40, (byte) 0x61, (byte) 0x54,
                    (byte) 0x5a, (byte) 0x0d, (byte) 0x9a, (byte) 0xdd,
            (byte) 0xef, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                    (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02,
                    (byte) 0x01, (byte) 0x03, (byte) 0x0a, (byte) 0x01,
            (byte) 0x00, (byte) 0x08, (byte) 0xc0, (byte) 0x41, (byte) 0x65, (byte) 0x1e,
                    (byte) 0xe1, (byte) 0x8b, (byte) 0x4e, (byte) 0x23, (byte) 0x06, (byte) 0x0e,
                    (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
            (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01, (byte) 0x02, (byte) 0x01,
                    (byte) 0x03, (byte) 0x0e, (byte) 0x01, (byte) 0x00, (byte) 0x08, (byte) 0x40,
                    (byte) 0x61, (byte) 0x54, (byte) 0x53, (byte) 0x1d,
            (byte) 0xdb, (byte) 0xcd, (byte) 0x29
        };
        PredatorUavMessageFactory factory = new PredatorUavMessageFactory();
        PredatorUavMessage predatorUavMessage = factory.create(bytes);
        assertNotNull(predatorUavMessage);
        assertEquals(predatorUavMessage.displayHeader(), "Predator EG0104.5");
        assertEquals(predatorUavMessage.getIdentifiers().size(), 31);
        assertEquals(
                predatorUavMessage.getUniversalLabel(),
                PredatorUavMessage.PredatorMetadataLocalSetUl);

        // Frame Center Latitude
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.FrameCenterLatitude));
        IPredatorMetadataValue v =
                predatorUavMessage.getField(PredatorMetadataKey.FrameCenterLatitude);
        assertTrue(v instanceof LatLonValue);
        LatLonValue frameCenterLat = (LatLonValue) v;
        assertEquals(frameCenterLat.getDisplayName(), "Frame Center Latitude");
        assertEquals(frameCenterLat.getDisplayableValue(), "-34.7897\u00B0");
        assertEquals(frameCenterLat.getDegrees(), -34.7897, 0.0001);
        assertEquals(
                predatorUavMessage
                        .getField((IKlvKey) PredatorMetadataKey.FrameCenterLatitude)
                        .getDisplayableValue(),
                "-34.7897\u00B0");

        // Frame Center Longitude
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.FrameCenterLongitude));
        v = predatorUavMessage.getField(PredatorMetadataKey.FrameCenterLongitude);
        assertTrue(v instanceof LatLonValue);
        LatLonValue frameCenterLon = (LatLonValue) v;
        assertEquals(frameCenterLon.getDisplayName(), "Frame Center Longitude");
        assertEquals(frameCenterLon.getDisplayableValue(), "138.6367\u00B0");
        assertEquals(frameCenterLon.getDegrees(), 138.6367, 0.0001);

        // Frame Center Elevation
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.FrameCenterElevation));
        v = predatorUavMessage.getField(PredatorMetadataKey.FrameCenterElevation);
        assertTrue(v instanceof AltitudeValue);
        AltitudeValue frameCenterElevation = (AltitudeValue) v;
        assertEquals(frameCenterElevation.getDisplayName(), "Frame Center Elevation");
        assertEquals(frameCenterElevation.getDisplayableValue(), "1001.7 m");
        assertEquals(frameCenterElevation.getElevation(), 1001.6700, 0.0001);

        // Image Coordinate System
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.ImageCoordinateSystem));
        v = predatorUavMessage.getField(PredatorMetadataKey.ImageCoordinateSystem);
        assertTrue(v instanceof TextValue);
        TextValue imageCoordinateSystem = (TextValue) v;
        assertEquals(imageCoordinateSystem.getDisplayName(), "Image Coordinate System");
        assertEquals(imageCoordinateSystem.getDisplayableValue(), "2");

        // Target Width
        assertTrue(predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.TargetWidth));
        v = predatorUavMessage.getField(PredatorMetadataKey.TargetWidth);
        assertTrue(v instanceof TargetWidth);
        TargetWidth targetWidth = (TargetWidth) v;
        assertEquals(targetWidth.getDisplayName(), "Target Width");
        assertEquals(targetWidth.getDisplayableValue(), "7225.4 m");
        assertEquals(targetWidth.getDistance(), 7225.445, 0.001);

        // Start Date Time - UTC
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.StartDateTimeUtc));
        v = predatorUavMessage.getField(PredatorMetadataKey.StartDateTimeUtc);
        assertTrue(v instanceof DateTimeUTC);
        DateTimeUTC startDateTime = (DateTimeUTC) v;
        assertEquals(startDateTime.getDisplayName(), "Start Date Time (UTC)");
        assertEquals(startDateTime.getDisplayableValue(), "20050331080452");

        // Event Start Date Time - UTC
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.EventStartDateTimeUtc));
        v = predatorUavMessage.getField(PredatorMetadataKey.EventStartDateTimeUtc);
        assertTrue(v instanceof DateTimeUTC);
        DateTimeUTC eventStartDateTime = (DateTimeUTC) v;
        assertEquals(eventStartDateTime.getDisplayName(), "Event Start Date Time (UTC)");
        assertEquals(eventStartDateTime.getDisplayableValue(), "20050331075051");

        // User Defined Time Stamp
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.UserDefinedTimeStamp));
        v = predatorUavMessage.getField(PredatorMetadataKey.UserDefinedTimeStamp);
        assertTrue(v instanceof UserDefinedTimeStamp);
        UserDefinedTimeStamp timestamp = (UserDefinedTimeStamp) v;
        assertEquals(timestamp.getDisplayName(), "User Defined Time Stamp");
        assertEquals(timestamp.getDisplayableValue(), "1112376646437500");

        // Corner Latitude Point 1
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLatitudePoint1));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLatitudePoint1);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLatitudePoint1 = (LatLonValue) v;
        assertEquals(cornerLatitudePoint1.getDisplayName(), "Corner Latitude Point 1");
        assertEquals(cornerLatitudePoint1.getDisplayableValue(), "-34.7885\u00B0");
        assertEquals(cornerLatitudePoint1.getDegrees(), -34.7885, 0.0001);

        // Corner Latitude Point 2
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLatitudePoint2));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLatitudePoint2);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLatitudePoint2 = (LatLonValue) v;
        assertEquals(cornerLatitudePoint2.getDisplayName(), "Corner Latitude Point 2");
        assertEquals(cornerLatitudePoint2.getDisplayableValue(), "-34.7893\u00B0");
        assertEquals(cornerLatitudePoint2.getDegrees(), -34.7893, 0.0001);

        // Corner Latitude Point 3
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLatitudePoint3));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLatitudePoint3);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLatitudePoint3 = (LatLonValue) v;
        assertEquals(cornerLatitudePoint3.getDisplayName(), "Corner Latitude Point 3");
        assertEquals(cornerLatitudePoint3.getDisplayableValue(), "-34.7908\u00B0");
        assertEquals(cornerLatitudePoint3.getDegrees(), -34.7908, 0.0001);

        // Corner Latitude Point 4
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLatitudePoint4));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLatitudePoint4);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLatitudePoint4 = (LatLonValue) v;
        assertEquals(cornerLatitudePoint4.getDisplayName(), "Corner Latitude Point 4");
        assertEquals(cornerLatitudePoint4.getDisplayableValue(), "-34.7900\u00B0");
        assertEquals(cornerLatitudePoint4.getDegrees(), -34.7900, 0.0001);

        // Corner Longitude Point 1
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLongitudePoint1));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLongitudePoint1);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLongitudePoint1 = (LatLonValue) v;
        assertEquals(cornerLongitudePoint1.getDisplayName(), "Corner Longitude Point 1");
        assertEquals(cornerLongitudePoint1.getDisplayableValue(), "138.6373\u00B0");
        assertEquals(cornerLongitudePoint1.getDegrees(), 138.6373, 0.0001);

        // Corner Longitude Point 2
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLongitudePoint2));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLongitudePoint2);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLongitudePoint2 = (LatLonValue) v;
        assertEquals(cornerLongitudePoint2.getDisplayName(), "Corner Longitude Point 2");
        assertEquals(cornerLongitudePoint2.getDisplayableValue(), "138.6382\u00B0");
        assertEquals(cornerLongitudePoint2.getDegrees(), 138.6382, 0.0001);

        // Corner Longitude Point 3
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLongitudePoint3));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLongitudePoint3);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLongitudePoint3 = (LatLonValue) v;
        assertEquals(cornerLongitudePoint3.getDisplayName(), "Corner Longitude Point 3");
        assertEquals(cornerLongitudePoint3.getDisplayableValue(), "138.6360\u00B0");
        assertEquals(cornerLongitudePoint3.getDegrees(), 138.6360, 0.0001);

        // Corner Longitude Point 4
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.CornerLongitudePoint4));
        v = predatorUavMessage.getField(PredatorMetadataKey.CornerLongitudePoint4);
        assertTrue(v instanceof LatLonValue);
        LatLonValue cornerLongitudePoint4 = (LatLonValue) v;
        assertEquals(cornerLongitudePoint4.getDisplayName(), "Corner Longitude Point 4");
        assertEquals(cornerLongitudePoint4.getDisplayableValue(), "138.6351\u00B0");
        assertEquals(cornerLongitudePoint4.getDegrees(), 138.6351, 0.0001);

        // Slant Range
        assertTrue(predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.SlantRange));
        v = predatorUavMessage.getField(PredatorMetadataKey.SlantRange);
        assertTrue(v instanceof SlantRange);
        SlantRange slantRange = (SlantRange) v;
        assertEquals(slantRange.getDisplayName(), "Slant Range");
        assertEquals(slantRange.getDisplayableValue(), "9142.0 m");
        assertEquals(slantRange.getRange(), 9142.000, 0.001);

        // Sensor Roll Angle
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.SensorRollAngle));
        v = predatorUavMessage.getField(PredatorMetadataKey.SensorRollAngle);
        assertTrue(v instanceof AngleValue);
        AngleValue sensorRollAngle = (AngleValue) v;
        assertEquals(sensorRollAngle.getDisplayName(), "Sensor Roll Angle");
        assertEquals(sensorRollAngle.getDisplayableValue(), "-1.4454\u00B0");
        assertEquals(sensorRollAngle.getDegrees(), -1.4454, 0.0001);

        // Angle to North
        assertTrue(predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.AngleToNorth));
        v = predatorUavMessage.getField(PredatorMetadataKey.AngleToNorth);
        assertTrue(v instanceof AngleValue);
        AngleValue angleToNorth = (AngleValue) v;
        assertEquals(angleToNorth.getDisplayName(), "Angle to North");
        assertEquals(angleToNorth.getDisplayableValue(), "49.4135\u00B0");
        assertEquals(angleToNorth.getDegrees(), 49.4135, 0.0001);

        // Obliquity Angle
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.ObliquityAngle));
        v = predatorUavMessage.getField(PredatorMetadataKey.ObliquityAngle);
        assertTrue(v instanceof AngleValue);
        AngleValue obliquityAngle = (AngleValue) v;
        assertEquals(obliquityAngle.getDisplayName(), "Obliquity Angle");
        assertEquals(obliquityAngle.getDisplayableValue(), "201.3300\u00B0");
        assertEquals(obliquityAngle.getDegrees(), 201.3300, 0.0001);

        // Platform Roll Angle
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.PlatformRollAngle));
        v = predatorUavMessage.getField(PredatorMetadataKey.PlatformRollAngle);
        assertTrue(v instanceof AngleValue);
        AngleValue platformRollAngle = (AngleValue) v;
        assertEquals(platformRollAngle.getDisplayName(), "Platform Roll Angle");
        assertEquals(platformRollAngle.getDisplayableValue(), "22.0600\u00B0");
        assertEquals(platformRollAngle.getDegrees(), 22.0600, 0.0001);

        // Platform Pitch Angle
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.PlatformPitchAngle));
        v = predatorUavMessage.getField(PredatorMetadataKey.PlatformPitchAngle);
        assertTrue(v instanceof AngleValue);
        AngleValue platformPitchAngle = (AngleValue) v;
        assertEquals(platformPitchAngle.getDisplayName(), "Platform Pitch Angle");
        assertEquals(platformPitchAngle.getDisplayableValue(), "31.6700\u00B0");
        assertEquals(platformPitchAngle.getDegrees(), 31.6700, 0.0001);

        // Platform Heading Angle
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.PlatformHeadingAngle));
        v = predatorUavMessage.getField(PredatorMetadataKey.PlatformHeadingAngle);
        assertTrue(v instanceof AngleValue);
        AngleValue platformHeadingAngle = (AngleValue) v;
        assertEquals(platformHeadingAngle.getDisplayName(), "Platform Heading Angle");
        assertEquals(platformHeadingAngle.getDisplayableValue(), "20.0100\u00B0");
        assertEquals(platformHeadingAngle.getDegrees(), 20.0100, 0.0001);

        // Field of View (Horizontal)
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.FieldOfViewHorizontal));
        v = predatorUavMessage.getField(PredatorMetadataKey.FieldOfViewHorizontal);
        assertTrue(v instanceof AngleValue);
        AngleValue fieldOfViewHorizontal = (AngleValue) v;
        assertEquals(fieldOfViewHorizontal.getDisplayName(), "Field of View - Horizontal");
        assertEquals(fieldOfViewHorizontal.getDisplayableValue(), "0.7527\u00B0");
        assertEquals(fieldOfViewHorizontal.getDegrees(), 0.7527, 0.0001);

        // Field of View (Vertical)
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.FieldOfViewVertical));
        v = predatorUavMessage.getField(PredatorMetadataKey.FieldOfViewVertical);
        assertTrue(v instanceof AngleValue);
        AngleValue fieldOfViewVertical = (AngleValue) v;
        assertEquals(fieldOfViewVertical.getDisplayName(), "Field of View - Vertical");
        assertEquals(fieldOfViewVertical.getDisplayableValue(), "45.4000\u00B0");
        assertEquals(fieldOfViewVertical.getDegrees(), 45.4000, 0.0001);

        // Device Altitude
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.DeviceAltitude));
        v = predatorUavMessage.getField(PredatorMetadataKey.DeviceAltitude);
        assertTrue(v instanceof AltitudeValue);
        AltitudeValue deviceAlt = (AltitudeValue) v;
        assertEquals(deviceAlt.getDisplayName(), "Device Altitude");
        assertEquals(deviceAlt.getDisplayableValue(), "3233.5 m");
        assertEquals(deviceAlt.getElevation(), 3233.5413, 0.0001);

        // Device Latitude
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.DeviceLatitude));
        v = predatorUavMessage.getField(PredatorMetadataKey.DeviceLatitude);
        assertTrue(v instanceof LatLonValue);
        LatLonValue deviceLat = (LatLonValue) v;
        assertEquals(deviceLat.getDisplayName(), "Device Latitude");
        assertEquals(deviceLat.getDisplayableValue(), "-34.8381\u00B0");
        assertEquals(deviceLat.getDegrees(), -34.8381, 0.0001);

        // Device Longitude
        assertTrue(
                predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.DeviceLongitude));
        v = predatorUavMessage.getField(PredatorMetadataKey.DeviceLongitude);
        assertTrue(v instanceof LatLonValue);
        LatLonValue deviceLon = (LatLonValue) v;
        assertEquals(deviceLon.getDisplayName(), "Device Longitude");
        assertEquals(deviceLon.getDisplayableValue(), "138.5680\u00B0");
        assertEquals(deviceLon.getDegrees(), 138.5680, 0.0001);

        // Image Source Device
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.ImageSourceDevice));
        v = predatorUavMessage.getField(PredatorMetadataKey.ImageSourceDevice);
        assertTrue(v instanceof ImageSourceDevice);
        ImageSourceDevice imageSourceDevice = (ImageSourceDevice) v;
        assertEquals(imageSourceDevice.getDisplayName(), "Image Source Device");
        assertEquals(imageSourceDevice.getDisplayableValue(), "EO Nose");

        // Episode Number
        assertTrue(predatorUavMessage.getIdentifiers().contains(PredatorMetadataKey.EpisodeNumber));
        v = predatorUavMessage.getField(PredatorMetadataKey.EpisodeNumber);
        assertTrue(v instanceof TextValue);
        TextValue episodeNumber = (TextValue) v;
        assertEquals(episodeNumber.getDisplayName(), "Episode Number");
        assertEquals(episodeNumber.getDisplayableValue(), "100");

        // Device Designation
        assertTrue(
                predatorUavMessage
                        .getIdentifiers()
                        .contains(PredatorMetadataKey.DeviceDesignation));
        v = predatorUavMessage.getField(PredatorMetadataKey.DeviceDesignation);
        assertTrue(v instanceof TextValue);
        TextValue deviceDesignation = (TextValue) v;
        assertEquals(deviceDesignation.getDisplayName(), "Device Designation");
        assertEquals(deviceDesignation.getDisplayableValue(), "10");
    }
}
