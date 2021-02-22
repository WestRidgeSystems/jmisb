package org.jmisb.api.klv.st1909;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.EnumSet;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0601.CornerOffset;
import org.jmisb.api.klv.st0601.FlagDataKey;
import org.jmisb.api.klv.st0601.FrameCenterElevation;
import org.jmisb.api.klv.st0601.FrameCenterHae;
import org.jmisb.api.klv.st0601.FullCornerLatitude;
import org.jmisb.api.klv.st0601.FullCornerLongitude;
import org.jmisb.api.klv.st0601.GenericFlagData01;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.NestedSecurityMetadata;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeight;
import org.jmisb.api.klv.st0601.SensorTrueAltitude;
import org.jmisb.api.klv.st0601.SlantRange;
import org.jmisb.api.klv.st0601.TargetLocationElevation;
import org.jmisb.api.klv.st0601.TargetWidth;
import org.jmisb.api.klv.st0601.TargetWidthExtended;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;

/** Utility converter for extracting ST1909 information from ST0601 metadata message. */
public class ST0601Converter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER;

    static {
        DATE_TIME_FORMATTER =
                new DateTimeFormatterBuilder()
                        .append(DateTimeFormatter.ISO_DATE)
                        .appendLiteral('T')
                        .appendPattern("HH:mm:ss.S")
                        .toFormatter();
    }

    /**
     * Convert an ST0601 message into the ST1909 metadata format.
     *
     * <p>This will extract the relevant items, and format them as required by ST1909.
     *
     * <p>Existing ST1909 data will be updated if applicable, or retained if not present in the
     * ST0601 message.
     *
     * @param message the source ST0601 message.
     * @param metadata the ST1909 metadata state to convert into.
     */
    public static void convertST0601(UasDatalinkMessage message, MetadataItems metadata) {
        convertMainSensorGroupValues(message, metadata);
        convertClassificationAndReleasabilityGroupValues(message, metadata);
        convertPlatformInformationGroupValues(message, metadata);
        convertTrueNorthArrowGroupValues(message, metadata);
        convertLaserSensorGroupValues(message, metadata);
        convertTargetGroupValues(message, metadata);
        convertDateTimeGroupValues(message, metadata);
    }

    private static void convertMainSensorGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        convertTagIfPresent(
                message, UasDatalinkTag.ImageSourceSensor, metadata, MetadataKey.MainSensorName);
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorRelativeAzimuthAngle,
                metadata,
                MetadataKey.AzAngle,
                "REL AZ %1$10s");
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorRelativeElevationAngle,
                metadata,
                MetadataKey.ElAngle,
                "REL EL %1$10s");
    }

    private static void convertClassificationAndReleasabilityGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        if (message.getIdentifiers().contains(UasDatalinkTag.SecurityLocalMetadataSet)) {
            NestedSecurityMetadata securityItem =
                    (NestedSecurityMetadata)
                            (message.getField(UasDatalinkTag.SecurityLocalMetadataSet));
            SecurityMetadataLocalSet securityMetadataLocalSet = securityItem.getLocalSet();
            if (securityMetadataLocalSet.getKeys().contains(SecurityMetadataKey.ClassifyingCountry)
                    && securityMetadataLocalSet
                            .getKeys()
                            .contains(SecurityMetadataKey.SecurityClassification)) {
                String baseValue =
                        securityMetadataLocalSet
                                        .getField(SecurityMetadataKey.ClassifyingCountry)
                                        .getDisplayableValue()
                                + " "
                                + securityMetadataLocalSet
                                        .getField(SecurityMetadataKey.SecurityClassification)
                                        .getDisplayableValue();
                String scishi = "";
                if (securityMetadataLocalSet.getKeys().contains(SecurityMetadataKey.SciShiInfo)) {
                    scishi =
                            securityMetadataLocalSet
                                    .getField(SecurityMetadataKey.SciShiInfo)
                                    .getDisplayableValue();
                }
                String caveats = "";
                if (securityMetadataLocalSet.getKeys().contains(SecurityMetadataKey.Caveats)) {
                    caveats =
                            securityMetadataLocalSet
                                    .getField(SecurityMetadataKey.Caveats)
                                    .getDisplayableValue();
                }
                String relto = "";
                if (securityMetadataLocalSet
                        .getKeys()
                        .contains(SecurityMetadataKey.ReleasingInstructions)) {
                    relto =
                            securityMetadataLocalSet
                                    .getField(SecurityMetadataKey.ReleasingInstructions)
                                    .getDisplayableValue();
                }
                // TODO: append the caveats, compartments and releasability using something from a
                // resource or configuration
                String line1 = baseValue + " " + caveats + " " + scishi + " " + relto;
                metadata.addItem(MetadataKey.ClassificationAndReleasabilityLine1, line1.trim());
            }
        }
    }

    private static void convertDateTimeGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        IUasDatalinkValue metadataTimestamp = message.getField(UasDatalinkTag.PrecisionTimeStamp);
        if (metadataTimestamp != null) {
            PrecisionTimeStamp precisionTimeStamp = (PrecisionTimeStamp) metadataTimestamp;
            String mt = "MT " + DATE_TIME_FORMATTER.format(precisionTimeStamp.getDateTime()) + "Z";
            metadata.addItem(MetadataKey.MetadataTimestamp, mt);
        }
    }

    private static void convertTargetGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        if ((message.getIdentifiers().contains(UasDatalinkTag.TargetLocationLatitude))
                && (message.getIdentifiers().contains(UasDatalinkTag.TargetLocationLongitude))
                && (message.getIdentifiers().contains(UasDatalinkTag.TargetLocationElevation))) {
            metadata.addItem(
                    MetadataKey.TargetLatitude,
                    message.getField(UasDatalinkTag.TargetLocationLatitude).getDisplayableValue()
                            + " TL LAT");
            metadata.addItem(
                    MetadataKey.TargetLongitude,
                    message.getField(UasDatalinkTag.TargetLocationLongitude).getDisplayableValue()
                            + " TL LON");
            IUasDatalinkValue value = message.getField(UasDatalinkTag.TargetLocationElevation);
            TargetLocationElevation targetLocationElevation = (TargetLocationElevation) value;
            metadata.addItem(
                    MetadataKey.TargetElevation,
                    "" + (int) (targetLocationElevation.getMeters()) + "m HAE TL EL");
        } else if ((message.getIdentifiers().contains(UasDatalinkTag.FrameCenterLatitude))
                && (message.getIdentifiers().contains(UasDatalinkTag.FrameCenterLongitude))) {
            metadata.addItem(
                    MetadataKey.TargetLatitude,
                    message.getField(UasDatalinkTag.FrameCenterLatitude).getDisplayableValue()
                            + " FC LAT");
            metadata.addItem(
                    MetadataKey.TargetLongitude,
                    message.getField(UasDatalinkTag.FrameCenterLongitude).getDisplayableValue()
                            + " FC LON");
            if (message.getIdentifiers().contains(UasDatalinkTag.FrameCenterHae)) {
                IUasDatalinkValue value = message.getField(UasDatalinkTag.FrameCenterHae);
                FrameCenterHae frameCenterHae = (FrameCenterHae) value;
                metadata.addItem(
                        MetadataKey.TargetElevation,
                        "" + (int) (frameCenterHae.getMeters()) + "m HAE FC EL");
            } else if (message.getIdentifiers().contains(UasDatalinkTag.FrameCenterElevation)) {
                IUasDatalinkValue value = message.getField(UasDatalinkTag.FrameCenterElevation);
                FrameCenterElevation frameCenterElevation = (FrameCenterElevation) value;
                metadata.addItem(
                        MetadataKey.TargetElevation,
                        "" + (int) (frameCenterElevation.getMeters()) + "m MSL FC EL");
            }
        }
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorHorizontalFov,
                metadata,
                MetadataKey.HorizontalFOV,
                "%s HFOV");
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorVerticalFov,
                metadata,
                MetadataKey.VerticalFOV,
                "%s VFOV");
        convertTargetWidthExtendedOrTargetWidthIfPresent(message, metadata);
        convertSlantRangeIfPresent(message, metadata);
    }

    private static void convertSlantRangeIfPresent(
            UasDatalinkMessage message, MetadataItems metadata) {
        IUasDatalinkValue slantRangeValue = message.getField(UasDatalinkTag.SlantRange);
        if (slantRangeValue != null) {
            SlantRange slantRange = (SlantRange) slantRangeValue;
            metadata.addItem(MetadataKey.SlantRange, "" + (int) slantRange.getMeters() + "m SR");
        }
    }

    private static void convertTargetWidthExtendedOrTargetWidthIfPresent(
            UasDatalinkMessage message, MetadataItems metadata) {
        IUasDatalinkValue targetWidthValue = message.getField(UasDatalinkTag.TargetWidthExtended);
        if (targetWidthValue != null) {
            TargetWidthExtended targetWidthExtended = (TargetWidthExtended) targetWidthValue;
            String targetWidth = "" + Math.round(targetWidthExtended.getMeters()) + "m TW";
            metadata.addItem(MetadataKey.TargetWidth, targetWidth);
        } else if (message.getIdentifiers().contains(UasDatalinkTag.TargetWidth)) {
            targetWidthValue = message.getField(UasDatalinkTag.TargetWidth);
            String targetWidth =
                    "" + Math.round(((TargetWidth) targetWidthValue).getMeters()) + "m TW";
            metadata.addItem(MetadataKey.TargetWidth, targetWidth);
        }
    }

    private static void convertPlatformInformationGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        if (message.getIdentifiers().contains(UasDatalinkTag.PlatformDesignation)) {
            if (message.getIdentifiers().contains(UasDatalinkTag.PlatformCallSign)) {
                metadata.addItem(
                        MetadataKey.PlatformName,
                        message.getField(UasDatalinkTag.PlatformDesignation).getDisplayableValue()
                                + " "
                                + message.getField(UasDatalinkTag.PlatformCallSign)
                                        .getDisplayableValue());
            } else {
                convertTag(
                        message,
                        UasDatalinkTag.PlatformDesignation,
                        metadata,
                        MetadataKey.PlatformName);
            }
        }
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorLatitude,
                metadata,
                MetadataKey.PlatformLatitude,
                "%s LAT");
        convertTagIfPresent(
                message,
                UasDatalinkTag.SensorLongitude,
                metadata,
                MetadataKey.PlatformLongitude,
                "%s LON");
        if (message.getIdentifiers().contains(UasDatalinkTag.SensorEllipsoidHeight)) {
            IUasDatalinkValue value = message.getField(UasDatalinkTag.SensorEllipsoidHeight);
            SensorEllipsoidHeight sensorEllipsoidHeight = (SensorEllipsoidHeight) value;
            metadata.addItem(
                    MetadataKey.PlatformAltitude,
                    "" + (int) (sensorEllipsoidHeight.getMeters()) + "m HAE ALT");
        } else if (message.getIdentifiers().contains(UasDatalinkTag.SensorTrueAltitude)) {
            IUasDatalinkValue value = message.getField(UasDatalinkTag.SensorTrueAltitude);
            SensorTrueAltitude sensorTrueAltitude = (SensorTrueAltitude) value;
            metadata.addItem(
                    MetadataKey.PlatformAltitude,
                    "" + (int) (sensorTrueAltitude.getMeters()) + "m MSL ALT");
        }
    }

    private static void convertTrueNorthArrowGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        // TODO: build this from corner values if offsets aren't available
        Collection<UasDatalinkTag> tags = message.getIdentifiers();
        Collection<UasDatalinkTag> offsetCornerTags =
                EnumSet.of(
                        UasDatalinkTag.OffsetCornerLatitudePoint1,
                        UasDatalinkTag.OffsetCornerLatitudePoint2,
                        UasDatalinkTag.OffsetCornerLatitudePoint3,
                        UasDatalinkTag.OffsetCornerLatitudePoint4,
                        UasDatalinkTag.OffsetCornerLongitudePoint1,
                        UasDatalinkTag.OffsetCornerLongitudePoint2,
                        UasDatalinkTag.OffsetCornerLongitudePoint3,
                        UasDatalinkTag.OffsetCornerLongitudePoint4);
        Collection<UasDatalinkTag> cornerPositionTags =
                EnumSet.of(
                        UasDatalinkTag.CornerLatPt1,
                        UasDatalinkTag.CornerLonPt1,
                        UasDatalinkTag.CornerLatPt2,
                        UasDatalinkTag.CornerLonPt2,
                        UasDatalinkTag.CornerLatPt3,
                        UasDatalinkTag.CornerLonPt3,
                        UasDatalinkTag.CornerLatPt4,
                        UasDatalinkTag.CornerLonPt4);
        if (tags.containsAll(offsetCornerTags)) {
            double northAngle = getNorthAngleFromOffsetCorners(message);
            metadata.addItem(MetadataKey.NorthAngle, "" + northAngle);
        } else if (tags.containsAll(cornerPositionTags)) {
            double northAngle = getNorthAngleFromFullCorners(message);
            metadata.addItem(MetadataKey.NorthAngle, "" + northAngle);
        }
    }

    private static double getNorthAngleFromOffsetCorners(UasDatalinkMessage message) {
        double lat1 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLatitudePoint1)))
                        .getDegrees();
        double lon1 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLongitudePoint1)))
                        .getDegrees();
        double lat4 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLatitudePoint4)))
                        .getDegrees();
        double lon4 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLongitudePoint4)))
                        .getDegrees();
        double angleLeftSide = Math.atan2(lat1 - lat4, lon1 - lon4) * 180.0 / Math.PI;
        angleLeftSide -= 90.0;
        if (angleLeftSide < 0) {
            angleLeftSide += 360.0;
        }
        double lat2 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLatitudePoint2)))
                        .getDegrees();
        double lon2 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLongitudePoint2)))
                        .getDegrees();
        double lat3 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLatitudePoint3)))
                        .getDegrees();
        double lon3 =
                ((CornerOffset) (message.getField(UasDatalinkTag.OffsetCornerLongitudePoint3)))
                        .getDegrees();
        double angleRightSide = Math.atan2(lat2 - lat3, lon2 - lon3) * 180.0 / Math.PI;
        angleRightSide -= 90.0;
        if (angleRightSide < 0) {
            angleRightSide += 360.0;
        }
        if (Math.abs(angleRightSide - angleLeftSide) > 180.0) {
            // this can happen when we're either side of a specific angle (like 359.0 and 1.0)
            // adjust angles
            if (angleRightSide > angleLeftSide) {
                angleLeftSide += 360.0;
            } else {
                angleRightSide += 360.0;
            }
        }
        double averageAngle = (angleLeftSide + angleRightSide) / 2.0;
        return averageAngle;
    }

    private static double getNorthAngleFromFullCorners(UasDatalinkMessage message) {
        double lat1 =
                ((FullCornerLatitude) (message.getField(UasDatalinkTag.CornerLatPt1))).getDegrees();
        double lon1 =
                ((FullCornerLongitude) (message.getField(UasDatalinkTag.CornerLonPt1)))
                        .getDegrees();
        double lat4 =
                ((FullCornerLatitude) (message.getField(UasDatalinkTag.CornerLatPt4))).getDegrees();
        double lon4 =
                ((FullCornerLongitude) (message.getField(UasDatalinkTag.CornerLonPt4)))
                        .getDegrees();
        double angleLeftSide = Math.atan2(lat1 - lat4, lon1 - lon4) * 180.0 / Math.PI;
        angleLeftSide -= 90.0;
        if (angleLeftSide < 0) {
            angleLeftSide += 360.0;
        }
        double lat2 =
                ((FullCornerLatitude) (message.getField(UasDatalinkTag.CornerLatPt2))).getDegrees();
        double lon2 =
                ((FullCornerLongitude) (message.getField(UasDatalinkTag.CornerLonPt2)))
                        .getDegrees();
        double lat3 =
                ((FullCornerLatitude) (message.getField(UasDatalinkTag.CornerLatPt3))).getDegrees();
        double lon3 =
                ((FullCornerLongitude) (message.getField(UasDatalinkTag.CornerLonPt3)))
                        .getDegrees();
        double angleRightSide = Math.atan2(lat2 - lat3, lon2 - lon3) * 180.0 / Math.PI;
        angleRightSide -= 90.0;
        if (angleRightSide < 0) {
            angleRightSide += 360.0;
        }
        if (Math.abs(angleRightSide - angleLeftSide) > 180.0) {
            // this can happen when we're slightly either side of north (like 359.0 and 1.0)
            if (angleRightSide > angleLeftSide) {
                angleLeftSide += 360.0;
            } else {
                angleRightSide += 360.0;
            }
        }
        double averageAngle = (angleLeftSide + angleRightSide) / 2.0;
        return averageAngle;
    }

    private static void convertTagIfPresent(
            UasDatalinkMessage message,
            UasDatalinkTag sourceTag,
            MetadataItems metadata,
            MetadataKey st1909DestinationKey) {
        if (message.getIdentifiers().contains(sourceTag)) {
            convertTag(message, sourceTag, metadata, st1909DestinationKey);
        }
    }

    private static void convertTagIfPresent(
            UasDatalinkMessage message,
            UasDatalinkTag sourceTag,
            MetadataItems metadata,
            MetadataKey st1909DestinationKey,
            String format) {
        if (message.getIdentifiers().contains(sourceTag)) {
            convertTag(message, sourceTag, metadata, st1909DestinationKey, format);
        }
    }

    private static void convertTag(
            UasDatalinkMessage message,
            UasDatalinkTag sourceTag,
            MetadataItems metadata,
            MetadataKey st1909DestinationKey) {
        metadata.addItem(
                st1909DestinationKey, message.getField(sourceTag).getDisplayableValue().trim());
    }

    private static void convertTag(
            UasDatalinkMessage message,
            UasDatalinkTag sourceTag,
            MetadataItems metadata,
            MetadataKey st1909DestinationKey,
            String format) {
        String formattedValue =
                String.format(format, message.getField(sourceTag).getDisplayableValue().trim());
        metadata.addItem(st1909DestinationKey, formattedValue);
    }

    private static void convertLaserSensorGroupValues(
            UasDatalinkMessage message, MetadataItems metadata) {
        // ST1909-37 - probably needs to be changed in the spec.
        metadata.addItem(MetadataKey.LaserSensorName, "Laser Rangefinder");
        if (message.getIdentifiers().contains(UasDatalinkTag.GenericFlagData01)) {
            GenericFlagData01 flagData =
                    (GenericFlagData01) message.getField(UasDatalinkTag.GenericFlagData01);
            if (flagData.getField(FlagDataKey.LaserRange).getValue().toLowerCase().contains("on")) {
                metadata.addItem(MetadataKey.LaserSensorStatus, "Laser ON");
            } else {
                metadata.addItem(MetadataKey.LaserSensorStatus, "Laser OFF");
            }
        }
        convertTagIfPresent(
                message,
                UasDatalinkTag.LaserPrfCode,
                metadata,
                MetadataKey.LaserPrfCode,
                "Laser PRF Code %1$6s");
    }
}
