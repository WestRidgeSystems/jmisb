package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.*;

/** Dynamically create {@link ISecurityMetadataValue}s from {@link SecurityMetadataKey}s. */
public class LocalSetFactory {
    private LocalSetFactory() {}

    /**
     * Create a {@link ISecurityMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws IllegalArgumentException if input is invalid
     */
    public static ISecurityMetadataValue createValue(SecurityMetadataKey tag, byte[] bytes) {
        // Keep the case statements in enum ordinal order so we can keep track of what is
        // implemented. Mark all
        // unimplemented tags with TODO.
        switch (tag) {
            case SecurityClassification:
                return new ClassificationLocal(bytes);
            case CcCodingMethod:
                return new CcMethod(bytes);
            case ClassifyingCountry:
                return new SecurityMetadataString(
                        SecurityMetadataString.CLASSIFYING_COUNTRY, bytes);
            case SciShiInfo:
                return new SecurityMetadataString(SecurityMetadataString.SCI_SHI_INFO, bytes);
            case Caveats:
                return new SecurityMetadataString(SecurityMetadataString.CAVEATS, bytes);
            case ReleasingInstructions:
                return new SecurityMetadataString(
                        SecurityMetadataString.RELEASING_INSTRUCTIONS, bytes);
            case ClassifiedBy:
                return new SecurityMetadataString(SecurityMetadataString.CLASSIFIED_BY, bytes);
            case DerivedFrom:
                return new SecurityMetadataString(SecurityMetadataString.DERIVED_FROM, bytes);
            case ClassificationReason:
                return new SecurityMetadataString(
                        SecurityMetadataString.CLASSIFICATION_REASON, bytes);
            case DeclassificationDate:
                return new DeclassificationDate(bytes);
            case MarkingSystem:
                return new SecurityMetadataString(SecurityMetadataString.MARKING_SYSTEM, bytes);
            case OcCodingMethod:
                return new OcMethod(bytes);
            case ObjectCountryCodes:
                return new ObjectCountryCodeString(bytes);
            case ClassificationComments:
                return new SecurityMetadataString(
                        SecurityMetadataString.CLASSIFICATION_COMMENTS, bytes);
            case StreamId:
                return new StreamId(bytes);
            case TransportStreamId:
                return new TransportStreamId(bytes);
            case ItemDesignatorId:
                return new ItemDesignatorId(bytes);
            case Version:
                return new ST0102Version(bytes);
            case CcCodingMethodVersionDate:
                return new CcmDate(bytes);
            case OcCodingMethodVersionDate:
                return new OcmDate(bytes);
            default:
                throw new IllegalArgumentException("Unrecognized tag: " + tag);
        }
    }
}
