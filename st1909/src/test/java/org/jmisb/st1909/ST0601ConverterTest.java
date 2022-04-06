package org.jmisb.st1909;

import static org.testng.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.jmisb.api.klv.st0102.ST0102Version;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.localset.CcMethod;
import org.jmisb.api.klv.st0102.localset.ClassificationLocal;
import org.jmisb.api.klv.st0102.localset.OcMethod;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet.Builder;
import org.jmisb.st0601.CornerOffset;
import org.jmisb.st0601.FlagDataKey;
import org.jmisb.st0601.FrameCenterElevation;
import org.jmisb.st0601.FrameCenterHae;
import org.jmisb.st0601.FrameCenterLatitude;
import org.jmisb.st0601.FrameCenterLongitude;
import org.jmisb.st0601.FullCornerLatitude;
import org.jmisb.st0601.FullCornerLongitude;
import org.jmisb.st0601.GenericFlagData01;
import org.jmisb.st0601.HorizontalFov;
import org.jmisb.st0601.IUasDatalinkValue;
import org.jmisb.st0601.LaserPrfCode;
import org.jmisb.st0601.NestedSecurityMetadata;
import org.jmisb.st0601.PrecisionTimeStamp;
import org.jmisb.st0601.SensorEllipsoidHeight;
import org.jmisb.st0601.SensorEllipsoidHeightExtended;
import org.jmisb.st0601.SensorLatitude;
import org.jmisb.st0601.SensorLongitude;
import org.jmisb.st0601.SensorRelativeAzimuth;
import org.jmisb.st0601.SensorRelativeElevation;
import org.jmisb.st0601.SensorTrueAltitude;
import org.jmisb.st0601.SlantRange;
import org.jmisb.st0601.TargetLocationElevation;
import org.jmisb.st0601.TargetLocationLatitude;
import org.jmisb.st0601.TargetLocationLongitude;
import org.jmisb.st0601.TargetWidth;
import org.jmisb.st0601.TargetWidthExtended;
import org.jmisb.st0601.UasDatalinkMessage;
import org.jmisb.st0601.UasDatalinkString;
import org.jmisb.st0601.UasDatalinkTag;
import org.jmisb.st0601.VerticalFov;
import org.testng.annotations.Test;

/** Unit tests for ST0601Converter. */
public class ST0601ConverterTest {

    public ST0601ConverterTest() {}

    @Test
    public void checkPlatformNameFromDesignationOnly() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "Testname"));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformName));
        assertEquals(metadata.getValue(MetadataKey.PlatformName), "Testname");
    }

    @Test
    public void checkPlatformNameFromDesignationAndCallsign() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "Testname"));
        map.put(
                UasDatalinkTag.PlatformCallSign,
                new UasDatalinkString(UasDatalinkString.PLATFORM_CALL_SIGN, "Romeo Sierra"));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformName));
        assertEquals(metadata.getValue(MetadataKey.PlatformName), "Testname Romeo Sierra");
    }

    @Test
    public void checkPlatformLatitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-34.34211));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformLatitude));
        // ST1909-17, ST1909-18, ST1909-19
        assertEquals(metadata.getValue(MetadataKey.PlatformLatitude), "-34.3421\u00B0 LAT");
    }

    @Test
    public void checkPlatformLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(150.87432));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformLongitude));
        // ST1909-20, ST1909-21, ST1909-22
        assertEquals(metadata.getValue(MetadataKey.PlatformLongitude), "150.8743\u00B0 LON");
    }

    @Test
    public void checkPlatformAltitudeHAE() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(1150.872));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformAltitude));
        // ST1909-23, ST1909-24, ST1909-26, ST1909-27, ST1909-28
        assertEquals(metadata.getValue(MetadataKey.PlatformAltitude), "1150m HAE ALT");
    }

    @Test
    public void checkPlatformAltitudeMSL() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(1150.872));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformAltitude));
        // ST1909-23, ST1909-24, ST1909-25, ST1909-27, ST1909-28
        assertEquals(metadata.getValue(MetadataKey.PlatformAltitude), "1150m MSL ALT");
    }

    @Test
    public void checkPlatformAltitudePreferHAEoverMSL() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(1150.872));
        map.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(2432.1));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformAltitude));
        assertEquals(metadata.getValue(MetadataKey.PlatformAltitude), "1150m HAE ALT");
    }

    @Test
    public void checkPlatformAltitudeExtendedHAE() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.SensorEllipsoidHeightExtended,
                new SensorEllipsoidHeightExtended(1150.872));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformAltitude));
        // ST1909-23, ST1909-24, ST1909-26, ST1909-27, ST1909-28, ST 1909.1-96
        assertEquals(metadata.getValue(MetadataKey.PlatformAltitude), "1150m HAE ALT");
    }

    @Test
    public void checkPlatformAltitudeExtendedPreferHAEoverMSL() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(1150.872));
        map.put(
                UasDatalinkTag.SensorEllipsoidHeightExtended,
                new SensorEllipsoidHeightExtended(4350.7));
        map.put(UasDatalinkTag.SensorTrueAltitude, new SensorTrueAltitude(2432.1));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformAltitude));
        assertEquals(metadata.getValue(MetadataKey.PlatformAltitude), "4350m HAE ALT");
    }

    @Test
    public void checkPlatformInformationGroup() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "Testname"));
        map.put(
                UasDatalinkTag.PlatformCallSign,
                new UasDatalinkString(UasDatalinkString.PLATFORM_CALL_SIGN, "Romeo Sierra"));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-34.34211));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(150.87432));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformName));
        assertEquals(metadata.getValue(MetadataKey.PlatformName), "Testname Romeo Sierra");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformLatitude));
        assertEquals(metadata.getValue(MetadataKey.PlatformLatitude), "-34.3421\u00B0 LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.PlatformLongitude));
        assertEquals(metadata.getValue(MetadataKey.PlatformLongitude), "150.8743\u00B0 LON");
    }

    @Test
    public void checkTargetSlantRange() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SlantRange, new SlantRange(8763.4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.SlantRange));
        assertEquals(metadata.getValue(MetadataKey.SlantRange), "8763m SR");
    }

    @Test
    public void checkTargetWidthOnlyRounding() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetWidth, new TargetWidth(132.89));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "133m TW");
    }

    @Test
    public void checkTargetWidthExtendedOnlyRounding() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetWidthExtended, new TargetWidthExtended(1499999.9));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "1500000m TW");
    }

    @Test
    public void checkSlantRange() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SlantRange, new SlantRange(7654.32));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.SlantRange));
        // ST1909-54, ST1909-55, ST1909-56, ST1909-57
        assertEquals(metadata.getValue(MetadataKey.SlantRange), "7654m SR");
    }

    @Test
    public void checkSlantRangeMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.SlantRange));
        // ST1909-54, ST1909-55, ST1909-56, ST1909-57, ST1909.1-95
        assertEquals(metadata.getValue(MetadataKey.SlantRange), "N/A SR");
    }

    @Test
    public void checkTargetWidth() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetWidth, new TargetWidth(154.32));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        // ST1909-58, ST1909-59, ST1909-60
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "154m TW");
    }

    @Test
    public void checkTargetWidthExtended() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetWidthExtended, new TargetWidthExtended(113154.32));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        // ST1909-58, ST1909-59, ST1909-60
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "113154m TW");
    }

    @Test
    public void checkTargetWidthExtendedIsPreferredOverTargetWidth() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetWidth, new TargetWidth(154.32));
        map.put(UasDatalinkTag.TargetWidthExtended, new TargetWidthExtended(113154.32));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        // ST1909-58, ST1909-59, ST1909-60
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "113154m TW");
    }

    @Test
    public void checkTargetWidthMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetWidth));
        // ST1909-58, ST1909-59, ST1909-60, ST 1909.1-95
        assertEquals(metadata.getValue(MetadataKey.TargetWidth), "N/A TW");
    }

    @Test
    public void checkHorizontalFieldOfView() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorHorizontalFov, new HorizontalFov(1.34));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.HorizontalFOV));
        // ST1909-61, ST1909-62, ST1909-63
        assertEquals(metadata.getValue(MetadataKey.HorizontalFOV), "1.3400\u00B0 HFOV");
    }

    @Test
    public void checkHorizontalFieldOfViewMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.HorizontalFOV));
        // ST1909-61, ST1909-62, ST1909-63
        assertEquals(metadata.getValue(MetadataKey.HorizontalFOV), "N/A HFOV");
    }

    @Test
    public void checkVerticalFieldOfView() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(0.9865432));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.VerticalFOV));
        // ST1909-64, ST1909-65, ST1909-66
        assertEquals(metadata.getValue(MetadataKey.VerticalFOV), "0.9865\u00B0 VFOV");
    }

    @Test
    public void checkVerticalFieldOfView180() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(180.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.VerticalFOV));
        // ST1909-64, ST1909-65, ST1909-66
        assertEquals(metadata.getValue(MetadataKey.VerticalFOV), "180.0000\u00B0 VFOV");
    }

    @Test
    public void checkVerticalFieldOfViewMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.VerticalFOV));
        // ST1909-64, ST1909-65, ST1909-66
        assertEquals(metadata.getValue(MetadataKey.VerticalFOV), "N/A VFOV");
    }

    @Test
    public void checkMainSensorName() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "EO Nose"));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.MainSensorName));
        assertEquals(metadata.getValue(MetadataKey.MainSensorName), "EO Nose");
    }

    @Test
    public void checkMainSensorRelativeAzimuth() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(0.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.AzAngle));
        // ST1909-05 and ST1909-06
        assertEquals(metadata.getValue(MetadataKey.AzAngle), "REL AZ    0.0000\u00B0");
    }

    @Test
    public void checkMainSensorRelativeAzimuth360() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(360.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.AzAngle));
        // ST1909-05 and ST1909-06
        assertEquals(metadata.getValue(MetadataKey.AzAngle), "REL AZ  360.0000\u00B0");
    }

    @Test
    public void checkMainSensorRelativeAzimuthMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.AzAngle));
        // ST1909-05 and ST1909-06, plus email guidance from Rick C.
        assertEquals(metadata.getValue(MetadataKey.AzAngle), "REL AZ        N/A");
    }

    @Test
    public void checkMainSensorRelativeElevation() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorRelativeElevationAngle, new SensorRelativeElevation(0.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.ElAngle));
        // ST1909-07 and ST1909-08
        assertEquals(metadata.getValue(MetadataKey.ElAngle), "REL EL    0.0000\u00B0");
    }

    @Test
    public void checkMainSensorRelativeElevationNeg180() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorRelativeElevationAngle, new SensorRelativeElevation(-180.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.ElAngle));
        // ST1909-07 and ST1909-08
        assertEquals(metadata.getValue(MetadataKey.ElAngle), "REL EL -180.0000\u00B0");
    }

    @Test
    public void checkMainSensorRelativeElevationPos180() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.SensorRelativeElevationAngle, new SensorRelativeElevation(180.0));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.ElAngle));
        // ST1909-07 and ST1909-08
        assertEquals(metadata.getValue(MetadataKey.ElAngle), "REL EL  180.0000\u00B0");
    }

    @Test
    public void checkMainSensorRelativeElevationMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.ElAngle));
        // ST1909-07 and ST1909-08, plus email guidance from Rick C.
        assertEquals(metadata.getValue(MetadataKey.ElAngle), "REL EL        N/A");
    }

    @Test
    public void checkLaserOnFlag() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        List<FlagDataKey> keyList = new ArrayList<>();
        keyList.add(FlagDataKey.LaserRange);
        map.put(UasDatalinkTag.GenericFlagData01, new GenericFlagData01(keyList));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserSensorStatus));
        // ST1909-38
        assertEquals(metadata.getValue(MetadataKey.LaserSensorStatus), "Laser ON");
    }

    @Test
    public void checkLaserOffFlag() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        List<FlagDataKey> keyList = new ArrayList<>();
        keyList.add(FlagDataKey.AutoTrack); // Doesn't really matter - just not LaserRange.
        map.put(UasDatalinkTag.GenericFlagData01, new GenericFlagData01(keyList));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserSensorStatus));
        // ST1909-39
        assertEquals(metadata.getValue(MetadataKey.LaserSensorStatus), "Laser OFF");
    }

    @Test
    public void checkLaserOffNoFlags() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        List<FlagDataKey> keyList = new ArrayList<>();
        map.put(UasDatalinkTag.GenericFlagData01, new GenericFlagData01(keyList));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserSensorStatus));
        // ST1909-39
        assertEquals(metadata.getValue(MetadataKey.LaserSensorStatus), "Laser OFF");
    }

    @Test
    public void checkLaserStatusMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserSensorStatus));
        // ST1909-38
        assertEquals(metadata.getValue(MetadataKey.LaserSensorStatus), "N/A");
    }

    @Test
    public void checkLaserPRFcode() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.LaserPrfCode, new LaserPrfCode(111));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserPrfCode));
        // ST1909-40, ST1909-41, ST1909-42
        assertEquals(metadata.getValue(MetadataKey.LaserPrfCode), "Laser PRF Code    111");
    }

    @Test
    public void checkLaserPRFcode8888() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.LaserPrfCode, new LaserPrfCode(8888));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserPrfCode));
        // ST1909-40, ST1909-41, ST1909-42
        assertEquals(metadata.getValue(MetadataKey.LaserPrfCode), "Laser PRF Code   8888");
    }

    @Test
    public void checkLaserPRFcodeMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserPrfCode));
        // ST1909-40, ST1909-41, ST1909-42
        assertEquals(metadata.getValue(MetadataKey.LaserPrfCode), "Laser PRF Code    N/A");
    }

    @Test
    public void checkTargetLocationLatitudeLongitudeAltitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-34.34211));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(150.87432));
        map.put(UasDatalinkTag.TargetLocationElevation, new TargetLocationElevation(873.43));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-81, ST1909-82, ST1909-83
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 TL LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-84, ST1909-85, ST1909-86
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 TL LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-87, ST1909-88, ST1909-89, ST1909-90, ST1909-91
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m HAE TL EL");
    }

    @Test
    public void checkFrameCenterLatitudeLongitudeAltitudeHAE() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterHae, new FrameCenterHae(873.43));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.LaserSensorName));
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-78, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m HAE FC EL");
    }

    @Test
    public void checkFrameCenterLatitudeLongitudeAltitudeMSL() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(873.43));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-77, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m MSL FC EL");
    }

    @Test
    public void checkFrameCenterLatitudeLongitudeAltitudeHAEisPreferred() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterHae, new FrameCenterHae(873.43));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(674.43));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-78, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m HAE FC EL");
    }

    @Test
    public void checkFrameCenterLatitudeLongitudeNoAlt() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 2);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "N/A FC EL");
    }

    @Test
    public void checkTargetLocationIsPreferredOverFrameCenter() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-34.34211));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(150.87432));
        map.put(UasDatalinkTag.TargetLocationElevation, new TargetLocationElevation(873.43));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-32.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(151.87432));
        map.put(UasDatalinkTag.FrameCenterHae, new FrameCenterHae(803.43));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(674.43));
        // ST1909-67 and ST1909-68
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-81, ST1909-82, ST1909-83
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 TL LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-84, ST1909-85, ST1909-86
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 TL LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-87, ST1909-88, ST1909-89, ST1909-90, ST1909-91
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m HAE TL EL");
    }

    @Test
    public void checkFrameCenterIsPreferredOverIncompleteTargetLocation() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(873.43));
        // ST1909-67 and ST1909-68
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-77, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m MSL FC EL");
    }

    @Test
    public void checkFrameCenterIsPreferredOverIncompleteTargetLocationNoAlt() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-34.94211));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(873.43));
        // ST1909-67 and ST1909-68
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-77, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m MSL FC EL");
    }

    @Test
    public void checkFrameCenterIsPreferredOverIncompleteTargetLocationNoAltNoLon() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-34.94211));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(150.87432));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(873.43));
        // ST1909-67 and ST1909-68
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "-34.3421\u00B0 FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "150.8743\u00B0 FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-77, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "873m MSL FC EL");
    }

    @Test
    public void checkFrameCenterLatitudeIncomplete() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-34.34211));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 3);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLatitude));
        // ST1909-69, ST1909-70, ST1909-71
        assertEquals(metadata.getValue(MetadataKey.TargetLatitude), "N/A FC LAT");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetLongitude));
        // ST1909-72, ST1909-73, ST1909-74
        assertEquals(metadata.getValue(MetadataKey.TargetLongitude), "N/A FC LON");
        assertTrue(metadata.getItemKeys().contains(MetadataKey.TargetElevation));
        // ST1909-75, ST1909-76, ST1909-77, ST1909-79, ST1909-80
        assertEquals(metadata.getValue(MetadataKey.TargetElevation), "N/A FC EL");
    }

    @Test
    public void checkMetadataDateTime() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(2020, Month.JULY, 21, 10, 59, 24, 123000000)));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.MetadataTimestamp));
        // ST1909-46, ST1909-49, ST1909-50
        assertEquals(metadata.getValue(MetadataKey.MetadataTimestamp), "MT 2020-07-21T10:59:24.1Z");
    }

    @Test
    public void checkClassificationAndReleasabilityGroupBasic() {
        // Security markings for test purposes only, content is all unclassified
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> securityMetadataMap =
                new TreeMap<>();
        securityMetadataMap.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        securityMetadataMap.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.ISO3166_THREE_LETTER));
        securityMetadataMap.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AUS"));
        securityMetadataMap.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.ISO3166_THREE_LETTER));
        securityMetadataMap.put(
                SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("AUS;PNG"));
        securityMetadataMap.put(SecurityMetadataKey.Version, new ST0102Version(12));
        SecurityMetadataLocalSet securityMetadata =
                new SecurityMetadataLocalSet(securityMetadataMap);
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(securityMetadata));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine1));
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine2));
        assertEquals(
                metadata.getValue(MetadataKey.ClassificationAndReleasabilityLine1),
                "//AUS UNCLASSIFIED");
    }

    @Test
    public void checkClassificationAndReleasabilityGroupMissing() {
        // Security markings for test purposes only, content is all unclassified
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> securityMetadataMap =
                new TreeMap<>();
        securityMetadataMap.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AUS"));
        securityMetadataMap.put(SecurityMetadataKey.Version, new ST0102Version(12));
        SecurityMetadataLocalSet securityMetadata =
                new SecurityMetadataLocalSet(securityMetadataMap);
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(securityMetadata));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine1));
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine2));
    }

    @Test
    public void checkClassificationAndReleasabilityGroupMissingClassification() {
        // Security markings for test purposes only, content is all unclassified
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> securityMetadataMap =
                new TreeMap<>();
        securityMetadataMap.put(SecurityMetadataKey.Version, new ST0102Version(12));
        SecurityMetadataLocalSet securityMetadata =
                new SecurityMetadataLocalSet(securityMetadataMap);
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(securityMetadata));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine1));
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine2));
    }

    @Test
    public void checkClassificationAndReleasabilityGroupAll() {
        // Security markings for test purposes only, content is all unclassified
        Builder st0102builder =
                new Builder(Classification.UNCLASSIFIED)
                        .ccMethod(CountryCodingMethod.ISO3166_THREE_LETTER)
                        .classifyingCountry("//AUS")
                        .ocMethod(CountryCodingMethod.ISO3166_THREE_LETTER)
                        .objectCountryCodes("AUS;PNG")
                        .sciShiInfo("BUTTER POPCORN/WIBBLE//")
                        .caveats("FOUO")
                        .releasingInstructions("REL TO ANYBODY")
                        .version(12);
        SecurityMetadataLocalSet securityMetadata = st0102builder.build();
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(securityMetadata));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine1));
        assertFalse(
                metadata.getItemKeys().contains(MetadataKey.ClassificationAndReleasabilityLine2));
        assertEquals(
                metadata.getValue(MetadataKey.ClassificationAndReleasabilityLine1),
                "//AUS UNCLASSIFIED FOUO BUTTER POPCORN/WIBBLE// REL TO ANYBODY");
    }

    @Test
    public void checkNorthGroupAngle0() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint1,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint2,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint3,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint4,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint1,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_1));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint2,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_2));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint3,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_3));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint4,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "0.0");
    }

    @Test
    public void checkNorthGroupAngle360Outwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint1,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint2,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint3,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint4,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint1,
                new CornerOffset(-0.011, CornerOffset.CORNER_LON_1));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint2,
                new CornerOffset(0.011, CornerOffset.CORNER_LON_2));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint3,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_3));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint4,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "360.0");
    }

    @Test
    public void checkNorthGroupAngle360Inwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint1,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint2,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint3,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint4,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint1,
                new CornerOffset(-0.011, CornerOffset.CORNER_LON_1));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint2,
                new CornerOffset(0.011, CornerOffset.CORNER_LON_2));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint3,
                new CornerOffset(0.015, CornerOffset.CORNER_LON_3));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint4,
                new CornerOffset(-0.015, CornerOffset.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "360.0");
    }

    @Test
    public void checkNorthGroupAngle180() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint1,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint2,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint3,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint4,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint1,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_1));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint2,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_2));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint3,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_3));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint4,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "180.0");
    }

    @Test
    public void checkNorthGroupAngle180Outwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint1,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint2,
                new CornerOffset(-0.01, CornerOffset.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint3,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.OffsetCornerLatitudePoint4,
                new CornerOffset(0.01, CornerOffset.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint1,
                new CornerOffset(0.01, CornerOffset.CORNER_LON_1));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint2,
                new CornerOffset(-0.01, CornerOffset.CORNER_LON_2));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint3,
                new CornerOffset(-0.011, CornerOffset.CORNER_LON_3));
        map.put(
                UasDatalinkTag.OffsetCornerLongitudePoint4,
                new CornerOffset(0.011, CornerOffset.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "180.0");
    }

    @Test
    public void checkNorthGroupAngle0FullCorner() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "0.0");
    }

    @Test
    public void checkNorthGroupAngle0FullCornerSlant() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(0.02, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle).substring(0, 5), "13.28");
    }

    @Test
    public void checkNorthGroupAngle0FullCornerOutwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(-0.011, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(0.011, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "360.0");
    }

    @Test
    public void checkNorthGroupAngle0FullCornerInwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(-0.011, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(0.011, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(0.015, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(-0.015, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "360.0");
    }

    @Test
    public void checkNorthGroupAngle180FullCorner() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "180.0");
    }

    @Test
    public void checkNorthGroupAngle180FullCornerOutwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(0.01, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(-0.01, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(-0.011, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(0.011, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "180.0");
    }

    @Test
    public void checkNorthGroupAngle180FullCornerInwards() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(0.014, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(-0.014, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(-0.010, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(0.010, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle), "180.0");
    }

    @Test
    public void checkNorthGroupAngle180FullCornerRightOut() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        map.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(-0.01, FullCornerLatitude.CORNER_LAT_1));
        map.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(-0.013, FullCornerLatitude.CORNER_LAT_2));
        map.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(0.017, FullCornerLatitude.CORNER_LAT_3));
        map.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(0.01, FullCornerLatitude.CORNER_LAT_4));
        map.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(0.010, FullCornerLongitude.CORNER_LON_1));
        map.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(-0.015, FullCornerLongitude.CORNER_LON_2));
        map.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(-0.010, FullCornerLongitude.CORNER_LON_3));
        map.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(0.010, FullCornerLongitude.CORNER_LON_4));
        UasDatalinkMessage st0601message = new UasDatalinkMessage(map);
        MetadataItems metadata = new MetadataItems();
        ST0601Converter.convertST0601(st0601message, metadata);
        assertTrue(metadata.getItemKeys().size() >= 1);
        assertTrue(metadata.getItemKeys().contains(MetadataKey.NorthAngle));
        assertEquals(metadata.getValue(MetadataKey.NorthAngle).substring(0, 6), "175.26");
    }

    @Test
    public void checkClassInstantiation() {
        ST0601Converter converter = new ST0601Converter();
        assertNotNull(converter);
    }
}
