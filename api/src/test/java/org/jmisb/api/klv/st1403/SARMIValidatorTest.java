package org.jmisb.api.klv.st1403;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.jmisb.api.klv.st0102.CcmDate;
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
import org.jmisb.api.klv.st0601.FrameCenterHae;
import org.jmisb.api.klv.st0601.FrameCenterLatitude;
import org.jmisb.api.klv.st0601.FrameCenterLongitude;
import org.jmisb.api.klv.st0601.FullCornerLatitude;
import org.jmisb.api.klv.st0601.FullCornerLongitude;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.MiisCoreIdentifier;
import org.jmisb.api.klv.st0601.NestedSARMILocalSet;
import org.jmisb.api.klv.st0601.NestedSecurityMetadata;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.ST0601Version;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeight;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeightExtended;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SlantRange;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1206.ApertureDuration;
import org.jmisb.api.klv.st1206.CrossRangeImagePlanePixelSize;
import org.jmisb.api.klv.st1206.DocumentVersion;
import org.jmisb.api.klv.st1206.GrazingAngle;
import org.jmisb.api.klv.st1206.GroundPlaneSquintAngle;
import org.jmisb.api.klv.st1206.ISARMIMetadataValue;
import org.jmisb.api.klv.st1206.ImageColumns;
import org.jmisb.api.klv.st1206.ImagePlane;
import org.jmisb.api.klv.st1206.ImageRows;
import org.jmisb.api.klv.st1206.LookDirection;
import org.jmisb.api.klv.st1206.RangeDirectionAngleRelativeToTrueNorth;
import org.jmisb.api.klv.st1206.RangeImagePlanePixelSize;
import org.jmisb.api.klv.st1206.RangeLayoverAngleRelativeToTrueNorth;
import org.jmisb.api.klv.st1206.RangeResolution;
import org.jmisb.api.klv.st1206.ReferenceFrameGrazingAngle;
import org.jmisb.api.klv.st1206.ReferenceFrameGroundPlaneSquintAngle;
import org.jmisb.api.klv.st1206.ReferenceFramePrecisionTimeStamp;
import org.jmisb.api.klv.st1206.ReferenceFrameRangeDirectionAngleRelativeToTrueNorth;
import org.jmisb.api.klv.st1206.ReferenceFrameRangeLayoverAngleRelativeToTrueNorth;
import org.jmisb.api.klv.st1206.SARMILocalSet;
import org.jmisb.api.klv.st1206.SARMIMetadataKey;
import org.jmisb.api.klv.st1206.TrueNorthDirectionRelativeToTopImageEdge;
import org.testng.annotations.Test;

/** Unit tests for SARMIValidator implementation. */
public class SARMIValidatorTest {

    @Test
    public void checkValidLocalSetSensorEllpsoidHeight75() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertTrue(results.isConformant());
        List<ValidationResult> failures = results.getNonConformances();
        assertEquals(failures.size(), 0);
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkValidLocalSetSensorEllpsoidHeight75WithExtraSecurityItem() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValidExtra()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertTrue(results.isConformant());
        List<ValidationResult> failures = results.getNonConformances();
        assertEquals(failures.size(), 0);
    }

    @Test
    public void checkValidLocalSetSensorEllpsoidHeight75WithExtraSARMIItem() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValidExtra()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertTrue(results.isConformant());
        List<ValidationResult> failures = results.getNonConformances();
        assertEquals(failures.size(), 0);
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetSensorEllpsoidHeight75WrongType() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(
                UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeightExtended(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        List<ValidationResult> out = results.getNonConformances();
        assertEquals(out.size(), 1);
        ValidationResult failure = out.get(0);
        assertEquals(failure.getValidity(), Validity.DoesNotConform);
        assertEquals(failure.getTraceability(), "ST 1403-03");
        assertEquals(
                failure.getDescription(),
                "Sensor Ellipsoid Height was not of the correct type. Found class org.jmisb.api.klv.st0601.SensorEllipsoidHeightExtended");
    }

    @Test
    public void checkInvalidLocalSetMissing0601() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.remove(UasDatalinkTag.CornerLatPt1);
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetWrongType0601() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.remove(UasDatalinkTag.CornerLatPt1);
        values.put(UasDatalinkTag.CornerLatPt1, new SlantRange(30.3));
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetNullSecurity() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(UasDatalinkTag.SecurityLocalMetadataSet, null);
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetNullSARMI() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(UasDatalinkTag.SarMotionImageryMetadata, null);
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetMissing0102() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        final NestedSecurityMetadata nestedSecurityMetadata =
                new NestedSecurityMetadata(makeSecurityLocalSetInvalid());
        values.put(UasDatalinkTag.SecurityLocalMetadataSet, nestedSecurityMetadata);
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetWrongType0102() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        final NestedSecurityMetadata nestedSecurityMetadata =
                new NestedSecurityMetadata(makeSecurityLocalSetWrongType());
        values.put(UasDatalinkTag.SecurityLocalMetadataSet, nestedSecurityMetadata);
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetMissing1206() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        final NestedSecurityMetadata nestedSecurityMetadata =
                new NestedSecurityMetadata(makeSecurityLocalSetValid());
        values.put(UasDatalinkTag.SecurityLocalMetadataSet, nestedSecurityMetadata);
        final NestedSARMILocalSet nestedSARMILocalSet =
                new NestedSARMILocalSet(makeSARMILocalSetInvalid());
        values.put(UasDatalinkTag.SarMotionImageryMetadata, nestedSARMILocalSet);
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetWrongType1206() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        final NestedSecurityMetadata nestedSecurityMetadata =
                new NestedSecurityMetadata(makeSecurityLocalSetValid());
        values.put(UasDatalinkTag.SecurityLocalMetadataSet, nestedSecurityMetadata);
        final NestedSARMILocalSet nestedSARMILocalSet =
                new NestedSARMILocalSet(makeSARMILocalSetWrongType());
        values.put(UasDatalinkTag.SarMotionImageryMetadata, nestedSARMILocalSet);
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetSensorEllpsoidHeight75NullValue() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, null);
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkValidLocalSetSensorEllpsoidHeightExtended104() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(
                UasDatalinkTag.SensorEllipsoidHeightExtended,
                new SensorEllipsoidHeightExtended(40000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertTrue(results.isConformant());
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertFalse(ccdResults.isConformant());
    }

    @Test
    public void checkInvalidLocalSetSensorEllpsoidHeightExtended104WrongType() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeightExtended, new SensorEllipsoidHeight(2000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
        List<ValidationResult> out = results.getNonConformances();
        assertEquals(out.size(), 1);
        ValidationResult failure = out.get(0);
        assertEquals(failure.getValidity(), Validity.DoesNotConform);
        assertEquals(failure.getTraceability(), "ST 1403-03");
        assertEquals(
                failure.getDescription(),
                "Sensor Ellipsoid Height Extended was not of the correct type. Found class org.jmisb.api.klv.st0601.SensorEllipsoidHeight");
    }

    @Test
    public void checkInvalidLocalSetSensorEllpsoidHeightExtended104NullValue() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeightExtended, null);
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
    }

    @Test
    public void checkLocalSetNoEllipsoidHeight() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
    }

    @Test
    public void checkLocalSetBothEllipsoidHeight() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SensorEllipsoidHeightExtended,
                new SensorEllipsoidHeightExtended(40000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMILocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertFalse(results.isConformant());
    }

    @Test
    public void checkValidLocalSetCCD() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = makeBaseST0601Values();
        values.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(10000.0));
        values.put(
                UasDatalinkTag.SecurityLocalMetadataSet,
                new NestedSecurityMetadata(makeSecurityLocalSetValid()));
        values.put(
                UasDatalinkTag.SarMotionImageryMetadata,
                new NestedSARMILocalSet(makeSARMICCDLocalSetValid()));
        UasDatalinkMessage message = new UasDatalinkMessage(values);
        ValidationResults results = SARMIValidator.validateSARImageryMetadata(message);
        assertTrue(results.isConformant());
        List<ValidationResult> failures = results.getNonConformances();
        assertEquals(failures.size(), 0);
        ValidationResults ccdResults =
                SARMIValidator.validateSARCoherentChangeProductMetadata(message);
        assertTrue(ccdResults.isConformant());
    }

    private SortedMap<UasDatalinkTag, IUasDatalinkValue> makeBaseST0601Values() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> values = new TreeMap<>();
        values.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(1000L * System.currentTimeMillis()));
        values.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "mission 1"));
        values.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "test platform"));
        values.put(
                (UasDatalinkTag.ImageSourceSensor),
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "test sensor"));
        values.put(
                (UasDatalinkTag.ImageCoordinateSystem),
                new UasDatalinkString(UasDatalinkString.IMAGE_COORDINATE_SYSTEM, "Geodetic WGS84"));
        values.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-35.35355));
        values.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(149.08939));
        values.put(UasDatalinkTag.SlantRange, new SlantRange(2000.0));
        values.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-35.35305));
        values.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(149.08939));
        values.put(
                UasDatalinkTag.CornerLatPt1,
                new FullCornerLatitude(-35.35300, FullCornerLatitude.CORNER_LAT_1));
        values.put(
                UasDatalinkTag.CornerLonPt1,
                new FullCornerLongitude(149.08930, FullCornerLongitude.CORNER_LON_1));
        values.put(
                UasDatalinkTag.CornerLatPt2,
                new FullCornerLatitude(-35.35300, FullCornerLatitude.CORNER_LAT_2));
        values.put(
                UasDatalinkTag.CornerLonPt2,
                new FullCornerLongitude(149.08940, FullCornerLongitude.CORNER_LON_2));
        values.put(
                UasDatalinkTag.CornerLatPt3,
                new FullCornerLatitude(-35.35320, FullCornerLatitude.CORNER_LAT_3));
        values.put(
                UasDatalinkTag.CornerLonPt3,
                new FullCornerLongitude(149.08940, FullCornerLongitude.CORNER_LON_3));
        values.put(
                UasDatalinkTag.CornerLatPt4,
                new FullCornerLatitude(-35.35320, FullCornerLatitude.CORNER_LAT_4));
        values.put(
                UasDatalinkTag.CornerLonPt4,
                new FullCornerLongitude(149.08930, FullCornerLongitude.CORNER_LON_4));
        values.put(UasDatalinkTag.UasLdsVersionNumber, new ST0601Version((short) 16));
        values.put(UasDatalinkTag.FrameCenterHae, new FrameCenterHae(12.0));
        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);
        values.put(UasDatalinkTag.MiisCoreIdentifier, new MiisCoreIdentifier(coreIdentifier));

        return values;
    }

    private SecurityMetadataLocalSet makeSecurityLocalSetValid() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values =
                makeSecurityLocalSetValues();
        return new SecurityMetadataLocalSet(values);
    }

    private SecurityMetadataLocalSet makeSecurityLocalSetValidExtra() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values =
                makeSecurityLocalSetValues();
        values.put(
                SecurityMetadataKey.CcCodingMethodVersionDate,
                new CcmDate(LocalDate.of(2020, 10, 16)));
        return new SecurityMetadataLocalSet(values);
    }

    private SecurityMetadataLocalSet makeSecurityLocalSetInvalid() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values =
                makeSecurityLocalSetValues();
        values.remove(SecurityMetadataKey.Version);
        return new SecurityMetadataLocalSet(values);
    }

    private SecurityMetadataLocalSet makeSecurityLocalSetWrongType() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values =
                makeSecurityLocalSetValues();
        values.remove(SecurityMetadataKey.Version);
        values.put(
                SecurityMetadataKey.Version, new CcMethod(CountryCodingMethod.GENC_THREE_LETTER));
        return new SecurityMetadataLocalSet(values);
    }

    private SortedMap<SecurityMetadataKey, ISecurityMetadataValue> makeSecurityLocalSetValues() {
        SortedMap<SecurityMetadataKey, ISecurityMetadataValue> values = new TreeMap<>();
        values.put(
                SecurityMetadataKey.SecurityClassification,
                new ClassificationLocal(Classification.UNCLASSIFIED));
        values.put(
                SecurityMetadataKey.CcCodingMethod,
                new CcMethod(CountryCodingMethod.ISO3166_TWO_LETTER));
        values.put(
                SecurityMetadataKey.ClassifyingCountry,
                new SecurityMetadataString(SecurityMetadataString.CLASSIFYING_COUNTRY, "//AU"));
        values.put(
                SecurityMetadataKey.OcCodingMethod,
                new OcMethod(CountryCodingMethod.GENC_TWO_LETTER));
        values.put(SecurityMetadataKey.ObjectCountryCodes, new ObjectCountryCodeString("US"));
        values.put(SecurityMetadataKey.Version, new ST0102Version(11));
        return values;
    }

    private SARMILocalSet makeSARMILocalSetValid() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = makeSARMILocalSetValues();
        return new SARMILocalSet(values);
    }

    private SARMILocalSet makeSARMICCDLocalSetValid() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = makeSARMILocalSetValues();
        values.put(
                SARMIMetadataKey.ReferenceFramePrecisionTimeStamp,
                new ReferenceFramePrecisionTimeStamp(LocalDateTime.now()));
        values.put(
                SARMIMetadataKey.ReferenceFrameGrazingAngle, new ReferenceFrameGrazingAngle(3.2));
        values.put(
                SARMIMetadataKey.ReferenceFrameGroundPlaneSquintAngle,
                new ReferenceFrameGroundPlaneSquintAngle(4.5));
        values.put(
                SARMIMetadataKey.ReferenceFrameRangeDirectionAngleRelativeToTrueNorth,
                new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(15.0));
        values.put(
                SARMIMetadataKey.ReferenceFrameRangeLayoverAngleRelativeToTrueNorth,
                new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(35.3));
        return new SARMILocalSet(values);
    }

    private SARMILocalSet makeSARMILocalSetValidExtra() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = makeSARMILocalSetValues();
        values.put(SARMIMetadataKey.ApertureDuration, new ApertureDuration(1000L));
        return new SARMILocalSet(values);
    }

    private SARMILocalSet makeSARMILocalSetInvalid() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = makeSARMILocalSetValues();
        values.remove(SARMIMetadataKey.DocumentVersion);
        return new SARMILocalSet(values);
    }

    private SARMILocalSet makeSARMILocalSetWrongType() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = makeSARMILocalSetValues();
        values.remove(SARMIMetadataKey.DocumentVersion);
        values.remove(SARMIMetadataKey.DocumentVersion, new LookDirection((byte) 1));
        return new SARMILocalSet(values);
    }

    private SortedMap<SARMIMetadataKey, ISARMIMetadataValue> makeSARMILocalSetValues() {
        SortedMap<SARMIMetadataKey, ISARMIMetadataValue> values = new TreeMap<>();
        values.put(SARMIMetadataKey.GrazingAngle, new GrazingAngle(4.3));
        values.put(SARMIMetadataKey.GroundPlaneSquintAngle, new GroundPlaneSquintAngle(5.2));
        values.put(SARMIMetadataKey.LookDirection, new LookDirection((byte) 0));
        values.put(SARMIMetadataKey.ImagePlane, new ImagePlane((byte) 0));
        values.put(SARMIMetadataKey.RangeResolution, new RangeResolution(12.0));
        values.put(SARMIMetadataKey.RangeImagePlanePixelSize, new RangeImagePlanePixelSize(20.0));
        values.put(
                SARMIMetadataKey.CrossRangeImagePlanePixelSize,
                new CrossRangeImagePlanePixelSize(15.0));
        values.put(SARMIMetadataKey.ImageRows, new ImageRows(800));
        values.put(SARMIMetadataKey.ImageColumns, new ImageColumns(750));
        values.put(
                SARMIMetadataKey.RangeDirectionAngleRelativeToTrueNorth,
                new RangeDirectionAngleRelativeToTrueNorth(15.0));
        values.put(
                SARMIMetadataKey.TrueNorthDirectionRelativeToTopImageEdge,
                new TrueNorthDirectionRelativeToTopImageEdge(25.0));
        values.put(
                SARMIMetadataKey.RangeLayoverAngleRelativeToTrueNorth,
                new RangeLayoverAngleRelativeToTrueNorth(53.2));
        values.put(SARMIMetadataKey.DocumentVersion, new DocumentVersion(0));
        return values;
    }
}
