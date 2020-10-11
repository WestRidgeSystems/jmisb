package org.jmisb.api.klv.st1403;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jmisb.api.klv.IKlvValue;
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
import org.jmisb.api.klv.st1206.CrossRangeImagePlanePixelSize;
import org.jmisb.api.klv.st1206.DocumentVersion;
import org.jmisb.api.klv.st1206.GrazingAngle;
import org.jmisb.api.klv.st1206.GroundPlaneSquintAngle;
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
import org.jmisb.api.klv.st1206.SARMILocalSet;
import org.jmisb.api.klv.st1206.SARMIMetadataKey;
import org.jmisb.api.klv.st1206.TrueNorthDirectionRelativeToTopImageEdge;

/**
 * SAR Motion Imagery Validator.
 *
 * <p>This class provides validation for Synthetic Aperture Radar Motion Imagery (SARMI) metadata
 * against the requirements of MISB ST 1403 "SARMI Threshold Metadata Sets".
 *
 * <p>ST 1403 describes two conformance targets - one that is for sequential display of SAR imagery,
 * and a second that is for sequential display of SAR coherent change products. This class provides
 * validation against those conformance targets.
 */
public class SARMIValidator {

    private SARMIValidator() {}

    /** Required Metadata Items from ST0601, from ST 1403.2 Table 1. */
    private static final Map<UasDatalinkTag, Class> REQUIRED_ST0601_ITEMS =
            new TreeMap<UasDatalinkTag, Class>() {
                {
                    /* No Checksum - that will get added / removed on serialisation */
                    put(UasDatalinkTag.PrecisionTimeStamp, PrecisionTimeStamp.class);
                    put(UasDatalinkTag.MissionId, UasDatalinkString.class);
                    put(UasDatalinkTag.PlatformDesignation, UasDatalinkString.class);
                    put(UasDatalinkTag.ImageSourceSensor, UasDatalinkString.class);
                    put(UasDatalinkTag.SensorLatitude, SensorLatitude.class);
                    put(UasDatalinkTag.SensorLongitude, SensorLongitude.class);
                    put(UasDatalinkTag.SlantRange, SlantRange.class);
                    put(UasDatalinkTag.FrameCenterLatitude, FrameCenterLatitude.class);
                    put(UasDatalinkTag.FrameCenterLongitude, FrameCenterLongitude.class);
                    put(UasDatalinkTag.CornerLatPt1, FullCornerLatitude.class);
                    put(UasDatalinkTag.CornerLonPt1, FullCornerLongitude.class);
                    put(UasDatalinkTag.CornerLatPt2, FullCornerLatitude.class);
                    put(UasDatalinkTag.CornerLonPt2, FullCornerLongitude.class);
                    put(UasDatalinkTag.CornerLatPt3, FullCornerLatitude.class);
                    put(UasDatalinkTag.CornerLonPt3, FullCornerLongitude.class);
                    put(UasDatalinkTag.CornerLatPt4, FullCornerLatitude.class);
                    put(UasDatalinkTag.CornerLonPt4, FullCornerLongitude.class);
                    put(UasDatalinkTag.SecurityLocalMetadataSet, NestedSecurityMetadata.class);
                    put(UasDatalinkTag.UasLdsVersionNumber, ST0601Version.class);
                    /* Ellipsoid Height | Ellipsoid Height Extended is special case */
                    put(UasDatalinkTag.FrameCenterHae, FrameCenterHae.class);
                    put(UasDatalinkTag.MiisCoreIdentifier, MiisCoreIdentifier.class);
                    put(UasDatalinkTag.SarMotionImageryMetadata, NestedSARMILocalSet.class);
                }
            };

    /** Required Metadata Items from ST0102, from ST 1403.2 Table 1. */
    private static final Map<SecurityMetadataKey, Class> REQUIRED_ST0102_ITEMS =
            new TreeMap<SecurityMetadataKey, Class>() {
                {
                    put(SecurityMetadataKey.SecurityClassification, ClassificationLocal.class);
                    put(SecurityMetadataKey.CcCodingMethod, CcMethod.class);
                    put(SecurityMetadataKey.ClassifyingCountry, SecurityMetadataString.class);
                    // TODO: what to put in for this?
                    // put(SecurityMetadataKey.SciShiInfo, SecurityMetadataString.class);
                    // put(SecurityMetadataKey.Caveats, SecurityMetadataString.class);
                    // put(SecurityMetadataKey.ReleasingInstructions, SecurityMetadataString.class);
                    put(SecurityMetadataKey.OcCodingMethod, OcMethod.class);
                    put(SecurityMetadataKey.ObjectCountryCodes, ObjectCountryCodeString.class);
                    put(SecurityMetadataKey.Version, ST0102Version.class);
                }
            };

    /** Required Metadata Items from ST1206, from ST 1403.2 Table 1. */
    private static final Map<SARMIMetadataKey, Class> REQUIRED_ST1206_ITEMS =
            new TreeMap<SARMIMetadataKey, Class>() {
                {
                    put(SARMIMetadataKey.GrazingAngle, GrazingAngle.class);
                    put(SARMIMetadataKey.GroundPlaneSquintAngle, GroundPlaneSquintAngle.class);
                    put(SARMIMetadataKey.LookDirection, LookDirection.class);
                    put(SARMIMetadataKey.ImagePlane, ImagePlane.class);
                    put(SARMIMetadataKey.RangeResolution, RangeResolution.class);
                    put(SARMIMetadataKey.RangeImagePlanePixelSize, RangeImagePlanePixelSize.class);
                    put(
                            SARMIMetadataKey.CrossRangeImagePlanePixelSize,
                            CrossRangeImagePlanePixelSize.class);
                    put(SARMIMetadataKey.ImageRows, ImageRows.class);
                    put(SARMIMetadataKey.ImageColumns, ImageColumns.class);
                    put(
                            SARMIMetadataKey.RangeDirectionAngleRelativeToTrueNorth,
                            RangeDirectionAngleRelativeToTrueNorth.class);
                    put(
                            SARMIMetadataKey.TrueNorthDirectionRelativeToTopImageEdge,
                            TrueNorthDirectionRelativeToTopImageEdge.class);
                    put(
                            SARMIMetadataKey.RangeLayoverAngleRelativeToTrueNorth,
                            RangeLayoverAngleRelativeToTrueNorth.class);
                    put(SARMIMetadataKey.DocumentVersion, DocumentVersion.class);
                }
            };

    /** Additional Metadata Items from ST1206, from ST 1403.2 Table 2. */
    private static final Map<SARMIMetadataKey, Class> ADDITIONAL_ST1206_ITEMS =
            new TreeMap<SARMIMetadataKey, Class>() {
                {
                    put(
                            SARMIMetadataKey.ReferenceFramePrecisionTimeStamp,
                            ReferenceFramePrecisionTimeStamp.class);
                    put(
                            SARMIMetadataKey.ReferenceFrameGrazingAngle,
                            ReferenceFrameGrazingAngle.class);
                    put(
                            SARMIMetadataKey.ReferenceFrameGroundPlaneSquintAngle,
                            ReferenceFrameGroundPlaneSquintAngle.class);
                }
            };

    /**
     * Validate that the given local set meets the requirements of ST 1403.2 for SAR Motion Imagery.
     *
     * <p>This only checks the requirements for the sequential display of SAR Imagery as SARMI data.
     *
     * @param localSet the local set to check
     * @return ValidationResult structure containing the validation results.
     */
    public static ValidationResults validateSARImageryMetadata(UasDatalinkMessage localSet) {
        ValidationResults result = new ValidationResults();
        result.addResults(validateCoreST0601Items(localSet));

        NestedSecurityMetadata nestedSecurityMetadata =
                (NestedSecurityMetadata) localSet.getField(UasDatalinkTag.SecurityLocalMetadataSet);
        if (nestedSecurityMetadata != null) {
            SecurityMetadataLocalSet securityLocalSet = nestedSecurityMetadata.getLocalSet();
            result.addResults(validateSecurityItems(securityLocalSet));
        }

        NestedSARMILocalSet nestedSARMILocalSet =
                (NestedSARMILocalSet) localSet.getField(UasDatalinkTag.SarMotionImageryMetadata);
        if (nestedSARMILocalSet != null) {
            SARMILocalSet sarmiLocalSet = nestedSARMILocalSet.getSARMI();
            result.addResults(validateSARMIItems(sarmiLocalSet));
        }
        return result;
    }

    /**
     * Validate that the given local set meets the requirements of ST 1403.2 for SAR Coherent Change
     * Products.
     *
     * <p>This checks the requirements for the sequential display of SAR Coherent Change Products as
     * SARMI data. This is a strict superset of the requirements for sequential display of SAR
     * Imagery.
     *
     * @param localSet the local set to check
     * @return ValidationResult structure containing the validation results.
     */
    public static ValidationResults validateSARCoherentChangeProductMetadata(
            UasDatalinkMessage localSet) {
        ValidationResults result = validateSARImageryMetadata(localSet);
        NestedSARMILocalSet nestedSARMILocalSet =
                (NestedSARMILocalSet) localSet.getField(UasDatalinkTag.SarMotionImageryMetadata);
        if (nestedSARMILocalSet != null) {
            SARMILocalSet sarmiLocalSet = nestedSARMILocalSet.getSARMI();
            result.addResults(validateAdditionalSARMIItems(sarmiLocalSet));
        }
        return result;
    }

    private static List<ValidationResult> validateCoreST0601Items(UasDatalinkMessage localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (UasDatalinkTag tag : REQUIRED_ST0601_ITEMS.keySet()) {
            ValidationResult result = validateHasValidItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        for (UasDatalinkTag tag : localSet.getIdentifiers()) {
            IUasDatalinkValue value = localSet.getField(tag);
            if (value == null) {
                results.add(getInvalidResultNullValue(tag.toString()));
            } else if (REQUIRED_ST0601_ITEMS.containsKey(tag)) {
                Class expectedClass = REQUIRED_ST0601_ITEMS.get(tag);
                results.add(checkValueIsOfCorrectType(value, expectedClass, tag.toString()));
            }
        }
        results.addAll(validateSensorEllipsoidHeightCombination(localSet));
        return results;
    }

    private static ValidationResult checkValueIsOfCorrectType(
            IKlvValue value, Class expectedClass, String tagLabel) {
        if (value.getClass() == expectedClass) {
            ValidationResult typeResult = new ValidationResult(Validity.Conforms);
            typeResult.setTraceability("ST 1403-03");
            typeResult.setDescription(
                    String.format(
                            "%s was expected type. Found %s",
                            tagLabel, value.getClass().toString()));
            return typeResult;
        } else {
            ValidationResult typeResult = new ValidationResult(Validity.DoesNotConform);
            typeResult.setTraceability("ST 1403-03");
            typeResult.setDescription(
                    String.format(
                            "%s was not of the correct type. Found %s",
                            tagLabel, value.getClass().toString()));
            return typeResult;
        }
    }

    private static List<ValidationResult> validateSensorEllipsoidHeightCombination(
            UasDatalinkMessage localSet) {
        List<ValidationResult> validationResults = new ArrayList<>();
        boolean hasSensorEllipsoidHeight =
                localSet.getTags().contains(UasDatalinkTag.SensorEllipsoidHeight);
        boolean hasSensorEllipsoidHeightExtended =
                localSet.getTags().contains(UasDatalinkTag.SensorEllipsoidHeightExtended);
        // Needs to be XOR
        if (hasSensorEllipsoidHeight && hasSensorEllipsoidHeightExtended) {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Only one of Sensor Ellipsoid Height (75) or Sensor Ellipsoid Height Extended (104) is allowed, but both were found.");
            validationResults.add(result);
        } else if (hasSensorEllipsoidHeight) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Required Ellipsoid Height (75) (but not Sensor Ellipsoid Height Extended) was found.");
            validationResults.add(result);
            IUasDatalinkValue v = localSet.getField(UasDatalinkTag.SensorEllipsoidHeight);
            if (v == null) {
                validationResults.add(getInvalidResultNullValue("Ellipsoid Height"));
            } else {
                if (v instanceof SensorEllipsoidHeight) {
                    ValidationResult typeResult = new ValidationResult(Validity.Conforms);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription("Sensor Ellipsoid Height was of the correct type.");
                    validationResults.add(typeResult);
                } else {
                    ValidationResult typeResult = new ValidationResult(Validity.DoesNotConform);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            String.format(
                                    "Sensor Ellipsoid Height was not of the correct type. Found %s",
                                    v.getClass().toString()));
                    validationResults.add(typeResult);
                }
            }
        } else if (hasSensorEllipsoidHeightExtended) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 1403-04");
            result.setDescription(
                    "Required Ellipsoid Height Extended (104) (but not Sensor Ellipsoid Height) was found.");
            validationResults.add(result);
            IUasDatalinkValue v = localSet.getField(UasDatalinkTag.SensorEllipsoidHeightExtended);
            if (v == null) {
                validationResults.add(getInvalidResultNullValue("Ellipsoid Height Extended"));
            } else {
                if (v instanceof SensorEllipsoidHeightExtended) {
                    ValidationResult typeResult = new ValidationResult(Validity.Conforms);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            "Sensor Ellipsoid Height Extended was of the correct type.");
                    validationResults.add(typeResult);
                } else {
                    ValidationResult typeResult = new ValidationResult(Validity.DoesNotConform);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            String.format(
                                    "Sensor Ellipsoid Height Extended was not of the correct type. Found %s",
                                    v.getClass().toString()));
                    validationResults.add(typeResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Neither Sensor Ellipsoid Height (75) or Sensor Ellipsoid Height Extended (104) was found.");
            validationResults.add(result);
        }
        return validationResults;
    }

    private static ValidationResult getInvalidResultNullValue(String tagLabel) {
        ValidationResult r = new ValidationResult(Validity.DoesNotConform);
        r.setTraceability("ST 1403.2-07");
        r.setDescription(String.format("Required %s tag was found, but has null value.", tagLabel));
        return r;
    }

    private static List<ValidationResult> validateSecurityItems(SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (SecurityMetadataKey tag : REQUIRED_ST0102_ITEMS.keySet()) {
            ValidationResult result = validateHasSecurityItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        for (SecurityMetadataKey tag : localSet.getIdentifiers()) {
            ISecurityMetadataValue value = localSet.getField(tag);
            if (REQUIRED_ST0102_ITEMS.containsKey(tag)) {
                Class expectedClass = REQUIRED_ST0102_ITEMS.get(tag);
                results.add(checkValueIsOfCorrectType(value, expectedClass, tag.toString()));
            }
        }
        return results;
    }

    private static List<ValidationResult> validateSARMIItems(SARMILocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (SARMIMetadataKey tag : REQUIRED_ST1206_ITEMS.keySet()) {
            ValidationResult result = validateHasSARMIItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult> validateAdditionalSARMIItems(SARMILocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (SARMIMetadataKey tag : ADDITIONAL_ST1206_ITEMS.keySet()) {
            ValidationResult result = validateHasSARMIItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        return results;
    }

    private static ValidationResult validateHasValidItem(
            UasDatalinkMessage localSet, UasDatalinkTag uasDatalinkTag) {

        if (localSet.getIdentifiers().contains(uasDatalinkTag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST0601 item %s (%d) was found.",
                            uasDatalinkTag.toString(), uasDatalinkTag.getCode()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST0601 item %s (%d) was not found.",
                            uasDatalinkTag.toString(), uasDatalinkTag.getCode()));
            return result;
        }
    }

    private static ValidationResult validateHasSecurityItem(
            SecurityMetadataLocalSet localSet, SecurityMetadataKey tag) {

        if (localSet.getIdentifiers().contains(tag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST0102 item %s (%d) was found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST0102 item %s (%d) was not found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        }
    }

    private static ValidationResult validateHasSARMIItem(
            SARMILocalSet localSet, SARMIMetadataKey tag) {

        if (localSet.getIdentifiers().contains(tag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST1206 item %s (%d) was found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST1206 item %s (%d) was not found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        }
    }
}
